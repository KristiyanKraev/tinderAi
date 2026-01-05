package projects.koko.tinder_backend.profiles;

public record Profile (
        String id,
        String firstName,
        String lastName,
        Integer age,
        String ethnicity,
        Gender gender,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
){}