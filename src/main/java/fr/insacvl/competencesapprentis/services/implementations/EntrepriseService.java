package fr.insacvl.competencesapprentis.services.implementations;


import fr.insacvl.competencesapprentis.beans.Entreprise;
import fr.insacvl.competencesapprentis.repository.EntrepriseRepos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {

    private EntrepriseRepos entrepriseRepos;

    public EntrepriseService(EntrepriseRepos entrepriseRepos) {
        this.entrepriseRepos = entrepriseRepos;
    }

    public List<Entreprise> findAll() {
        return entrepriseRepos.findAll();
    }

    public Entreprise saveEntreprise(Entreprise entreprise) {
        return entrepriseRepos.save(entreprise);
    }

    public Optional<Entreprise> findById(Long id) {
        return entrepriseRepos.findById(id);
    }

    public Entreprise save(Entreprise entreprise) {
        return entrepriseRepos.save(entreprise);
    }

    public Entreprise update(Long entrepriseId, Entreprise entreprise) {
        Optional<Entreprise> optionalEntreprise = findById(entrepriseId);
        Entreprise existingEntreprise = optionalEntreprise.orElse(null);
        if (existingEntreprise != null) {
            existingEntreprise.setNom(entreprise.getNom());
            existingEntreprise.setSiege(entreprise.getSiege());
            existingEntreprise.setAdresse(entreprise.getAdresse());

            return entrepriseRepos.save(existingEntreprise);
        } else {
            return null;
        }
    }

    public void delete(Long entrepriseId) {
        entrepriseRepos.deleteById(entrepriseId);
    }
}
