package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.services.interfaces.ICompetence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/competences")
public class CompetenceController {

    @Autowired
    private ICompetence competenceService;

    @GetMapping
    public String listCompetences(Model model) {
        // Récupérer la liste des compétences depuis le service
        List<Competence> competences = competenceService.getAllCompetences();

        // Ajouter la liste de compétences au modèle Thymeleaf
        model.addAttribute("competences", competences);

        return "competence/list";
    }

    @GetMapping("/add")
    public String showAddCompetenceForm(Model model) {
        model.addAttribute("competence", new Competence());
        return "competence/add";
    }

    @PostMapping("/add")
    public String addCompetence(@ModelAttribute Competence competence) {
        // Enregistrer la nouvelle compétence dans la base de données
        competenceService.save(competence);
        return "redirect:/competences";
    }
}
