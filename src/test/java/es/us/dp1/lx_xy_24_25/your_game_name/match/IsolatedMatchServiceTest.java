package es.us.dp1.lx_xy_24_25.your_game_name.match;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import es.us.dp1.lx_xy_24_25.your_game_name.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class IsolatedMatchServiceTest {

    MatchService gs;
    @Mock
    MatchRepository gr;
    @Mock
    UserRepository or;

    @BeforeEach
    public void setup(){
        gs=new MatchService(gr);
    }

    @Test
    public void saveWithoutOngoingMatchs(){
        Match g=createValidOngoingMatch();
        Match g2=createValidOngoingMatch();
        g2.setId(g2.getId()+1);
        User o=new User();
        g.setPlayers(Set.of(o));
        when(gr.findOngoingMatchesByPlayer(any(User.class))).thenReturn(List.of());
        try {
            gs.save(g);
        } catch (ConcurrentMatchException e) {
            fail("No exception should be thrown: "+e.getMessage());
        }
    }


    @Test
    public void saveWithOngoingMatchs(){
        Match g=createValidOngoingMatch();
        Match g2=createValidOngoingMatch();
        g2.setId(g2.getId()+1);
        User o=new User();
        g.setPlayers(Set.of(o));
        when(gr.findOngoingMatchesByPlayer(any(User.class))).thenReturn(List.of(g2));
        assertThrows(ConcurrentMatchException.class, ()-> gs.save(g));        
    }



    private Match createValidOngoingMatch() {
        Match g=new Match();
        g.setId(22);
        g.setName("Mario Party!");
        g.setStart(LocalDateTime.of(2023, 11, 11, 11,11, 11));        
        return g;
    }
}
