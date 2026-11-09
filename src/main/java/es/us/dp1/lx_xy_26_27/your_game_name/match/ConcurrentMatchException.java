package es.us.dp1.lx_xy_26_27.your_game_name.match;

import es.us.dp1.lx_xy_26_27.your_game_name.user.User;
import lombok.Getter;

/**
 * Thrown when a match that is being played includes a player who is already
 * playing another match. The REST API answers it with 409 (Conflict).
 */
@Getter
public class ConcurrentMatchException extends RuntimeException {

    private final transient User player;
    private final transient Match otherMatch;

    public ConcurrentMatchException(User player, Match otherMatch) {
        super("Player '" + player.getUsername() + "' is already playing the match with id " + otherMatch.getId());
        this.player = player;
        this.otherMatch = otherMatch;
    }
}
