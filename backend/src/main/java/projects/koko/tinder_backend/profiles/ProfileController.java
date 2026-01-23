package projects.koko.tinder_backend.profiles;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;

import java.util.Map;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService profileService;

    private static final String CURRENT_USER_ID = "user";

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getProfileById(@PathVariable("id") String id){
        return profileService.getProfileById(id);
    }
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getUserProfile(){
        return profileService.getProfileById(CURRENT_USER_ID);
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getRandomProfile() {
        return profileService.getRandomProfile();
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(@Valid @RequestBody ProfileRequest request){
       return profileService.createProfile(request);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse updateProfile(@PathVariable("id") String id, @Valid @RequestBody ProfileRequest request){
        return profileService.updateUserProfile(id,request);
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,String>> deleteProfile(@PathVariable("id") String id){
       try{
        profileService.deleteProfileById(id);
        return ResponseEntity.noContent().build();
       } catch (ProfileNotFoundException e){
           return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(Map.of("message","Profile not found with id: " + id));
       }
    }

}
