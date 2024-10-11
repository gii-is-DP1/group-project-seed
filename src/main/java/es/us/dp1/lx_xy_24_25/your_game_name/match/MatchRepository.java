package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {
    List<Match> findAll();
    List<Match> findByName(String name);
}
