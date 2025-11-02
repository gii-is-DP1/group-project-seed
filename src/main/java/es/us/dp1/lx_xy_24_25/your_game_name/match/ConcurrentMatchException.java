package es.us.dp1.lx_xy_24_25.your_game_name.match;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.us.dp1.lx_xy_24_25.your_game_name.user.User;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConcurrentMatchException extends Exception{

    User user;
    Match match;
    Match otherMatch;
    public ConcurrentMatchException(User player, Match m, Match otherMatch) {
        super("Unable to save match awith id '"+m.getId()+"as started, since user '"+player.getUsername()+"' is already playing another match with id '"+otherMatch.getId()+"'");
        this.user=player;
        this.match=m;
        this.otherMatch=otherMatch;
    }
    
}
