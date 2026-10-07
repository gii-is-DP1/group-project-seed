package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {

    List<Match> findAll();
}
