package fr.insacvl.competencesapprentis.services.interfaces;

import fr.insacvl.competencesapprentis.beans.Entreprise;

import java.util.List;

public interface IEntreprise {
    Entreprise findById(Long entrepriseId);
    List<Entreprise> findAll();
    Entreprise save(Entreprise entreprise);
    Entreprise update(Long entrepriseId, Entreprise entreprise);
    void delete(Long entrepriseId);
}
