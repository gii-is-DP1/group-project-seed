package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;

public interface MatchRepository extends CrudRepository<Match, Integer> {
    List<Match> findAll();
    List<Match> findByName(String name);
    @Query("SELECT m FROM  MATCH  m WHERE m.start<>null AND m.finish=null AND :player MEMBER OF m.players")
    List<Match> findOngoingMatchesByPlayer(@Param("player") User player);
}
