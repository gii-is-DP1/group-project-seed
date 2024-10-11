package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    MatchRepository mr;


    @Autowired
    public MatchService(MatchRepository gr){
        this.mr=mr;
    }
    

    @Transactional(readOnly=true)
    public List<Match> getAllMatchs(){
    return mr.findAll();
    }

    @Transactional(readOnly=true)
    public List<Match> getMatchsByName(String namepatterm){
        return mr.findAll();
    }

    

    @Transactional(readOnly=true)
    public List<Match> getRunningMatchs(){
        return mr.findAll();
    }

    @Transactional
    public Match save(Match m) {
        mr.save(m);
        return m;
    }

}
