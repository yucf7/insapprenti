package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.*;
import fr.insacvl.competencesapprentis.services.interfaces.IMission;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TuteurEntrepriseController {
    @Autowired
    private MissionService missionService2;
    @Autowired
    private TuteurEntrepriseService tuteurEntrepriseService;
    @Autowired
    private ApprentiService apprentiService;
    @Autowired
    private CompetenceService competenceService;
    @GetMapping("/tuteur-entreprise/dashboard")
    public String dashboardTuteurEntreprise(HttpServletRequest request,Model model) {
        String token = (String) request.getSession().getAttribute("token");
        long tuteurEntrepriseId = (long) JwtUtils.extractUserId(token);
        Optional<TuteurEntreprise> tuteurEntreprise = tuteurEntrepriseService.findById(tuteurEntrepriseId);
        TuteurEntreprise actualTuteurEntreprise = tuteurEntreprise.orElse(null);
        model.addAttribute("tuteurEntreprise", actualTuteurEntreprise);

        return "/tuteurEntreprise/dashboard";
    }

    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @GetMapping("/tuteur-entreprise/dashboard/listerEtudiants")
    public String listerEtudiantsTutEntreprise(Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if(token != null) {
            long tuteurEntrepriseId = (long) JwtUtils.extractUserId(token);
            Optional<TuteurEntreprise> tuteurEntreprise = tuteurEntrepriseService.findById(tuteurEntrepriseId);
            TuteurEntreprise actualTuteurEntreprise = tuteurEntreprise.orElse(null);
            List<Long> apprentisIds = missionService2.getMissionByTutEnrepriseId(actualTuteurEntreprise);
            System.out.println("apprentisIds récupéré : " + apprentisIds);
            List<Apprenti> apprentis = new ArrayList<>();
            for (Long elem : apprentisIds
            ) {
                Apprenti appernti = apprentiService.findById(elem);
                apprentis.add(appernti);

            }
            System.out.println("apprentis liste : " + apprentis);
            model.addAttribute("apprentis", apprentis);
            return "tuteurEntreprise/listerEtudiants";
        }
        return "loginPage";

    }

    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @GetMapping("/tuteur-entreprise/dashboard/listerMission/{id}")
    public String listerMissionsTutEntreprise(@PathVariable Long id, Model model, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if(token != null) {
            long tuteurEntrepriseId = (long) JwtUtils.extractUserId(token);
            Optional<TuteurEntreprise> tuteurEntreprise = tuteurEntrepriseService.findById(tuteurEntrepriseId);
            Long apprentisId = id;
            System.out.println("apprentisIds récupéré : " + apprentisId);
            List<Mission> missions = missionService2.findByApprentiId(apprentisId);
            model.addAttribute("missions", missions);
            return "tuteurEntreprise/listerMissions";
        }
        return "loginPage";

    }



    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @GetMapping("/tuteur-entreprise/dashboard/listerMission/detailsMission/{id}")
    public String showMissionDetails(@PathVariable Long id, Model model) {
        Mission mission = missionService2.findById(id);
        model.addAttribute("mission", mission);
        return "tuteurEntreprise/detailsMission";
    }

    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @GetMapping("/tuteur-entreprise/dashboard/listerMission/commenter-mission/{id}")
    public String afficherPageCommentaire(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        boolean b = tuteurEntrepriseService.isAuthToComment(id, request);
        if(b){
            Mission mission = missionService2.findById(id);
            model.addAttribute("mission", mission);
            List<Competence> competences = competenceService.getAllCompetences();
            model.addAttribute("competences", competences);
            return "tuteurEntreprise/commenterMission";
        }
        return "tuteurEntreprise/listerMissions";
    }

    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @PostMapping("/tuteur-entreprise/dashboard/listerMission/commenter-mission/{id}")
    public String commenterMission(@PathVariable("id") Long id, HttpServletRequest request, Model model, @ModelAttribute("mission") Mission missionFromForm){
        boolean b = tuteurEntrepriseService.isAuthToComment(id, request);
        if(b){
            Mission mission = missionService2.findById(id);
            mission.setCommentaireTuteurEntreprise(missionFromForm.getCommentaireTuteurEntreprise());
            missionService2.save(mission);
        }
        return "redirect:/tuteur-entreprise/dashboard/listerEtudiants";
    }
    @RequiresRole(Roles.ROLE_TUTEUR_ENTREPRISE)
    @GetMapping("/tuteur-entreprise/dashboard/profil/{id}")
    public String showProfil(@PathVariable Long id,HttpServletRequest request, Model model){
        String token = (String) request.getSession().getAttribute("token");
        if(token != null) {
            long tuteurEntrepriseId = (long) JwtUtils.extractUserId(token);
            Optional<TuteurEntreprise> tuteurEntreprise = tuteurEntrepriseService.findById(tuteurEntrepriseId);
            TuteurEntreprise actualTuteurEntreprise = tuteurEntreprise.orElse(null);
            model.addAttribute("tuteurEntreprise", actualTuteurEntreprise);
            return "tuteurEntreprise/profil";
        }
        else {
            return "loginPage";
        }
    }


}
