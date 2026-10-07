package es.us.dp1.lx_xy_26_27.your_game_name.match;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/matches")
@Tag(name = "Matches", description = "API for the management of matches (lobby)")
@SecurityRequirement(name = "bearerAuth")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Single collection resource with two optional filters:
     * - name: returns the matches whose name contains the given text.
     * - status: WAITING (can be joined), PLAYING (can be watched) or FINISHED.
     * Without filters it returns every match.
     */
    @GetMapping
    public List<Match> getMatches(
            @Parameter(description = "Text contained in the name of the match")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "Status of the match")
            @RequestParam(value = "status", required = false) MatchStatus status) {
        List<Match> result;
        if (name != null && !name.isBlank()) {
            result = matchService.getMatchesByName(name);
            // Both filters at the same time: we filter by status in memory.
            // (Discuss with the students: Specifications / Criteria API would avoid this)
            if (status != null)
                result = result.stream().filter(m -> m.getStatus() == status).toList();
        } else if (status != null) {
            result = switch (status) {
                case WAITING -> matchService.getWaitingMatches();
                case PLAYING -> matchService.getPlayingMatches();
                case FINISHED -> matchService.getFinishedMatches();
            };
        } else {
            result = matchService.getAllMatches();
        }
        return result;
    }

    @GetMapping("/{id}")
    public Match getMatchById(@PathVariable("id") Integer id) {
        return matchService.getMatchById(id);
    }

    @PostMapping
    public ResponseEntity<Match> createMatch(@Valid @RequestBody Match match) {
        match.setId(null);
        Match saved = matchService.save(match);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMatch(@Valid @RequestBody Match match, @PathVariable("id") Integer id) {
        Match toUpdate = matchService.getMatchById(id);
        BeanUtils.copyProperties(match, toUpdate, "id");
        matchService.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable("id") Integer id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
