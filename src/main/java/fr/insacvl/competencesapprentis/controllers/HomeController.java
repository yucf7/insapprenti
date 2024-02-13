package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.services.implementations.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String home() {
        return "index";
    }

}
