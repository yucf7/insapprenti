package fr.insacvl.competencesapprentis.services.interfaces;

import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;

import java.util.List;

public interface IMission {
    Mission findById(Long missionId);
    List<Mission> findByApprentiId(Long apprentiId);
    List<Mission> findAll();
    Mission save(Mission mission);
    Mission update(Long missionId, Mission mission);
    void delete(Long missionId);

    List<Mission> getAllMissions();

  //  List<Mission> getMissionByTutEnrepriseId();

    List<Long> getMissionByTutEnrepriseId(TuteurEntreprise TuteurEntreprise);

    List<Long> getMissionByTutInsaId(TuteurInsa TuteurInsa);
}
