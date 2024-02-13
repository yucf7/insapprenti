package fr.insacvl.competencesapprentis.controllers;

import com.itextpdf.text.DocumentException;
import fr.insacvl.competencesapprentis.beans.*;
import fr.insacvl.competencesapprentis.repository.CompetenceRepos;
import fr.insacvl.competencesapprentis.security.JwtUtils;
import fr.insacvl.competencesapprentis.security.RequiresRole;
import fr.insacvl.competencesapprentis.security.Roles;
import fr.insacvl.competencesapprentis.services.implementations.*;
import fr.insacvl.competencesapprentis.services.interfaces.ICompetence;
import fr.insacvl.competencesapprentis.services.interfaces.IMission;
import fr.insacvl.competencesapprentis.services.shared.AuthenticationService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.SECRET_KEY;

@Controller
public class MissionController {

}
