package fr.insacvl.competencesapprentis.services.implementations;

import fr.insacvl.competencesapprentis.beans.Admin;
import fr.insacvl.competencesapprentis.repository.AdminRepos;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    AdminRepos adminRepos;

    public AdminService (AdminRepos adminRepos){
        this.adminRepos = adminRepos;
    }

    public Admin save(Admin admin){
        return adminRepos.save(admin);
    }
}
