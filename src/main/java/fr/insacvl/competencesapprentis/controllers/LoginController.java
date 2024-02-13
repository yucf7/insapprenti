package fr.insacvl.competencesapprentis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.insacvl.competencesapprentis.beans.Admin;
import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.beans.TuteurEntreprise;
import fr.insacvl.competencesapprentis.beans.TuteurInsa;
import fr.insacvl.competencesapprentis.security.AppUser;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.shared.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("")
    public String showLoginPage() {
        return "loginPage";
    }

    @PostMapping("")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletRequest request) {
        AppUser user = authenticationService.login(email, password);
        if(user!=null){
            String role  = "";
            String redirect = "";
            if(user.getClass() == Admin.class){
                role = Roles.ROLE_ADMIN.toString();
                redirect = "redirect:/admin";
            } else {
                if(user.getClass() == TuteurEntreprise.class){
                    role = Roles.ROLE_TUTEUR_ENTREPRISE.toString();
                    redirect = "redirect:/tuteur-entreprise/dashboard";
                } else {
                    if(user.getClass() == TuteurInsa.class){
                        role = Roles.ROLE_TUTEUR_INSA.toString();
                        redirect = "redirect:/tuteur-insa/dashboard";
                    } else {
                        if(user.getClass() == Apprenti.class){


                            role = Roles.ROLE_APPRENTI.toString();
                            redirect = "redirect:/apprenti/dashboard";
                        }
                    }
                }
            }

            String token=JwtUtils.generateToken(role,user.getId());
            request.getSession().setAttribute("token", token);
            return redirect;
        }
        return "redirect:/login";
    }

}
