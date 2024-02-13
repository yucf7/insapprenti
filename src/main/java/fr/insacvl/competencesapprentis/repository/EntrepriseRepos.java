package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseRepos extends JpaRepository<Entreprise, Long> {
}
