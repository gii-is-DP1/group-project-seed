package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;

@Service
public class MatchService {

    MatchRepository mr;


    @Autowired
    public MatchService(MatchRepository mr){
        this.mr=mr;
    }
    

    @Transactional(readOnly=true)
    public List<Match> getAllMatchs(){
    return mr.findAll();
    }

    @Transactional(readOnly=true)
    public List<Match> getMatchesByName(String namepatterm){
        return mr.findByName(namepatterm);
    }

    

    @Transactional(readOnly=true)
    public List<Match> getRunningMatches(){
        return mr.findAll();
    }

    @Transactional(rollbackFor = {ConcurrentMatchException.class})
    public Match save(Match m) throws ConcurrentMatchException {
        List<Match> onGoingMatches;
        if(m.getStatus()==MatchStatus.ONGOING)
            for(User player:m.getPlayers()){
                onGoingMatches=mr.findOngoingMatchesByPlayer(player);
                if(!onGoingMatches.isEmpty() && !onGoingMatches.contains(m))                
                    throw new ConcurrentMatchException(player,m,onGoingMatches.get(0));
            }
        mr.save(m);
        return m;
    }

    @Transactional(readOnly=true)
    public Optional<Match> getMatchById(Integer id) {
        return mr.findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        mr.deleteById(id);
    }

}
