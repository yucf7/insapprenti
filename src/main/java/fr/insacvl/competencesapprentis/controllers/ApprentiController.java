package fr.insacvl.competencesapprentis.controllers;


import com.itextpdf.text.DocumentException;
import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.*;
import fr.insacvl.competencesapprentis.services.interfaces.IMission;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class ApprentiController {
    @Autowired
    private IMission missionService;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private CompetenceAcquiseService competenceAcquiseService;
    @Autowired
    private MissionService missionService2;
    @Autowired
    private PdfGenerationService pdfGenerationService;
    @Autowired
    private TuteurEntrepriseService tuteurEntrepriseService;
    @Autowired
    private ApprentiService apprentiService;


    @GetMapping
    public List<Apprenti> getAllApprentis() {
        return apprentiService.findAll();
    }

    @GetMapping("/{id}")
    public Apprenti getApprentiById(@PathVariable Long id) {
        return apprentiService.findById(id);
    }

    @PostMapping
    public void saveApprenti(@RequestBody Apprenti apprenti) {
        apprentiService.save(apprenti);
    }

    @DeleteMapping("/{id}")
    public void deleteApprenti(@PathVariable Long id) {
        apprentiService.delete(id);
    }

    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard")
    public String showDashboard(Model model, HttpServletRequest request){
        String token = (String) request.getSession().getAttribute("token");
        long apprenticeId = (long) JwtUtils.extractUserId(token);
        Apprenti apprenti = apprentiService.findById(apprenticeId);
        model.addAttribute("apprenti",apprenti);

        return "apprenti/dashboard";
    }

    @GetMapping("/apprenti/dashboard/addMission")
    public String showMissionForm(Model model) {
        // Retrieve the user's role from the session


        model.addAttribute("mission", new Mission());
        List<Competence> competences = competenceService.getAllCompetences();
        model.addAttribute("competences", competences);

        return "apprenti/addMission";
    }

    @Transactional
    @PostMapping("/apprenti/dashboard/addMission")
    public String addMission(@ModelAttribute Mission mission,
                             @RequestParam Map<String, String> niveauMap,
                             @RequestParam("competence.id") List<Integer> competences,
                             @RequestParam("justification") List<String> justifications,
                             HttpServletRequest request) {
        List<Integer> niveaux = niveauMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("niveau_"))
                .map(entry -> Integer.parseInt(entry.getValue()))
                .collect(Collectors.toList());
        List<CompetenceAcquise> competencesAcquises = new ArrayList<>();
        System.out.println("Received niveaux: " + niveaux);
        System.out.println("Received justifications: " + justifications);

        // Check if niveaux is not empty
        if (!niveaux.isEmpty()) {
            for (int i = 0; i < niveaux.size(); i++) {
                CompetenceAcquise competenceAcquise = new CompetenceAcquise();
                competenceAcquise.setNiveau(niveaux.get(i));
                long competenceId = competences.get(i);
                Competence competence = competenceService.getCompetenceById(competenceId);
                competenceAcquise.setCompetence(competence);
                // Check if justifications list has enough elements
                if (i < justifications.size()) {
                    competenceAcquise.setJustification(justifications.get(i));
                }

                //competenceAcquise.setCompetence(mission.getCompetence());
                competenceAcquise.setMission(mission);

                competenceAcquiseService.save(competenceAcquise);
                competencesAcquises.add(competenceAcquise);

            }
        }

        // Update the mission with the list of CompetenceAcquise
        mission.setCompetencesAcquises(competencesAcquises);
        String token = (String) request.getSession().getAttribute("token");
        long apprenticeId = (long) JwtUtils.extractUserId(token);
        Apprenti apprenti = apprentiService.findById(apprenticeId);
        // Save the mission in the database
        mission.setApprenti(apprenti);
        missionService.save(mission);
        return "redirect:/apprenti/dashboard/listerMission";

    }

    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/listerMission")
    public String listerMission(Model model,HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        System.out.println("Token récupéré : " + token);
        long apprenticeId = (long) JwtUtils.extractUserId(token);
        System.out.println("Id récupéré : " + apprenticeId);
        List<Mission> missions = missionService2.findByApprentiId(apprenticeId);
        model.addAttribute("missions", missions);
        return "apprenti/listerMissions";

    }

    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/detailsMission/{id}")
    public String showMissionDetails(@PathVariable Long id, Model model) {
        Mission mission = missionService2.findById(id);
        model.addAttribute("mission", mission);
        return "apprenti/detailsMission";
    }
    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/modifierMission/{id}")
    public String modifierMission(@PathVariable Long id, Model model) {
        // Récupérer la mission à modifier depuis la base de données
        Mission mission = missionService2.findById(id);
        // Ajouter la mission au modèle pour la page de modification
        model.addAttribute("mission", mission);
        List<Competence> competences = competenceService.getAllCompetences();
        model.addAttribute("competences", competences);
        return "apprenti/modifyMission";  // Le nom de la nouvelle page pour la modification

    }
    @RequiresRole(Roles.ROLE_APPRENTI)
    @PostMapping("/apprenti/dashboard/modifierMission/{id}")
    public String modifierMission(@ModelAttribute("mission") Mission mission) {
        Mission existingMission = missionService.findById(mission.getId());

        existingMission.setNom(mission.getNom());
        existingMission.setDescription(mission.getDescription());
        existingMission.setDateDebut(mission.getDateDebut());
        existingMission.setDateFin(mission.getDateFin());
        existingMission.setObjectif(mission.getObjectif());
        existingMission.setResultat(mission.getResultat());

        List<CompetenceAcquise> existingCompetencesAcquises = existingMission.getCompetencesAcquises();
        for (int i = 0; i < existingCompetencesAcquises.size(); i++) {
            CompetenceAcquise existingCompetence = existingCompetencesAcquises.get(i);
            CompetenceAcquise submittedCompetence = mission.getCompetencesAcquises().get(i);

            existingCompetence.setNiveau(submittedCompetence.getNiveau());
            //Competence res = competenceService.findById(submittedCompetence.getId());
            // String nameCompetence
            existingCompetence.setJustification(submittedCompetence.getJustification());
            existingCompetence.setCompetence(submittedCompetence.getCompetence());
        }


        // Save the updated mission (including associated competencesAcquises)
        missionService.save(existingMission);

        return "redirect:/apprenti/dashboard/listerMission";
    }

    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/exporterPDF/{id}")
    public ResponseEntity<byte[]> exporterMissionEnPDF(@PathVariable Long id) {
        try {
            Mission mission = missionService2.findById(id);

            byte[] pdfBytes = pdfGenerationService.generateMissionPDF(mission);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Vosmissions.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/profil/{id}")
    public String showProfil(@PathVariable Long id,Model model){
        Apprenti apprenti = apprentiService.findById(id);
        model.addAttribute("apprenti", apprenti);
        return "apprenti/profil";
    }




    @RequiresRole(Roles.ROLE_APPRENTI)
    @GetMapping("/apprenti/dashboard/visualiserNote")
    public String showNote(Model model,HttpServletRequest request){

        String token = (String) request.getSession().getAttribute("token");
        System.out.println("Token récupéré : " + token);
        long apprenticeId = (long) JwtUtils.extractUserId(token);
        System.out.println("Id récupéré : " + apprenticeId);
        List<Mission> missions = missionService2.findByApprentiId(apprenticeId);
        model.addAttribute("missions", missions);


        return "apprenti/visualiserNote";
    }
}


