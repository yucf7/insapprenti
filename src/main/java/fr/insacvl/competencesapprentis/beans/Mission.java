package fr.insacvl.competencesapprentis.beans;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;
    private String statut;
    private String objectif;
    private String resultat;
    private String commentaireTuteurEntreprise;
    private String commentaireTuteurInsa;

    @ManyToOne
    @JoinColumn(name = "apprenti_id")
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    private List<CompetenceAcquise> competencesAcquises;

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
