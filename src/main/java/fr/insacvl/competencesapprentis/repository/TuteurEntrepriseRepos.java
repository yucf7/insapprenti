package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuteurEntrepriseRepos extends JpaRepository<TuteurEntreprise, Long> {

    TuteurEntreprise findByEmail(String email);

}
