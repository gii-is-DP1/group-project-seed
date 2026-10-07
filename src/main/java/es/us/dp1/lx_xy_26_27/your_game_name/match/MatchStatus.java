package es.us.dp1.lx_xy_26_27.your_game_name.match;

/**
 * Status of a match as shown in the lobby. It is not stored in the database:
 * it is derived from the start and finish instants of the match.
 */
public enum MatchStatus {
    WAITING, PLAYING, FINISHED
}
