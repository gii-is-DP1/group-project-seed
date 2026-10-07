package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.lx_xy_26_27.your_game_name.configuration.SecurityConfiguration;
import es.us.dp1.lx_xy_26_27.your_game_name.exceptions.ResourceNotFoundException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

/**
 * Slice tests of {@link MatchController}: the service is mocked, so these tests only
 * check the HTTP layer (URLs, parameters, status codes and JSON).
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@WebMvcTest(controllers = MatchController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class MatchControllerTests {

    private static final String BASE_URL = "/api/v1/matches";

    @MockitoBean
    private MatchService matchService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Match waiting;
    private Match playing;

    @BeforeEach
    void setup() {
        waiting = new Match();
        waiting.setId(1);
        waiting.setName("Fiesta para todos");

        playing = new Match();
        playing.setId(3);
        playing.setName("Partida ya comenzada");
        playing.setCode("1234");
        playing.setStart(LocalDateTime.of(2026, 10, 1, 15, 20));
    }

    @Test
    @WithMockUser("player")
    void shouldFindAllMatchesWithoutFilters() throws Exception {
        when(matchService.getAllMatches()).thenReturn(List.of(waiting, playing));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Fiesta para todos"))
                .andExpect(jsonPath("$[0].status").value("WAITING"))
                .andExpect(jsonPath("$[1].status").value("PLAYING"));
    }

    @Test
    @WithMockUser("player")
    void shouldFindMatchesByName() throws Exception {
        when(matchService.getMatchesByName("fiesta")).thenReturn(List.of(waiting));

        mockMvc.perform(get(BASE_URL).param("name", "fiesta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser("player")
    void shouldFindMatchesByStatus() throws Exception {
        when(matchService.getPlayingMatches()).thenReturn(List.of(playing));

        mockMvc.perform(get(BASE_URL).param("status", "PLAYING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(3));
        verify(matchService, never()).getAllMatches();
    }

    @Test
    @WithMockUser("player")
    void shouldFilterByNameAndStatus() throws Exception {
        when(matchService.getMatchesByName("partida")).thenReturn(List.of(waiting, playing));

        mockMvc.perform(get(BASE_URL).param("name", "partida").param("status", "WAITING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser("player")
    void shouldFindMatchById() throws Exception {
        when(matchService.getMatchById(3)).thenReturn(playing);

        mockMvc.perform(get(BASE_URL + "/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Partida ya comenzada"))
                .andExpect(jsonPath("$.code").value("1234"));
    }

    @Test
    @WithMockUser("player")
    void shouldReturnNotFoundMatch() throws Exception {
        when(matchService.getMatchById(999)).thenThrow(new ResourceNotFoundException("Match", "id", 999));

        mockMvc.perform(get(BASE_URL + "/{id}", 999)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("player")
    void shouldCreateMatch() throws Exception {
        Match newMatch = new Match();
        newMatch.setName("Nueva partida");
        Match saved = new Match();
        saved.setId(100);
        saved.setName("Nueva partida");
        when(matchService.save(any(Match.class))).thenReturn(saved);

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMatch)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/matches/100"))
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    @WithMockUser("player")
    void shouldNotCreateMatchWithBlankName() throws Exception {
        Match invalid = new Match();
        invalid.setName("");

        mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
        verify(matchService, never()).save(any(Match.class));
    }

    @Test
    @WithMockUser("player")
    void shouldUpdateMatch() throws Exception {
        when(matchService.getMatchById(1)).thenReturn(waiting);
        Match changes = new Match();
        changes.setName("Nombre actualizado");

        mockMvc.perform(put(BASE_URL + "/{id}", 1).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changes)))
                .andExpect(status().isNoContent());
        verify(matchService).save(waiting);
    }

    @Test
    @WithMockUser("player")
    void shouldDeleteMatch() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 1).with(csrf()))
                .andExpect(status().isNoContent());
        verify(matchService).delete(1);
    }
}
