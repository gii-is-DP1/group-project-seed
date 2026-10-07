package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_26_27.your_game_name.exceptions.ResourceNotFoundException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

/**
 * Integration tests of {@link MatchService} against the matches defined in data.sql:
 * 1 and 2 are waiting, 3 and 4 are being played, and 5 is finished.
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@SpringBootTest
@AutoConfigureTestDatabase
class MatchServiceTests {

    @Autowired
    private MatchService matchService;

    private static List<Integer> ids(List<Match> matches) {
        return matches.stream().map(Match::getId).toList();
    }

    @Test
    void shouldFindAllMatches() {
        assertEquals(5, matchService.getAllMatches().size());
    }

    @Test
    void shouldFindMatchesByNameIgnoringCase() {
        assertThat(ids(matchService.getMatchesByName("PARTIDA"))).containsExactlyInAnyOrder(3, 4, 5);
        assertThat(ids(matchService.getMatchesByName("fiesta"))).containsExactly(1);
        assertThat(matchService.getMatchesByName("does not exist")).isEmpty();
    }

    @Test
    void shouldFindWaitingMatches() {
        assertThat(ids(matchService.getWaitingMatches())).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void shouldFindPlayingMatches() {
        assertThat(ids(matchService.getPlayingMatches())).containsExactlyInAnyOrder(3, 4);
    }

    @Test
    void shouldFindFinishedMatches() {
        assertThat(ids(matchService.getFinishedMatches())).containsExactly(5);
    }

    @Test
    void shouldComputeStatusFromStartAndFinish() {
        assertEquals(MatchStatus.WAITING, matchService.getMatchById(1).getStatus());
        assertEquals(MatchStatus.PLAYING, matchService.getMatchById(3).getStatus());
        assertEquals(MatchStatus.FINISHED, matchService.getMatchById(5).getStatus());
    }

    @Test
    void shouldFindMatchById() {
        Match match = matchService.getMatchById(2);
        assertEquals("Solo para amigos", match.getName());
        assertEquals("super-secret", match.getCode());
    }

    @Test
    void shouldNotFindMatchWithWrongId() {
        assertThrows(ResourceNotFoundException.class, () -> matchService.getMatchById(999));
    }

    @Test
    @Transactional
    void shouldInsertMatch() {
        Match match = new Match();
        match.setName("Nueva partida");
        match.setStart(LocalDateTime.now());

        Match saved = matchService.save(match);

        assertNotNull(saved.getId());
        assertEquals(6, matchService.getAllMatches().size());
        assertThat(ids(matchService.getPlayingMatches())).contains(saved.getId());
    }

    @Test
    @Transactional
    void shouldDeleteMatch() {
        matchService.delete(5);
        assertEquals(4, matchService.getAllMatches().size());
        assertThrows(ResourceNotFoundException.class, () -> matchService.getMatchById(5));
    }

    @Test
    @Transactional
    void shouldNotDeleteMatchWithWrongId() {
        assertThrows(ResourceNotFoundException.class, () -> matchService.delete(999));
    }
}
