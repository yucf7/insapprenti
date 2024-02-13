package fr.insacvl.competencesapprentis.services.interfaces;


import fr.insacvl.competencesapprentis.beans.Competence;
import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import java.util.List;

public interface ICompetenceAcquise {
    List<CompetenceAcquise> getAllCompetences();

    CompetenceAcquise getCompetenceById(Long id);

    void saveCompetence(CompetenceAcquise competence);

    void deleteCompetence(Long id);

    void save(CompetenceAcquise competenceAcquise);

    CompetenceAcquise findById(Long competenceId);

}
