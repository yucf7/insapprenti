package fr.insacvl.competencesapprentis.controllers;

import fr.insacvl.competencesapprentis.beans.*;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.*;
import fr.insacvl.competencesapprentis.services.shared.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ApprentiService apprentiService;
    private TuteurInsaService tuteurInsaService;
    private TuteurEntrepriseService tuteurEntrepriseService;
    private EntrepriseService entrepriseService;
    private AuthenticationService authenticationService;



    private AdminService adminService;
    public AdminController(ApprentiService apprentiService, EntrepriseService entrepriseService,
                           AuthenticationService authenticationService, TuteurInsaService tuteurInsaService,
                           TuteurEntrepriseService tuteurEntrepriseService, AdminService adminService){
        this.apprentiService = apprentiService;
        this.entrepriseService = entrepriseService;
        this.authenticationService = authenticationService;
        this.tuteurInsaService = tuteurInsaService;
        this.tuteurEntrepriseService = tuteurEntrepriseService;
        this.adminService = adminService;
    }



    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("")
    public String indexGet(Model model){
        List<Apprenti> apprentis = apprentiService.findAll();
        List<TuteurEntreprise> tuteursEntreprise = tuteurEntrepriseService.findAll();
        List<TuteurInsa> tuteursInsa = tuteurInsaService.findAll();

        List<Entreprise> entreprises = entrepriseService.findAll();

        long accounts_nbr = apprentis.size() + tuteursEntreprise.size() + tuteursInsa.size();

        model.addAttribute("accounts_nbr", accounts_nbr + "");
        model.addAttribute("entreprises_nbr", entreprises.size() + "");

        return "admin/index";
    }

    @RequiresRole(Roles.ROLE_ADMIN)

    @GetMapping("/add-user/apprenti")
    public String addApprentiGet(Model model, HttpServletRequest request) {
        List<Entreprise> entrepriseList = entrepriseService.findAll();
        model.addAttribute("entreprises", entrepriseList);
        return "admin/add-apprenti";
    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/add-user/tuteur-insa")
    public String addTuteurInsaGet(Model model) {
        List<String> departements = new ArrayList<String>();
        departements.add("STI");
        departements.add("MRI");
        model.addAttribute("departements", departements);

        return "admin/add-tuteur-insa";
    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/add-user/tuteur-entreprise")
    public String addTuteurEntrepriseGet(Model model) {
        List<Entreprise> entrepriseList = entrepriseService.findAll();
        model.addAttribute("entreprises", entrepriseList);

        return "admin/add-tuteur-entreprise";
    }


    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/add-entreprise")
    public String addEntrepriseGet() {
        return "admin/add-entreprise";
    }



    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/list-apprentis")
    public String addApprentiAccountsGet(Model model){
        List<Apprenti> apprentis = apprentiService.findAll();
        List<TuteurInsa> tuteursInsa = tuteurInsaService.findAll();
        List<TuteurEntreprise> tuteursEntreprise = tuteurEntrepriseService.findAll();

        model.addAttribute("apprentis", apprentis);
        model.addAttribute("tuteursInsa", tuteursInsa);
        model.addAttribute("tuteursEntreprise", tuteursEntreprise);
        return "admin/list-apprentis";
    }


    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/list-tuteurs-entreprise")
    public String listTuteursINSAGet(Model model){

        List<Apprenti> apprentis = apprentiService.findAll();
        List<TuteurEntreprise> tuteursEntreprise = tuteurEntrepriseService.findAll();


        model.addAttribute("apprentis", apprentis);
        model.addAttribute("tuteurs", tuteursEntreprise);
        return "admin/list-tuteurs-entreprise";
    }


    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/list-tuteurs-insa")
    public String listTuteursEntrepriseGet(Model model){
        List<Apprenti> apprentis = apprentiService.findAll();
        List<TuteurInsa> tuteursInsa = tuteurInsaService.findAll();


        model.addAttribute("apprentis", apprentis);
        model.addAttribute("tuteurs", tuteursInsa);
        return "admin/list-tuteurs-insa";
    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/modify-user/apprenti/{id}")
    public String modifyApprentiAccountGet(@PathVariable Long id, Model model) {
        Apprenti apprenti = apprentiService.findById(id);
        List<TuteurInsa> tuteursInsa = tuteurInsaService.findAll();
        List<TuteurEntreprise> tuteursEntreprise = tuteurEntrepriseService.findAll();
        List<Entreprise> entreprises = entrepriseService.findAll();
        List<TuteurEntreprise> filteredtuteursEntreprise =
                tuteursEntreprise.stream()
                        .filter(elet ->
                                elet.getEntreprise().getId()
                                        .equals(apprenti.getEntreprise().getId()))
                        .collect(Collectors.toCollection(ArrayList::new));

        model.addAttribute("apprenti", apprenti);
        model.addAttribute("tuteursInsa", tuteursInsa);
        model.addAttribute("tuteursEntreprise", filteredtuteursEntreprise);
        model.addAttribute("entreprises", entreprises);
        return "admin/modify-apprenti";
    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/modify-user/tuteur-insa/{id}")
    public String modifyTuteurINSAAccountGet(@PathVariable Long id, Model model) {
        Optional<TuteurInsa> tuteurInsaOptional = tuteurInsaService.findById(id);

        TuteurInsa tuteurInsa = tuteurInsaOptional.orElse(null);

        model.addAttribute("tuteur", tuteurInsa);

        return "admin/modify-tuteur-insa";
    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/modify-user/tuteur-entreprise/{id}")
    public String modifyTuteurEntreprisAccountGet(@PathVariable Long id, Model model) {
        Optional<TuteurEntreprise> tuteurEntrepriseOptional = tuteurEntrepriseService.findById(id);

        TuteurEntreprise tuteurEntreprise = tuteurEntrepriseOptional.orElse(null);
        List<Entreprise> entreprises = entrepriseService.findAll();

        model.addAttribute("tuteur", tuteurEntreprise);
        model.addAttribute("entreprises", entreprises);

        return "admin/modify-tuteur-entreprise";
    }


    @RequiresRole(Roles.ROLE_ADMIN)
    @PostMapping("/add-user")
    public ResponseEntity<Object> addUser(@RequestBody Map<String, Object> userData, @RequestParam(name = "dialog", required = false) Long dialogValue, Model model) {
        try {
            String nom = (String) userData.get("nom");
            String prenom = (String) userData.get("prenom");
            String mdp = (String) userData.get("mdp");
            String email = (String) userData.get("email");
            int role = (int) userData.get("role");
            Date date_creation = new Date();

            if (apprentiService.findByEmail(email) != null
                    || tuteurInsaService.findByEmail(email) != null
                    || tuteurEntrepriseService.findByEmail(email) != null) {

                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }


            // generate hash for password
            String hashedPassword = authenticationService.encodePassword(mdp);
            switch (role) {
                case 1:
                    Admin admin = new Admin();
                    admin.setNom(nom);
                    admin.setPrenom(prenom);
                    admin.setMdp(hashedPassword);
                    admin.setRole(1);
                    admin.setEmail(email);
                    admin.setDate_creation(date_creation);
                    Admin newAdmin = adminService.save(admin);
                    return ResponseEntity.ok(newAdmin);
                case 2: // apprenti account
                    String promo = (String) userData.get("promo");
                    String matricule = (String) userData.get("matricule");
                    String filliere = (String) userData.get("filliere");
                    int niveau = (int) userData.get("niveau");
                    if (apprentiService.findByEmail(email) != null) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }

                    Map<String, Object> entreprise = (Map<String, Object>) userData.get("entreprise");
                    String entrepriseIdString = (String) entreprise.get("id");
                    Long entrepriseId = Long.parseLong(entrepriseIdString);

                    Optional<Entreprise> chosenEntreprise = entrepriseService.findById(entrepriseId);
                    Entreprise actualEntreprise = chosenEntreprise.orElse(null);

                    Apprenti newApprenti = new Apprenti(matricule, promo, niveau, filliere, actualEntreprise);
                    newApprenti.setNom(nom);
                    newApprenti.setPrenom(prenom);
                    newApprenti.setMdp(hashedPassword);
                    newApprenti.setEmail(email);
                    newApprenti.setRole(role);

                    newApprenti.setDate_creation(date_creation);



                    Apprenti savedApprenti = apprentiService.save(newApprenti);
                    return ResponseEntity.ok(savedApprenti);
                case 3: // tuteur INSA account
                    String departement = (String) userData.get("departement");
                    if (tuteurInsaService.findByEmail(email) != null) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                    TuteurInsa newTuteurInsa = new TuteurInsa();
                    newTuteurInsa.setNom(nom);
                    newTuteurInsa.setPrenom(prenom);
                    newTuteurInsa.setMdp(hashedPassword);
                    newTuteurInsa.setEmail(email);
                    newTuteurInsa.setRole(role);
                    newTuteurInsa.setDepartement(departement);

                    newTuteurInsa.setDate_creation(date_creation);


                    TuteurInsa savedTuteurInsa = tuteurInsaService.save(newTuteurInsa);
                    return ResponseEntity.ok(savedTuteurInsa);
                case 4: // tuteur entreprise account
                    if (tuteurEntrepriseService.findByEmail(email) != null) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).build();
                    }
                    Map<String, Object> entrepriseTuteur = (Map<String, Object>) userData.get("entreprise");
                    String entrepriseTuteurIdString = (String) entrepriseTuteur.get("id");
                    Long entrepriseTuteurId = Long.parseLong(entrepriseTuteurIdString);

                    Optional<Entreprise> chosenEntrepriseTuteur = entrepriseService.findById(entrepriseTuteurId);
                    Entreprise actualTuteurEntreprise = chosenEntrepriseTuteur.orElse(null);

                    String post = (String) userData.get("post");
                    TuteurEntreprise newTuteurEntreprise = new TuteurEntreprise();
                    newTuteurEntreprise.setNom(nom);
                    newTuteurEntreprise.setPrenom(prenom);
                    newTuteurEntreprise.setMdp(hashedPassword);
                    newTuteurEntreprise.setEmail(email);
                    newTuteurEntreprise.setRole(role);
                    newTuteurEntreprise.setPost(post);
                    newTuteurEntreprise.setEntreprise(actualTuteurEntreprise);

                    newTuteurEntreprise.setDate_creation(date_creation);


                    if(dialogValue != null){

                        Apprenti apprenti = apprentiService.findById(dialogValue);
                        if (apprenti != null) {
                            apprenti.setTuteurEntreprise(newTuteurEntreprise);
                        }
                    }
                    TuteurEntreprise savedTuteurEntreprise = tuteurEntrepriseService.save(newTuteurEntreprise);

                    return ResponseEntity.ok(savedTuteurEntreprise);
                default:
                    Map<String, Object> response = new HashMap<>();
                    response.put("error", "no user created");
                    return ResponseEntity.ok(response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
    }


    @RequiresRole(Roles.ROLE_ADMIN)
    @PostMapping("/add-entreprise")
    public ResponseEntity<Entreprise> addEntreprise(@RequestBody Entreprise entreprise) {
        try {
            Entreprise createdEntreprise = entrepriseService.saveEntreprise(entreprise);
            return ResponseEntity.ok(createdEntreprise);
        } catch (Exception e) {
            return ResponseEntity.ok(new Entreprise());
        }
    }

    // delete tuteurInsa or tuteurEntreprise from apprenti
    @RequiresRole(Roles.ROLE_ADMIN)
    @DeleteMapping("/removeTuteur/{id}/{type}")
    public ResponseEntity<?> removeTuteur(@PathVariable Long id, @PathVariable int type) {
        // path var type = // 0 for tuteurInsa ; // 1 for tuteur entreprise
        try {
            Apprenti apprenti = apprentiService.findById(id);
            if (type == 0) {
                apprenti.setTuteurInsa(null);
            }
            if (type == 1) {
                apprenti.setTuteurEntreprise(null);
            }

            apprentiService.save(apprenti);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "removed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("error", "not removed");
            return ResponseEntity.badRequest().build();
        }
    }

    // put mapping to modify apprenti's tuteur INSA or Entreprise
    @RequiresRole(Roles.ROLE_ADMIN)
    @PutMapping("/setTuteur/{id}")
    public ResponseEntity<?> setTuteur(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            Integer tuteurInsaIdInteger = (Integer) body.get("tuteurInsa");
            Long tuteurInsaId = (tuteurInsaIdInteger != null) ? tuteurInsaIdInteger.longValue() : null;

            Integer tuteurEntrepriseIdInteger = (Integer) body.get("tuteurEntreprise");
            Long tuteurEntrepriseId = (tuteurEntrepriseIdInteger != null) ? tuteurEntrepriseIdInteger.longValue() : null;


            Apprenti apprenti = apprentiService.findById(id);

            // we check if one of em is not null to retrive full object from DB
            // if it's null it stills null
            if (tuteurInsaId != null) {
                Optional<TuteurInsa> tuteurInsa = tuteurInsaService.findById(tuteurInsaId);
                TuteurInsa actualTuteurInsa = tuteurInsa.orElse(null);
                apprenti.setTuteurInsa(actualTuteurInsa);
            }
            if (tuteurEntrepriseId != null) {
                Optional<TuteurEntreprise> tuteurEntreprise = tuteurEntrepriseService.findById(tuteurEntrepriseId);
                TuteurEntreprise actualTuteurEntreprise = tuteurEntreprise.orElse(null);
                apprenti.setTuteurEntreprise(actualTuteurEntreprise);

            }
            apprentiService.save(apprenti);
            Map<String, Object> response = new HashMap<>();
            response.put("success", "set");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("error", "not set");
            return ResponseEntity.badRequest().build();
        }
    }

    // putmapping to modify users
    @RequiresRole(Roles.ROLE_ADMIN)
    @PutMapping("/modify-user/{type}/{id}")

    public ResponseEntity<?> modifyUser(@PathVariable Long id, @PathVariable String type, @RequestBody Map<String, Object> body) {
        try {
            String email = (String) body.get("email");
            if (apprentiService.findByEmail(email) != null
                    || tuteurInsaService.findByEmail(email) != null
                    || tuteurEntrepriseService.findByEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }



            switch (type){
                case "apprenti":
                    Apprenti apprenti = apprentiService.findById(id);
                    for (Map.Entry<String, Object> entry : body.entrySet()) {
                        String field = entry.getKey();
                        Object value = entry.getValue();

                        if (value != null) {
                            switch (field) {
                                case "nom":
                                    apprenti.setNom((String) value);
                                    break;
                                case "prenom":
                                    apprenti.setPrenom((String) value);
                                    break;
                                case "email":
                                    apprenti.setEmail((String) value);
                                    break;
                                case "mdp":
                                    String hashedPassword = authenticationService.encodePassword((String) value);

                                    apprenti.setMdp(hashedPassword);
                                    break;
                                case "promo":
                                    apprenti.setPromo((String) value);
                                    break;
                                case "matricule":
                                    apprenti.setMatricule((String) value);
                                    break;
                                case "filliere":
                                    apprenti.setFilliere((String) value);
                                    break;
                                case "niveau":
                                    apprenti.setNiveau((Integer) value);
                                    break;
                                case "entreprise":
                                    Map<String, Object> entreprise = (Map<String, Object>) value;
                                    String entrepriseIdString = (String) entreprise.get("id");
                                    Long entrepriseId = Long.parseLong(entrepriseIdString);
                                    Optional<Entreprise> chosenEntreprise = entrepriseService.findById(entrepriseId);
                                    Entreprise actualEntreprise = chosenEntreprise.orElse(null);

                                    if (actualEntreprise != null) {
                                        apprenti.setEntreprise(actualEntreprise);
                                    }
                                    break;

                            }
                        }
                    }
                    apprentiService.save(apprenti);
                    break;
                case "tuteur-insa":
                    Optional<TuteurInsa> tuteurInsaOptional = tuteurInsaService.findById(id);
                    TuteurInsa tuteurInsa = tuteurInsaOptional.orElse(null);
                    if(tuteurInsa == null) break;
                    for (Map.Entry<String, Object> entry : body.entrySet()) {
                        String field = entry.getKey();
                        Object value = entry.getValue();

                        if (value != null) {
                            switch (field) {
                                case "nom":
                                    tuteurInsa.setNom((String) value);
                                    break;
                                case "prenom":
                                    tuteurInsa.setPrenom((String) value);
                                    break;
                                case "email":
                                    tuteurInsa.setEmail((String) value);
                                    break;
                                case "mdp":
                                    String hashedPassword = authenticationService.encodePassword((String) value);
                                    tuteurInsa.setMdp(hashedPassword);
                                    break;
                            }
                        }
                    }
                    tuteurInsaService.save(tuteurInsa);
                    break;
                case "tuteur-entreprise":
                    Optional<TuteurEntreprise> tuteurEntrepriseOptional = tuteurEntrepriseService.findById(id);
                    TuteurEntreprise tuteurEntreprise = tuteurEntrepriseOptional.orElse(null);

                    if(tuteurEntreprise == null) break;

                    for (Map.Entry<String, Object> entry : body.entrySet()) {
                        String field = entry.getKey();
                        Object value = entry.getValue();

                        if (value != null) {
                            switch (field) {
                                case "nom":
                                    tuteurEntreprise.setNom((String) value);
                                    break;
                                case "prenom":
                                    tuteurEntreprise.setPrenom((String) value);
                                    break;
                                case "email":
                                    tuteurEntreprise.setEmail((String) value);
                                    break;
                                case "mdp":
                                    String hashedPassword = authenticationService.encodePassword((String) value);
                                    tuteurEntreprise.setMdp(hashedPassword);
                                    break;
                                case "post":
                                    tuteurEntreprise.setPost((String) value);
                                    break;
                                case "entreprise":
                                    Map<String, Object> entreprise = (Map<String, Object>) value;
                                    String entrepriseIdString = (String) entreprise.get("id");
                                    Long entrepriseId = Long.parseLong(entrepriseIdString);
                                    Optional<Entreprise> chosenEntreprise = entrepriseService.findById(entrepriseId);
                                    Entreprise actualEntreprise = chosenEntreprise.orElse(null);

                                    if(actualEntreprise != null){
                                        tuteurEntreprise.setEntreprise(actualEntreprise);
                                    }
                                    break;
                            }
                        }
                    }
                    tuteurEntrepriseService.save(tuteurEntreprise);
                    break;

            }

            return ResponseEntity.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @RequiresRole(Roles.ROLE_ADMIN)
    @GetMapping("/accounts-infos")
    public ResponseEntity<?> getAccountsInfos(){
        List<Apprenti> apprentis = apprentiService.findAll();
        List<TuteurEntreprise> tuteursEntreprise = tuteurEntrepriseService.findAll();
        List<TuteurInsa> tuteursInsa = tuteurInsaService.findAll();

        double accounts_nbr = apprentis.size() + tuteursEntreprise.size() + tuteursInsa.size();
        Map<String, Object> response = new HashMap<>();

        response.put("accounts_nbr",accounts_nbr);
        response.put("apprentis", apprentis);
        response.put("tuteursEntreprise", tuteursEntreprise);
        response.put("tuteursInsa", tuteursInsa);

        return ResponseEntity.ok(response);

    }

    // route to add admin
    @PostMapping("/add-admin")
    public ResponseEntity<Object> addAdmin(@RequestBody Map<String, Object> userData) {
        try {
            String routePass = (String) userData.get("routePass");
            if(!routePass.equals("a*d*d*.*a*$*m*i/n")){
                return ResponseEntity.badRequest().build();
            }
            String nom = (String) userData.get("nom");
            String prenom = (String) userData.get("prenom");
            String mdp = (String) userData.get("mdp");
            String email = (String) userData.get("email");
            Date date_creation = new Date();

            if(apprentiService.findByEmail(email) != null
                    || tuteurInsaService.findByEmail(email) != null
                    || tuteurEntrepriseService.findByEmail(email) != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }




            // generate hash for password
            String hashedPassword = authenticationService.encodePassword(mdp);
                    Admin admin = new Admin();
                    admin.setNom(nom);
                    admin.setPrenom(prenom);
                    admin.setMdp(hashedPassword);
                    admin.setRole(1);
                    admin.setEmail(email);
                    admin.setDate_creation(date_creation);
                    Admin newAdmin = adminService.save(admin);
                    return ResponseEntity.ok(newAdmin);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
    }



}