package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import es.us.dp1.lx_xy_26_27.your_game_name.testing.ServiceTest;
import es.us.dp1.lx_xy_26_27.your_game_name.testing.SociableTest;
import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import es.us.dp1.lx_xy_26_27.your_game_name.user.UserRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

/**
 * Sociable tests of the business rule R-01 "a player cannot play two matches at the same time":
 * {@link MatchService} uses the real {@link MatchRepository} and the data of data.sql.
 * <p>
 * {@code @DataJpaTest} only creates the JPA beans (repositories), so the service under
 * test is added with {@code @Import}. Each test runs in a transaction that is rolled back.
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@Story("HU-04: Crear partida")
@ServiceTest
@SociableTest
@DataJpaTest
@Import(MatchService.class)
class SociableMatchServiceTests {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserRepository userRepository;

    private User player(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    private static Match newPlayingMatch(User... players) {
        Match match = new Match();
        match.setName("Mario Party!");
        match.setStart(LocalDateTime.of(2026, 11, 11, 11, 11));
        match.setPlayers(new HashSet<>(Set.of(players)));
        return match;
    }

    @Test
    @Description("R-01: A player who is not playing any match can start a new one")
    void shouldSavePlayingMatchOfFreePlayer() {
        Match saved = assertDoesNotThrow(() -> matchService.save(newPlayingMatch(player(4))));

        assertNotNull(saved.getId());
        assertThat(matchService.getPlayingMatches()).contains(saved);
    }

    @Test
    @Description("R-01: A player who is already playing match 3 cannot start another match")
    void shouldNotSavePlayingMatchOfBusyPlayer() {
        int matchesBefore = matchService.getAllMatches().size();

        ConcurrentMatchException e = assertThrows(ConcurrentMatchException.class,
                () -> matchService.save(newPlayingMatch(player(5))));

        assertEquals(3, e.getOtherMatch().getId());
        assertEquals(matchesBefore, matchService.getAllMatches().size());
    }

    @Test
    @Description("R-01: The rule only applies to matches being played: a busy player can join a waiting match")
    void shouldSaveWaitingMatchOfBusyPlayer() {
        Match waiting = newPlayingMatch(player(5));
        waiting.setStart(null);

        assertDoesNotThrow(() -> matchService.save(waiting));
    }

    @Test
    @Description("R-01: A match being played can be saved again: it does not conflict with itself")
    void shouldUpdatePlayingMatchOfItsOwnPlayer() {
        Match match3 = matchService.getMatchById(3);
        // Hibernate needs a mutable collection: Set.of(...) would fail when flushing
        match3.setPlayers(new HashSet<>(Set.of(player(5))));
        match3.setName("Nuevo nombre");

        assertDoesNotThrow(() -> matchService.save(match3));
        assertEquals("Nuevo nombre", matchService.getMatchById(3).getName());
    }
}
