package projects.koko.tinder_backend.profiles.mapper;

import org.mapstruct.*;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {

    @Mapping(target = "userId", source = "user.id")
    ProfileResponse toProfileResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isAi", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Profile toProfileFromRequest(ProfileRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isAi", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateProfileFromRequest(ProfileRequest request, @MappingTarget Profile profile);
}
