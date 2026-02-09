package projects.koko.tinder_backend.profiles.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import projects.koko.tinder_backend.profiles.Gender;

import java.time.LocalDateTime;

@Data
public class ProfileRequest {

//    @NotBlank(message= "User id is required")
//    private String userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;



    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private Integer age;

    @NotBlank(message = "Ethnicity is required")
    private String ethnicity;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Bio is required")
    private String bio;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String myersBriggsPersonalityType;

    private boolean isAi = false;
    private boolean isActive = true;
//    private LocalDateTime createdAt;
}
