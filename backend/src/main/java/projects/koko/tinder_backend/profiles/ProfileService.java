package projects.koko.tinder_backend.profiles;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;
import projects.koko.tinder_backend.profiles.mapper.ProfileMapper;
import projects.koko.tinder_backend.user.User;
import projects.koko.tinder_backend.utils.ProfileExistsException;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    public ProfileResponse getProfileById(String id){
        return profileRepository.findById(id)
                .map(profileMapper::toProfileResponse)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id : " + id));
    }
    public ProfileResponse getRandomProfile(){
        return profileRepository.getRandomProfile()
                .map(profileMapper::toProfileResponse)
                .orElseThrow(() -> new ProfileNotFoundException("No profiles available "));
    }

    public ProfileResponse createProfile(ProfileRequest request, String userId){
        // Check if a profile already exists for this user
        profileRepository.findByUserId(userId).ifPresent(profile -> {
            throw new ProfileExistsException("Profile already exists for this user");
        });
        
        Profile newProfile = profileMapper.toProfileFromRequest(request);
        User user = new User();
        user.setId(userId);
        newProfile.setUser(user);
        newProfile.setIsAi(false);
        newProfile.setIsActive(true);

        Profile savedProfile = profileRepository.save(newProfile);
        return profileMapper.toProfileResponse(savedProfile);
    }

    public ProfileResponse updateUserProfile(String id, ProfileRequest request){
        return profileRepository.findById(id)
                .map(profile -> {
                    profileMapper.updateProfileFromRequest(request,profile);
                    Profile updatedProfile = profileRepository.save(profile);
                    return profileMapper.toProfileResponse(updatedProfile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id : " + id));
    }

    @Transactional
    public void deleteProfileById(String id){
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id: " + id));

        if(profile.getUser() != null){
            User user = profile.getUser();
            user.setProfile(null);
        }
        profileRepository.delete(profile);
    }
}
