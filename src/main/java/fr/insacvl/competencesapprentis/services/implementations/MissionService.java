package fr.insacvl.competencesapprentis.services.implementations;

import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;
import fr.insacvl.competencesapprentis.services.interfaces.IMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.insacvl.competencesapprentis.repository.MissionRepos;

import java.util.ArrayList;
import java.util.List;

@Service
public class MissionService implements IMission {

    @Autowired
    private MissionRepos missionRepository;
    @Autowired
    private ApprentiService apprentiService;
    @Override
    public Mission findById(Long missionId) {
        return missionRepository.findById(missionId).orElse(null);
    }

    @Override
    public List<Mission> findByApprentiId(Long apprentiId) {
        return missionRepository.findByApprentiId(apprentiId);
    }

    @Override
    public List<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public Mission save(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public Mission update(Long missionId, Mission mission) {
        Mission existingMission = findById(missionId);
        if (existingMission != null) {
            // Mettez à jour les propriétés de la mission existante avec les nouvelles valeurs
            existingMission.setNom(mission.getNom());
            existingMission.setDescription(mission.getDescription());
            existingMission.setDateDebut(mission.getDateDebut());
            existingMission.setDateFin(mission.getDateFin());
            existingMission.setStatut(mission.getStatut());
            existingMission.setApprenti(mission.getApprenti());

            return missionRepository.save(existingMission);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long missionId) {
        missionRepository.deleteById(missionId);
    }
    @Override
    public List<Mission> getAllMissions(){
        return missionRepository.findAll();
    }
    /*
        @Override
        public List<Mission> getMissionByTutEnrepriseId() {
            return null;
        }
    */
    @Override
    public List<Long> getMissionByTutEnrepriseId(TuteurEntreprise TuteurEntreprise){
        System.out.println("Token récupéré : " + TuteurEntreprise);
        List<Apprenti> apprentis = apprentiService.findAll();
        List<Long> idApprentis = new ArrayList<>();
        for (Apprenti apprenti:apprentis) {
            if (apprenti.getTuteurEntreprise() == TuteurEntreprise){
                System.out.println("apprenti.getTuteurEntreprise récupéré : " + apprenti.getTuteurEntreprise());
                idApprentis.add(apprenti.getId());
            }

        }
        System.out.println("liste gggggggggggggggggggg : " + idApprentis);

        return idApprentis;
    }

    @Override
    public List<Long> getMissionByTutInsaId(TuteurInsa TuteurInsa){
        System.out.println("Token récupéré : " + TuteurInsa);
        List<Apprenti> apprentis = apprentiService.findAll();
        List<Long> idApprentis = new ArrayList<>();
        for (Apprenti apprenti:apprentis) {
            if (apprenti.getTuteurInsa() == TuteurInsa){
                System.out.println("apprenti.getTuteurEntreprise récupéré : " + apprenti.getTuteurInsa());
                idApprentis.add(apprenti.getId());
            }

        }
        System.out.println("liste gggggggggggggggggggg : " + idApprentis);

        return idApprentis;
    }

}
