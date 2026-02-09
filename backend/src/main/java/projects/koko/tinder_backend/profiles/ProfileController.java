package projects.koko.tinder_backend.profiles;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;
import projects.koko.tinder_backend.user.User;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;

import java.util.Map;

@RestController
@RequestMapping( "/profiles")
public class ProfileController {

    private final ProfileService profileService;

//    private static final String CURRENT_USER_ID = "user";

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable("id") String id){
        ProfileResponse profile =  profileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }
//    @GetMapping("/user")
//    @ResponseStatus(HttpStatus.OK)
//    public ProfileResponse getUserProfile(){
//        return profileService.getProfileById(CURRENT_USER_ID);
//    }
    @CrossOrigin(origins = "*")
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getRandomProfile() {
        return profileService.getRandomProfile();
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<ProfileResponse> createProfile(@Valid @RequestBody ProfileRequest request, @AuthenticationPrincipal User user){
        ProfileResponse profile = profileService.createProfile(request, user.getId());
        return new ResponseEntity<>(profile,HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse updateProfile(@PathVariable("id") String id, @Valid @RequestBody ProfileRequest request){
        return profileService.updateUserProfile(id,request);
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteProfile(@PathVariable("id") String id){
        profileService.deleteProfileById(id);
        return ResponseEntity.noContent().build();

    }
}
