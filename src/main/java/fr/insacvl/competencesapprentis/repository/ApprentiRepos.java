package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprentiRepos extends JpaRepository<Apprenti, Long> {

    Apprenti findByEmail(String email);

    List<Apprenti> findByTuteurInsaId(Long id);
}
