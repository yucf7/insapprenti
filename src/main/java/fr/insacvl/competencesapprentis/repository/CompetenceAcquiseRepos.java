package fr.insacvl.competencesapprentis.repository;

import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetenceAcquiseRepos extends JpaRepository<CompetenceAcquise, Long> {
        List<CompetenceAcquise> findByMissionId(long idMission);
}
