package fr.insacvl.competencesapprentis.beans;

import fr.insacvl.competencesapprentis.beans.Mission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CompetenceAcquise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int niveau;
    private String justification;

    private boolean validee = false;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
}
