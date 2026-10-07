package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.time.LocalDateTime;

import es.us.dp1.lx_xy_26_27.your_game_name.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * A match (game) that players can browse in the lobby. For now it is an isolated
 * entity: the relationship with its players will be added in a later session.
 */
@Getter
@Setter
@Entity
// "MATCH" is a reserved word in MySQL, so we use a plural table name
@Table(name = "matches")
public class Match extends NamedEntity {

    // Secret code required to join a private match (null means the match is public)
    String code;

    // Instant in which the match started (null means it is still waiting for players)
    LocalDateTime start;

    // Instant in which the match finished (null means it has not finished yet)
    LocalDateTime finish;

    @Override
    @NotBlank
    public String getName() {
        return super.getName();
    }
}
