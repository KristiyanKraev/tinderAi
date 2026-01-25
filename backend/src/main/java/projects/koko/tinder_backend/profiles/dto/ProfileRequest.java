package projects.koko.tinder_backend.profiles.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import projects.koko.tinder_backend.profiles.Gender;

@Data
public class ProfileRequest {
    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private Integer age;

    private String ethnicity;

    private Gender gender;

    private String bio;

    private String myersBriggsPersonalityType;
}
