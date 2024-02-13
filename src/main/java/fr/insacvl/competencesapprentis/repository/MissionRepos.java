package fr.insacvl.competencesapprentis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.insacvl.competencesapprentis.beans.Mission;
import java.util.List;

public interface MissionRepos extends JpaRepository<Mission, Long> {
    List<Mission> findByApprentiId(Long apprentiId);
}
