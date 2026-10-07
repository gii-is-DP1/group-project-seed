package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * None of these queries needs JPQL: Spring Data derives them from the method names.
 */
public interface MatchRepository extends CrudRepository<Match, Integer> {

    List<Match> findAll();

    // SELECT m FROM Match m WHERE UPPER(m.name) LIKE UPPER('%' || :name || '%')
    List<Match> findByNameContainingIgnoreCase(String name);

    // Waiting for players: not started yet
    List<Match> findByStartIsNull();

    // Being played: started but not finished
    List<Match> findByStartIsNotNullAndFinishIsNull();

    // Finished
    List<Match> findByFinishIsNotNull();
}
