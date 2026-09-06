package es.us.dp1.lx_xy_24_25.your_game_name;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class DatabasePersistenceTests {

    @TempDir
    Path directory;

    @Test
    void preservesChangesAndDeletionsAfterRestart() {
        try (var context = start("persistence-check,init-data")) {
            var jdbc = context.getBean(JdbcTemplate.class);
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM appusers", Integer.class)).isEqualTo(11);
            jdbc.update("UPDATE appusers SET username = 'renamed-admin' WHERE id = 1");
            jdbc.update("DELETE FROM appusers WHERE id = 13");
            jdbc.update("INSERT INTO appusers (id, username, password, authority) VALUES (99, 'new-player', 'test', 2)");
        }

        try (var context = start("persistence-check")) {
            var jdbc = context.getBean(JdbcTemplate.class);
            assertThat(jdbc.queryForObject("SELECT username FROM appusers WHERE id = 1", String.class))
                .isEqualTo("renamed-admin");
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM appusers WHERE id = 13", Integer.class))
                .isZero();
            assertThat(jdbc.queryForObject("SELECT username FROM appusers WHERE id = 99", String.class))
                .isEqualTo("new-player");
        }
    }

    private ConfigurableApplicationContext start(String profiles) {
        // An explicit profile bypasses the test-only application-default.properties.
        // ddl-auto and SQL initialization are read from the real application configuration.
        return new SpringApplicationBuilder(GameApplication.class).run(
            "--spring.profiles.active=" + profiles,
            "--spring.datasource.url=jdbc:h2:file:" + directory.resolve("testdb").toString().replace('\\', '/'),
            "--server.port=0",
            "--spring.jpa.show-sql=false");
    }
}
