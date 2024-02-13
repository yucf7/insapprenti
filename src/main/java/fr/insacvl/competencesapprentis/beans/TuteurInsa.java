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
public class TuteurInsa extends User implements AppUser{
    private String departement;
}
