package es.us.dp1.lx_xy_24_25.your_game_name.match;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes=MatchService.class))
public class SocialMatchServiceTest 
{

    @Autowired
    MatchService ms;
    @Autowired
    UserRepository ur;

    @Test
    public void saveWithoutOngoingMatches(){
        Match m=createValidOngoingMatch();
        Optional<User> u=ur.findById(4);
        assumeTrue(u.isPresent());
        m.setPlayers(Set.of(u.get()));
        try {
            ms.save(m);
        } catch (ConcurrentMatchException e) {
            fail("No exception should be thrown: "+e.getMessage());
        }
    }


    @Test
    public void saveWithOngoingMatchs(){
        Match m=createValidOngoingMatch();
        Optional<User> u=ur.findById(5);
        assumeTrue(u.isPresent());
        m.setPlayers(Set.of(u.get()));
        assertThrows(ConcurrentMatchException.class, ()-> ms.save(m));
    }



    private Match createValidOngoingMatch() {
        Match m=new Match();
        m.setId(22);
        m.setName("Mario Party!");
        m.setStart(LocalDateTime.of(2023, 11, 11, 11,11, 11));
        return m;
    }
}



