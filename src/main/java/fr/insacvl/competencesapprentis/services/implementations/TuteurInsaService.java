package fr.insacvl.competencesapprentis.services.implementations;


import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;
import fr.insacvl.competencesapprentis.repository.TuteurInsaRepos;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.interfaces.ITuteurInsa;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurInsaService {

    @Autowired
    TuteurInsaRepos tuteurInsaRepos;
    @Autowired
    MissionService missionService;


    public TuteurInsaService (TuteurInsaRepos tuteurInsaRepos){
        this.tuteurInsaRepos = tuteurInsaRepos;
    }

    public TuteurInsa save(TuteurInsa tuteurInsa){
        return tuteurInsaRepos.save(tuteurInsa);
    }

    public TuteurInsa findByEmail(String email){
        return tuteurInsaRepos.findByEmail(email);
    }

    public List<TuteurInsa> findAll(){
        return tuteurInsaRepos.findAll();
    }

    public Optional<TuteurInsa> findById(Long id){
        return tuteurInsaRepos.findById(id);
    }

    public boolean isAuthToComment(Long idMission, HttpServletRequest request){
        String token = request.getSession().getAttribute("token").toString();
        Integer idTuteurInsa = JwtUtils.extractUserId(token);
        TuteurInsa tuteurINSA= tuteurInsaRepos.findById(idTuteurInsa.longValue()).get();
        Mission mission = missionService.findById(idMission);
        if(mission != null){
            Apprenti apprenti = mission.getApprenti();
            if(apprenti != null){
                TuteurInsa tuteurInsaAppr = apprenti.getTuteurInsa();
                if(tuteurInsaAppr != null){
                    if(tuteurInsaAppr.equals(tuteurINSA)){
                        return true;
                    }
                }
            }
        }

        return false;
    }



}
