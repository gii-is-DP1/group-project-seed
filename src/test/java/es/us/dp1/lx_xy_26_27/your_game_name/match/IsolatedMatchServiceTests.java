package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.lx_xy_26_27.your_game_name.testing.IsolatedTest;
import es.us.dp1.lx_xy_26_27.your_game_name.testing.ServiceTest;
import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

/**
 * Isolated tests of the business rule R-01 "a player cannot play two matches at the same time":
 * the repository is a Mockito mock, so neither Spring nor the database are used.
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@Story("HU-04: Crear partida")
@ServiceTest
@IsolatedTest
@ExtendWith(MockitoExtension.class)
class IsolatedMatchServiceTests {

    @Mock
    private MatchRepository matchRepository;

    // Mockito creates the service passing the mock to its constructor
    @InjectMocks
    private MatchService matchService;

    private User player;
    private Match match;
    private Match otherMatch;

    @BeforeEach
    void setup() {
        player = new User();
        player.setId(5);
        player.setUsername("player2");

        match = new Match();
        match.setId(22);
        match.setName("Mario Party!");
        match.setStart(LocalDateTime.of(2026, 11, 11, 11, 11));
        match.setPlayers(Set.of(player));

        otherMatch = new Match();
        otherMatch.setId(3);
        otherMatch.setName("Partida abierta ya comenzada");
        otherMatch.setStart(LocalDateTime.of(2026, 10, 1, 15, 20));
    }

    @Test
    @Description("R-01: If the players are not playing other matches, the match is saved")
    void shouldSaveWhenPlayersAreFree() {
        when(matchRepository.findPlayingMatchesByPlayer(player)).thenReturn(List.of());
        when(matchRepository.save(match)).thenReturn(match);

        assertSame(match, matchService.save(match));
        verify(matchRepository).save(match);
    }

    @Test
    @Description("R-01: If a player is playing another match, the match is not saved")
    void shouldNotSaveWhenPlayerIsPlayingAnotherMatch() {
        when(matchRepository.findPlayingMatchesByPlayer(player)).thenReturn(List.of(otherMatch));

        ConcurrentMatchException e = assertThrows(ConcurrentMatchException.class, () -> matchService.save(match));

        assertSame(otherMatch, e.getOtherMatch());
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    @Description("R-01: The match being saved does not conflict with itself")
    void shouldSaveWhenTheOnlyPlayingMatchIsTheSameOne() {
        when(matchRepository.findPlayingMatchesByPlayer(player)).thenReturn(List.of(match));
        when(matchRepository.save(match)).thenReturn(match);

        assertSame(match, matchService.save(match));
    }

    @Test
    @Description("R-01: The rule is not checked for matches that are waiting for players")
    void shouldNotCheckTheRuleForWaitingMatches() {
        match.setStart(null);
        when(matchRepository.save(match)).thenReturn(match);

        matchService.save(match);

        verify(matchRepository, never()).findPlayingMatchesByPlayer(any(User.class));
    }
}
