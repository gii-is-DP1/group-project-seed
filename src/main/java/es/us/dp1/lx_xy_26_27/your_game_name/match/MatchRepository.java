package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.us.dp1.lx_xy_26_27.your_game_name.user.User;

/**
 * Most of these queries are derived by Spring Data from the method names.
 * The last one is written in JPQL.
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

    // Matches being played in which the given player takes part (live coding session 2)
    @Query("SELECT m FROM Match m WHERE m.start IS NOT NULL AND m.finish IS NULL AND :player MEMBER OF m.players")
    List<Match> findPlayingMatchesByPlayer(@Param("player") User player);
}
