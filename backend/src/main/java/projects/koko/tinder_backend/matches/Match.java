package projects.koko.tinder_backend.matches;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projects.koko.tinder_backend.profiles.Profile;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "profile_one")
    private Profile profileOne;

    @ManyToOne
    @JoinColumn(name = "profile_two")
    private Profile profileTwo;

    private LocalDateTime matchedAt = LocalDateTime.now();

    public Match(Profile liker, Profile liked){
        this.profileOne = liker;
        this.profileTwo = liked;
    }
}
