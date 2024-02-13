package fr.insacvl.competencesapprentis.services.implementations;


import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;
import fr.insacvl.competencesapprentis.repository.TuteurEntrepriseRepos;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.services.interfaces.ITuteurEntreprise;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurEntrepriseService {

    @Autowired
    private TuteurEntrepriseRepos tuteurEntrepriseRepos;
    @Autowired
    private MissionService missionService;
    public TuteurEntrepriseService(TuteurEntrepriseRepos tuteurEntrepriseRepos) {
        this.tuteurEntrepriseRepos = tuteurEntrepriseRepos;
    }

    public TuteurEntreprise save (TuteurEntreprise tuteurEntreprise){
        return tuteurEntrepriseRepos.save(tuteurEntreprise);
    }

    public TuteurEntreprise findByEmail(String email){
        return tuteurEntrepriseRepos.findByEmail(email);
    }

    public List<TuteurEntreprise> findAll(){
        return tuteurEntrepriseRepos.findAll();
    }

    public Optional<TuteurEntreprise> findById(Long id){
        return tuteurEntrepriseRepos.findById(id);
    }

    public boolean isAuthToComment(Long idMission, HttpServletRequest request){
        String token = request.getSession().getAttribute("token").toString();
        Integer idTuteurEntreprise = JwtUtils.extractUserId(token);
        TuteurEntreprise tuteurEntreprise = tuteurEntrepriseRepos.findById(idTuteurEntreprise.longValue()).get();
        Mission mission = missionService.findById(idMission);
        if(mission != null){
            Apprenti apprenti = mission.getApprenti();
            if(apprenti != null){
                TuteurEntreprise tuteurEntAppr = apprenti.getTuteurEntreprise();
                if(tuteurEntAppr != null){
                    if(tuteurEntAppr.equals(tuteurEntreprise)){
                        return true;
                    }
                }
            }
        }

        return false;
    }



}



