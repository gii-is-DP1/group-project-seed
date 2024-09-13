package es.us.dp1.lx_xy_24_25.your_game_name.statistic;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
    
    List<Achievement> findAll();
    
    public Achievement findByName(String name);
}

