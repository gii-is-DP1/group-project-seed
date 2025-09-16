package es.us.dp1.lx_yy_25_26.my_game.statistic;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{
    
    List<Achievement> findAll();
    
    public Achievement findByName(String name);
}
