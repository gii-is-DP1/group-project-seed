package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest()
public class MatchRepositoryTest {

    @Autowired
    MatchRepository mr;

    @Test
    public void findOngoingMatchesByNullPlayer(){ 
        List<Match> actualResult=mr.findOngoingMatchesByPlayer(null);
        assertTrue(actualResult.isEmpty());
    }

}
