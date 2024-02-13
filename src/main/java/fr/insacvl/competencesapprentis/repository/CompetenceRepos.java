package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepos extends JpaRepository<Competence, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}
