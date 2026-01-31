package projects.koko.tinder_backend.likes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import projects.koko.tinder_backend.profiles.Profile;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "liker_id")
    private Profile liker;

    @ManyToOne
    @JoinColumn(name="liked_id")
    private Profile liked;

    @Column(name = "liked_at")
    private LocalDateTime likedAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isMatch = false;

}
