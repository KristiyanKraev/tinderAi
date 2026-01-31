package projects.koko.tinder_backend.matches.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import projects.koko.tinder_backend.profiles.Profile;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateMatchRequest {

    @Column(name = "profile_one_id")
    @ManyToOne
    private Profile profileOne;

    @Column(name = "profile_two_id")
    @ManyToOne
    private Profile profileTwo;

    private LocalDateTime matchedAt = LocalDateTime.now();

}
