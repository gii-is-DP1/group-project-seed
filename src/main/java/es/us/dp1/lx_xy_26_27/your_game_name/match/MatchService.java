package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.lx_xy_26_27.your_game_name.exceptions.ResourceNotFoundException;
import es.us.dp1.lx_xy_26_27.your_game_name.user.User;

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
    @Transactional(readOnly = true)
    public List<Match> getMatchesByName(String namePattern) {
        return matchRepository.findByNameContainingIgnoreCase(namePattern);
    }

    // Business need of the lobby: matches waiting for players (they can be joined)
    @Transactional(readOnly = true)
    public List<Match> getWaitingMatches() {
        return matchRepository.findByStartIsNull();
    }

    // Business need of the lobby: matches being played (they can be watched as a spectator)
    @Transactional(readOnly = true)
    public List<Match> getPlayingMatches() {
        return matchRepository.findByStartIsNotNullAndFinishIsNull();
    }

    @Transactional(readOnly = true)
    public List<Match> getFinishedMatches() {
        return matchRepository.findByFinishIsNotNull();
    }

    @Transactional(readOnly = true)
    public Match getMatchById(Integer id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
    }

    /**
     * Business rule R-01 (live coding session 2): a player cannot play two matches at the same time.
     * If the match is being played and one of its players is already playing another match,
     * the match is not saved and a {@link ConcurrentMatchException} is thrown.
     */
    @Transactional
    public Match save(Match match) {
        if (match.getStatus() == MatchStatus.PLAYING && match.getPlayers() != null) {
            for (User player : match.getPlayers()) {
                for (Match other : matchRepository.findPlayingMatchesByPlayer(player)) {
                    // A new match has no id yet: Objects.equals avoids a NullPointerException
                    if (!Objects.equals(other.getId(), match.getId()))
                        throw new ConcurrentMatchException(player, other);
                }
            }
        }
        return matchRepository.save(match);
    }

    @Transactional
    public void delete(Integer id) {
        Match toDelete = getMatchById(id);
        matchRepository.delete(toDelete);
    }
}
