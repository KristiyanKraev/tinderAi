package projects.koko.tinder_backend.profiles;

import org.springframework.web.bind.annotation.*;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    private static final String CURRENT_USER_ID = "user";

    public ProfileController(ProfileRepository profileRepository, ProfileService profileService) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ProfileResponse getProfileById(@PathVariable("id") String id){
        return profileService.getProfileById(id);
    }
    @GetMapping("/user")
    public ProfileResponse getUserProfile(){
        return profileService.getProfileById(CURRENT_USER_ID);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/random")
    public ProfileResponse getRandomProfile() {
        return profileService.getRandomProfile();
    }

    public ProfileResponse createProfile()

}
