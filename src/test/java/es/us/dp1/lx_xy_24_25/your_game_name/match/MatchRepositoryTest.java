package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserRepository;

@DataJpaTest()
public class MatchRepositoryTest {

    @Autowired
    MatchRepository mr;
    @Autowired
    UserRepository ur;

    @Test
    public void findOngoingMatchesByNullPlayer(){ 
        List<Match> actualResult=mr.findOngoingMatchesByPlayer(null);
        assertTrue(actualResult.isEmpty());
    }


     @Test
    public void findOngoingMatchesByPlayerTestNoGames() {
        // There are 3 ways to get one empty list of results => Refactor as 3 different
        // tests!
        // This user has matches that have not started yet:
        Optional<User> user = ur.findById(4);
        List<Match> actualResult = mr.findOngoingMatchesByPlayer(user.get());
        assertTrue(actualResult.isEmpty());

        // This owner has no matches:
        user = ur.findById(9);
        actualResult = mr.findOngoingMatchesByPlayer(user.get());
        assertTrue(actualResult.isEmpty());

        // This owner has matches that are finished already:
        user = ur.findById(8);
        actualResult = mr.findOngoingMatchesByPlayer(user.get());
        assertTrue(actualResult.isEmpty());
    }

    // This owner has matches that have not started yet (4)
    // This owner has no matches (9)
    // This owner has matches that are finished already (8)
    @ParameterizedTest
    @ValueSource(ints = { 4, 9, 8 })
    public void findOngoingMatchesByPlayerTestNoGamesParameterized(int id) {

        Optional<User> user = ur.findById(id);
        List<Match> actualResult = mr.findOngoingMatchesByPlayer(user.get());
        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void findOngoingMatchesByPlayerTestSingleGame(){
        Optional<User> o=ur.findById(5);
        Optional<Match> g=mr.findById(2);
        List<Match> actualResult=mr.findOngoingMatchesByPlayer(o.get());   
        assertEquals(1,actualResult.size());
        assertTrue(actualResult.contains(g.get()));
    }

    @Test
    public void findOngoingGamesByPlayerMultipleGames(){
        Optional<User> o=ur.findById(6);
        Optional<Match> g2=mr.findById(2);
        Optional<Match> g4=mr.findById(4);
        List<Match> actualResult=mr.findOngoingMatchesByPlayer(o.get());   
        //assertEquals(1,actualResult.size());
        assertThat(actualResult.size()).isEqualTo(2);
        assertTrue(actualResult.contains(g2.get()));
        assertTrue(actualResult.contains(g4.get()));
    }
}
