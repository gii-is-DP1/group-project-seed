package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_26_27.your_game_name.exceptions.ResourceNotFoundException;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Transactional(readOnly = true)
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    // Business need of the lobby: find matches by (part of) their name.
    // Dummy implementation for now: it will be implemented with a query method later.
    @Transactional(readOnly = true)
    public List<Match> getMatchesByName(String namePattern) {
        return matchRepository.findAll();
    }

    // Business need of the lobby: matches waiting for players (they can be joined)
    // Dummy implementation for now.
    @Transactional(readOnly = true)
    public List<Match> getWaitingMatches() {
        return matchRepository.findAll();
    }

    // Business need of the lobby: matches being played (they can be watched as a spectator)
    // Dummy implementation for now.
    @Transactional(readOnly = true)
    public List<Match> getPlayingMatches() {
        return matchRepository.findAll();
    }

    // Dummy implementation for now.
    @Transactional(readOnly = true)
    public List<Match> getFinishedMatches() {
        return matchRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Match getMatchById(Integer id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
    }

    @Transactional
    public Match save(Match match) {
        return matchRepository.save(match);
    }

    @Transactional
    public void delete(Integer id) {
        Match toDelete = getMatchById(id);
        matchRepository.delete(toDelete);
    }
}
