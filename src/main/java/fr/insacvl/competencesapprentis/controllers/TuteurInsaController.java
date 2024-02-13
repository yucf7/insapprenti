package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.*;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.*;
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
public class TuteurInsaController {
        @Autowired
        private MissionService missionService2;
        @Autowired
        private TuteurInsaService tuteurInsaService;
        @Autowired
        private ApprentiService apprentiService;
        @Autowired
        private CompetenceService competenceService;
        @GetMapping("/tuteur-insa/dashboard")
        public String dashboardTuteurInsa(HttpServletRequest request,Model model) {
            String token = (String) request.getSession().getAttribute("token");
            long tuteurInsaId = (long) JwtUtils.extractUserId(token);
            Optional<TuteurInsa> tuteurInsa = tuteurInsaService.findById(tuteurInsaId);
            TuteurInsa actualTuteurInsa = tuteurInsa.orElse(null);
            model.addAttribute("tuteurInsa", actualTuteurInsa);
            return "/tuteurInsa/dashboard";
        }

        @RequiresRole(Roles.ROLE_TUTEUR_INSA)
        @GetMapping("/tuteur-insa/dashboard/listerEtudiants")
        public String listerEtudiantsTutInsa(Model model, HttpServletRequest request) {
            String token = (String) request.getSession().getAttribute("token");
            if(token != null) {
                long tuteurInsaId = (long) JwtUtils.extractUserId(token);
                Optional<TuteurInsa> tuteurInsa = tuteurInsaService.findById(tuteurInsaId);
                TuteurInsa actualTuteurInsa = tuteurInsa.orElse(null);
                List<Long> apprentisIds = missionService2.getMissionByTutInsaId(actualTuteurInsa);
                System.out.println("apprentisIds récupéré : " + apprentisIds);
                List<Apprenti> apprentis = new ArrayList<>();
                List<Mission> missions = new ArrayList<>();
                for (Long elem : apprentisIds
                ) {
                    Apprenti appernti = apprentiService.findById(elem);
                    apprentis.add(appernti);

                }
                System.out.println("apprentis liste : " + apprentis);
                model.addAttribute("apprentis", apprentis);
                return "tuteurInsa/listerEtudiants";
            }
            return "loginPage";

        }
        @RequiresRole(Roles.ROLE_TUTEUR_INSA)
        @GetMapping("/tuteur-insa/dashboard/listerMission/{id}")
        public String listerMissionsTutInsa(@PathVariable Long id, Model model, HttpServletRequest request) {
            String token = (String) request.getSession().getAttribute("token");
            if(token != null) {
                long tuteurInsaId = (long) JwtUtils.extractUserId(token);
                Optional<TuteurInsa> tuteurInsa = tuteurInsaService.findById(tuteurInsaId);
                Long apprentisId = id;
                System.out.println("apprentisIds récupéré : " + apprentisId);
                List<Mission> missions = missionService2.findByApprentiId(apprentisId);
                model.addAttribute("missions", missions);
                return "tuteurInsa/listerMissions";
            }
            return "loginPage";

        }
        @RequiresRole(Roles.ROLE_TUTEUR_INSA)
        @GetMapping("/tuteur-insa/dashboard/listerMission/detailsMission/{id}")
        public String showMissionDetails(@PathVariable Long id, Model model) {
            Mission mission = missionService2.findById(id);
            model.addAttribute("mission", mission);
            return "tuteurInsa/detailsMission";
        }


    @RequiresRole(Roles.ROLE_TUTEUR_INSA)
    @GetMapping("/tuteur-insa/dashboard/listerMission/commenter-mission/{id}")
    public String afficherPageCommentaire(@PathVariable("id") Long id, HttpServletRequest request, Model model){
        boolean b = tuteurInsaService.isAuthToComment(id, request);
        if(b){
            Mission mission = missionService2.findById(id);
            model.addAttribute("mission", mission);
            List<Competence> competences = competenceService.getAllCompetences();
            model.addAttribute("competences", competences);
            return "tuteurInsa/commenterMission";
        }
        return "tuteurInsa/listerMissions";
    }

    @RequiresRole(Roles.ROLE_TUTEUR_INSA)
    @PostMapping("/tuteur-insa/dashboard/listerMission/commenter-mission/{id}")
    public String commenterMission(@PathVariable("id") Long id, HttpServletRequest request, Model model, @ModelAttribute("mission") Mission missionFromForm){
        boolean b = tuteurInsaService.isAuthToComment(id, request);
        if(b){
            Mission mission = missionService2.findById(id);
            mission.setCommentaireTuteurInsa(missionFromForm.getCommentaireTuteurInsa());
            missionService2.save(mission);
        }
        return "redirect:/tuteur-insa/dashboard/listerEtudiants";
    }



    @RequiresRole(Roles.ROLE_TUTEUR_INSA)
    @GetMapping("/tuteur-insa/dashboard/profil/{id}")
    public String showProfil(@PathVariable Long id,HttpServletRequest request, Model model){
        String token = (String) request.getSession().getAttribute("token");
        if(token != null) {
            long tuteurInsaId = (long) JwtUtils.extractUserId(token);
            Optional<TuteurInsa> tuteurInsa = tuteurInsaService.findById(tuteurInsaId);
            TuteurInsa actualTuteurInsa = tuteurInsa.orElse(null);
            model.addAttribute("tuteurInsa", actualTuteurInsa);
            return "tuteurInsa/profil";
        }
        else {
            return "loginPage";
        }
    }

    }


