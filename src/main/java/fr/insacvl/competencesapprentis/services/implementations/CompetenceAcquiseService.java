package fr.insacvl.competencesapprentis.services.implementations;

import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import fr.insacvl.competencesapprentis.beans.Mission;
import fr.insacvl.competencesapprentis.repository.CompetenceAcquiseRepos;
import fr.insacvl.competencesapprentis.services.interfaces.ICompetenceAcquise;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;


@Service
public class CompetenceAcquiseService  {

    @Autowired
    private CompetenceAcquiseRepos competenceRepository;

    public List<CompetenceAcquise> getAllCompetences() {
        return competenceRepository.findAll();
    }

    public CompetenceAcquise getCompetenceById(Long id) {
        return competenceRepository.findById(id).orElse(null);
    }

    public void saveCompetence(Competence competence) {

    }

    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }

    public void save(CompetenceAcquise competenceAcquise) {
        // Implémentation pour enregistrer la compétence acquise dans le repository
        competenceRepository.save(competenceAcquise);
    }

    public CompetenceAcquise findById(Long competenceId) {
        return null;
    }

    /*public void update(Mission mission, CompetenceAcquise competenceToUpdate) {
        long id=mission.getCompetencesAcquises().getId();
        CompetenceAcquise competencesAcquise = competenceRepository.getReferenceById(id);
        competencesAcquise.setNiveau(competenceToUpdate.getNiveau());
        competencesAcquise.setJustification(competenceToUpdate.getJustification());
        competenceRepository.save(competencesAcquise);

    }*/
    public void update(Mission mission, CompetenceAcquise competenceToUpdate) {
        Long id = competenceToUpdate.getId();

        if (id != null) {
            Optional<CompetenceAcquise> optionalCompetenceAcquise = competenceRepository.findById(id);

            if (optionalCompetenceAcquise.isPresent()) {
                CompetenceAcquise competencesAcquise = optionalCompetenceAcquise.get();
                competencesAcquise.setNiveau(competenceToUpdate.getNiveau());
                System.out.println("Rnew evee: " + competencesAcquise.getNiveau());

                competencesAcquise.setJustification(competenceToUpdate.getJustification());
                competenceRepository.save(competencesAcquise);
            } else {
                // Handle the case where CompetenceAcquise with the specified ID is not found
                // You can throw an exception, log a message, or handle it based on your requirements.
                // For example:
                // throw new NotFoundException("CompetenceAcquise with ID " + id + " not found");
            }
        } else {
            // Handle the case where the ID is null
            // You can throw an exception, log a message, or handle it based on your requirements.
            // For example:
            // throw new IllegalArgumentException("CompetenceAcquise ID cannot be null");
        }
    }

    public List<CompetenceAcquise> findCompOfMission(long idMission) {
        return competenceRepository.findByMissionId(idMission);
    }


}

