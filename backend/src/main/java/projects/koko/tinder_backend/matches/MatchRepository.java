package projects.koko.tinder_backend.matches;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository  extends JpaRepository<Match, String> {

}
