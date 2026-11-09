package es.us.dp1.lx_xy_26_27.your_game_name.match;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.lx_xy_26_27.your_game_name.testing.RepositoryTest;
import es.us.dp1.lx_xy_26_27.your_game_name.testing.SociableTest;
import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import es.us.dp1.lx_xy_26_27.your_game_name.user.UserRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

/**
 * Tests of the JPQL query {@link MatchRepository#findPlayingMatchesByPlayer(User)},
 * used to check the business rule R-01
 * against the players defined in data.sql:
 * player1 (4) only waits, player2 (5) plays match 3, player3 (6) plays matches 3 and 4,
 * player4 (7) only has a finished match, and player6 (9) has no matches.
 */
@Epic("Matches Module")
@Feature("Matches Lobby")
@Owner("DP1-tutors")
@Story("HU-04: Crear partida")
@RepositoryTest
@SociableTest
@DataJpaTest
class MatchRepositoryTests {

    @Autowired
    private MatchRepository matchRepository;

    // Needed to get from the database the players that we pass to the query
    @Autowired
    private UserRepository userRepository;

    private User player(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    private static List<Integer> ids(List<Match> matches) {
        return matches.stream().map(Match::getId).toList();
    }

    @Test
    @Description("Without a player, the query returns no matches")
    void shouldNotFindPlayingMatchesOfNullPlayer() {
        assertThat(matchRepository.findPlayingMatchesByPlayer(null)).isEmpty();
    }

    // 4: player1 only takes part in matches that are waiting
    // 7: player4 only takes part in a finished match
    // 9: player6 takes part in no match
    @ParameterizedTest
    @ValueSource(ints = { 4, 7, 9 })
    @Description("A player without matches being played gets an empty list")
    void shouldNotFindPlayingMatches(int playerId) {
        assertThat(matchRepository.findPlayingMatchesByPlayer(player(playerId))).isEmpty();
    }

    @Test
    @Description("player2 is playing only match 3")
    void shouldFindOnePlayingMatch() {
        assertThat(ids(matchRepository.findPlayingMatchesByPlayer(player(5)))).containsExactly(3);
    }

    @Test
    @Description("player3 is playing matches 3 and 4")
    void shouldFindSeveralPlayingMatches() {
        assertThat(ids(matchRepository.findPlayingMatchesByPlayer(player(6)))).containsExactlyInAnyOrder(3, 4);
    }
}
