package org.javaexercises.reactiveapiddspells;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaexercises.reactiveapiddspells.DTO.SpellsResponseDto;
import org.javaexercises.reactiveapiddspells.service.SpellService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SpellServiceTest {

    private static final String JSON_FILE_PATH = "spells.json"; // Caminho para o arquivo JSON no classpath

    // Configurando um contêiner Nginx
    private static final GenericContainer<?> nginxContainer = new GenericContainer<>(DockerImageName.parse("nginx:alpine"))
            .withCopyFileToContainer(MountableFile.forClasspathResource(JSON_FILE_PATH), "/usr/share/nginx/html/spells.json")
            .withExposedPorts(80);

    @Autowired
    private SpellService spellService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void startNginx() {
        nginxContainer.start();
        String baseUrl = String.format("http://%s:%d/api/spells/?name=",
                nginxContainer.getHost(),
                nginxContainer.getMappedPort(80));

        // Configurar a URL base na classe de configuração ou no WebClient
        System.setProperty("api.base.url", baseUrl);
    }

    @AfterAll
    public static void stopNginx() {
        nginxContainer.stop();
    }

    @Test
    public void testGetSpellByName() throws JsonProcessingException {
        String spellName = "fireball";

        Mono<SpellsResponseDto> response = spellService.getSpellByName(spellName);

        SpellsResponseDto spellsResponseDto = response.block();

        assertThat(spellsResponseDto).isNotNull();
        assertThat(spellsResponseDto.getCount()).isEqualTo(2);
        assertThat(spellsResponseDto.getResults()).hasSize(2);
        assertThat(spellsResponseDto.getResults().get(0).getName()).isEqualTo("Delayed Blast Fireball");
        assertThat(spellsResponseDto.getResults().get(1).getName()).isEqualTo("Fireball");
    }
}
