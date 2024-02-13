package fr.insacvl.competencesapprentis.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import fr.insacvl.competencesapprentis.security.AppUser;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Apprenti extends User implements AppUser{
    @Id
    private Long id;
    private String matricule;
    private String promo;
    private int niveau;
    private String filliere;

    @ManyToOne
    private TuteurInsa tuteurInsa;

    @ManyToOne
    private TuteurEntreprise tuteurEntreprise;

    @ManyToOne
    private Entreprise entreprise;



    public Apprenti(String matricule, String promo, int niveau, String filliere, Entreprise entreprise) {
        this.matricule = matricule;
        this.promo = promo;
        this.niveau = niveau;
        this.filliere = filliere;
        this.entreprise = entreprise;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
