package es.us.dp1.lx_xy_26_27.your_game_name.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import es.us.dp1.lx_xy_26_27.your_game_name.user.UserService;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

/**
 * Regression tests: the password of a registered user must be encoded exactly once,
 * so that the user can log in with the password used to register.
 */
@Epic("Users & Admin Module")
@Feature("Authentication")
@Owner("DP1-tutors")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignupAndLoginTests {

    private static final String PASSWORD = "s3cr3tPass";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registeredUserCanLogIn() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "newplayer", "password": "%s", "authority": "PLAYER",
                 "firstName": "New", "lastName": "Player"}
                """.formatted(PASSWORD)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/auth/signin").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "newplayer", "password": "%s"}
                """.formatted(PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void passwordIsEncodedOnlyOnceOnSignup() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup").contentType(MediaType.APPLICATION_JSON).content("""
                {"username": "otherplayer", "password": "%s", "authority": "PLAYER",
                 "firstName": "Other", "lastName": "Player"}
                """.formatted(PASSWORD)))
                .andExpect(status().isOk());

        String stored = userService.findUser("otherplayer").getPassword();
        assertTrue(passwordEncoder.matches(PASSWORD, stored));
    }

    @Test
    void savingAnExistingUserAgainKeepsItsPassword() {
        User user = userService.findUser("player1");
        String hash = user.getPassword();

        userService.saveUser(user);

        assertEquals(hash, userService.findUser("player1").getPassword());
        assertTrue(passwordEncoder.matches("0wn3r", userService.findUser("player1").getPassword()));
    }
}
