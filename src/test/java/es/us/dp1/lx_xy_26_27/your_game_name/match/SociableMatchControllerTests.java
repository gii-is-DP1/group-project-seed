package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_26_27.your_game_name.testing.ControllerTest;
import es.us.dp1.lx_xy_26_27.your_game_name.testing.SociableTest;
import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

/**
 * Sociable tests of {@link MatchController}: the whole backend is started (security,
 * controller, service, repository and database), so they also check the security rules.
 * {@code @Transactional} rolls back the changes made by each test.
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@Story("HU-04: Crear partida")
@ControllerTest
@SociableTest
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SociableMatchControllerTests {

    private static final String BASE_URL = "/api/v1/matches";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchService matchService;

    // Only the id is sent: it is enough for JPA to link the existing user
    private static Match newPlayingMatch(String name, int playerId) {
        User player = new User();
        player.setId(playerId);
        Match match = new Match();
        match.setName(name);
        match.setStart(LocalDateTime.of(2026, 11, 11, 11, 11));
        match.setPlayers(Set.of(player));
        return match;
    }

    @Test
    @WithMockUser(username = "player1", authorities = "PLAYER")
    @Description("A player creates a match with a free player, and it is stored in the database")
    void shouldCreateMatch() throws Exception {
        Match match = newPlayingMatch("Crazy smash bros session", 4);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PLAYING"))
                .andExpect(jsonPath("$.players[0].id").value(4));

        assertThat(matchService.getMatchesByName("Crazy smash bros session")).hasSize(1);
    }

    @Test
    @WithMockUser(username = "player1", authorities = "PLAYER")
    @Description("R-01: A match with a player who is playing another match is rejected with 409 and not stored")
    void shouldNotCreateMatchWithBusyPlayer() throws Exception {
        Match match = newPlayingMatch("Turtles in time", 5);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isConflict());

        assertThat(matchService.getMatchesByName("Turtles in time")).isEmpty();
    }

    @Test
    @WithAnonymousUser
    @Description("An anonymous user cannot create matches")
    void shouldNotCreateMatchAsAnonymous() throws Exception {
        Match match = newPlayingMatch("Anonymous match", 4);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isUnauthorized());

        assertThat(matchService.getMatchesByName("Anonymous match")).isEmpty();
    }

    @Test
    @WithMockUser(username = "guest", authorities = "GUEST")
    @Description("A user without the PLAYER or ADMIN authority cannot create matches")
    void shouldNotCreateMatchWithoutAuthority() throws Exception {
        Match match = newPlayingMatch("Guest match", 4);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(match)))
                .andExpect(status().isForbidden());
    }
}
