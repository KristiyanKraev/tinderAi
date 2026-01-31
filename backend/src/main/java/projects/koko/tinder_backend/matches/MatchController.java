package projects.koko.tinder_backend.matches;

import org.springframework.web.bind.annotation.*;
import projects.koko.tinder_backend.matches.dto.CreateMatchRequest;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Match createMatch(@RequestBody CreateMatchRequest request) {
        return matchService.createMatch(request.getProfileOne().getId(), request.getProfileOne().getId());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{profileId}")
    public List<Match> getAllMatches(@PathVariable("profileId") String profileId) {
        return matchService.getAllMatchesForProfile(profileId);
    }

    @DeleteMapping("/{matchId}")
    public void deleteMatchById(@PathVariable("matchId") String matchId){
        matchService.deleteMatchById(matchId);
    }

}
