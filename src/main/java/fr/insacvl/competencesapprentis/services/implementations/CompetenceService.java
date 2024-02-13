package fr.insacvl.competencesapprentis.services.implementations;


import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.repository.CompetenceRepos;
import fr.insacvl.competencesapprentis.services.interfaces.ICompetence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
public class CompetenceService implements ICompetence {

    @Autowired
    private CompetenceRepos competenceRepository;

    public Competence findById(Long id) {
        return competenceRepository.findById(id).orElse(null);
    }

    public List<Competence> getAllCompetences() {
        // Utilisez le repository pour récupérer toutes les compétences depuis la base de données
        return competenceRepository.findAll();
    }

    public Competence getCompetenceById(Long id) {
        Optional<Competence> competenceOptional = competenceRepository.findById(id);
        return competenceOptional.orElseThrow(() -> new NotFoundException("Competence not found with id: " + id));
    }


    public void saveCompetence(Competence competence) {

    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);

    }

    public void save(Competence competence) {

    }

    public List <Competence> findAll(){
        return competenceRepository.findAll();
    }


}
