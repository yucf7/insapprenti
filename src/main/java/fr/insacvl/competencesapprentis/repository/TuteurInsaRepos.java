package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.TuteurInsa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuteurInsaRepos extends JpaRepository<TuteurInsa, Long> {

    TuteurInsa findByEmail (String email);
}
