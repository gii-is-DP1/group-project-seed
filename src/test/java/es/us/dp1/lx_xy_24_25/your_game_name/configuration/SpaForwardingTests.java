package es.us.dp1.lx_xy_24_25.your_game_name.configuration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

/**
 * Checks that the React routes are forwarded to index.html and are public when the
 * frontend is packaged in the JAR, while the API is still protected.
 */
@Epic("Configuration")
@Feature("Packaged frontend (SPA)")
@Owner("DP1-tutors")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class SpaForwardingTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldForwardSingleSegmentRoutesToIndex() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void shouldForwardNestedRoutesToIndex() throws Exception {
        mockMvc.perform(get("/users/5")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void shouldForwardDeepRoutesToIndex() throws Exception {
        mockMvc.perform(get("/games/7/players/3")).andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    }

    @Test
    void shouldNotForwardStaticFiles() throws Exception {
        mockMvc.perform(get("/assets/does-not-exist.js")).andExpect(status().isNotFound()).andExpect(forwardedUrl(null));
    }

    @Test
    void shouldNotForwardNorExposeTheApi() throws Exception {
        mockMvc.perform(get("/api/v1/users")).andExpect(status().isUnauthorized()).andExpect(forwardedUrl(null));
    }

    @Test
    void shouldNotForwardSwagger() throws Exception {
        mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk()).andExpect(forwardedUrl(null));
    }
}
