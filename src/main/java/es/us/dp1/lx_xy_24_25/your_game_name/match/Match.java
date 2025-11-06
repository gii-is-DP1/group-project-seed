package es.us.dp1.lx_xy_24_25.your_game_name.match;

import java.time.LocalDateTime;
import java.util.Set;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import es.us.dp1.lx_xy_24_25.your_game_name.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Match extends BaseEntity {
    @NotNull
    @NotEmpty
    private String name;
    private String code;
    private LocalDateTime start;
    private LocalDateTime finish;

    @ManyToOne
    User creator;
    @ManyToMany
    Set<User> players;    

    @Transient
    public MatchStatus getStatus(){
        if(start==null) 
            return MatchStatus.WAITING;
        else if(finish==null)
            return MatchStatus.ONGOING;
        else
            return MatchStatus.FINISHED;
    }
}
