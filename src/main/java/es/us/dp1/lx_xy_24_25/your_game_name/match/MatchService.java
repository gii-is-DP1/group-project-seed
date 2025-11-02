package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Match save(Match m) {
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
