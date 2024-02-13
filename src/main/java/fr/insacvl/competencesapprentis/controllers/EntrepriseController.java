package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.Entreprise;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.EntrepriseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("entreprise")
public class EntrepriseController {

    private EntrepriseService entrepriseService;

    public EntrepriseController(EntrepriseService entrepriseService){
        this.entrepriseService = entrepriseService;

    }
    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/get-all")
    public ResponseEntity<List<Entreprise>> getAllEntreprises(){
        List<Entreprise> allEntreprises = entrepriseService.findAll();
        return ResponseEntity.ok(allEntreprises);

    }
}
