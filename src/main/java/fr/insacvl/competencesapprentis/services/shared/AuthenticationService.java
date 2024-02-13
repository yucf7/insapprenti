package fr.insacvl.competencesapprentis.services.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.AppUser;
import fr.insacvl.competencesapprentis.repository.AdminRepos;
import fr.insacvl.competencesapprentis.repository.TuteurEntrepriseRepos;
import fr.insacvl.competencesapprentis.repository.TuteurInsaRepos;
import fr.insacvl.competencesapprentis.repository.ApprentiRepos;
import fr.insacvl.competencesapprentis.beans.Admin;
import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;


@Service
public class AuthenticationService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepos adminRepos;
    private final TuteurEntrepriseRepos TuteurEntrepriseRepos;
    private final TuteurInsaRepos TuteurInsaRepos;
    private final ApprentiRepos ApprentiRepos;

    @Autowired
    public AuthenticationService(BCryptPasswordEncoder bCryptPasswordEncoder, AdminRepos adminRepos, TuteurEntrepriseRepos TuteurEntrepriseRepos, TuteurInsaRepos TuteurInsaRepos, ApprentiRepos ApprentiRepos) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminRepos = adminRepos;
        this.TuteurEntrepriseRepos = TuteurEntrepriseRepos;
        this.TuteurInsaRepos = TuteurInsaRepos;
        this.ApprentiRepos = ApprentiRepos;

    }

    public String encodePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    public  boolean matches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public AppUser login(String email, String password) {
        Admin admin = adminRepos.findByEmail(email);
        if(admin!=null && matches(password, admin.getMdp())){
            return admin;
        } else{
            TuteurEntreprise tuteurEntreprise = TuteurEntrepriseRepos.findByEmail(email);
            if(tuteurEntreprise!=null && matches(password, tuteurEntreprise.getMdp())){
                return tuteurEntreprise;
            } else {
                TuteurInsa tuteurInsa = TuteurInsaRepos.findByEmail(email);
                if(tuteurInsa!=null && matches(password, tuteurInsa.getMdp())){
                    return tuteurInsa;
                } else {
                    Apprenti apprenti = ApprentiRepos.findByEmail(email);
                    if(apprenti!=null && matches(password, apprenti.getMdp())){
                        return apprenti;
                    }
                }
            }

        }

        return null;
    }
}
