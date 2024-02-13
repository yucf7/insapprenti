package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepos extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
}
