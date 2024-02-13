package fr.insacvl.competencesapprentis.services.interfaces;

import fr.insacvl.competencesapprentis.beans.Competence;
import java.util.List;

public interface ICompetence {
    List<Competence> getAllCompetences();

    Competence getCompetenceById(Long id);

    void saveCompetence(Competence competence);

    void deleteCompetence(Long id);

    void save(Competence competence);
}
