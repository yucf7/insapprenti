package fr.insacvl.competencesapprentis.beans;


import fr.insacvl.competencesapprentis.security.AppUser;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Admin extends User implements AppUser {


}
