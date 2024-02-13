package fr.insacvl.competencesapprentis.services.implementations;

import fr.insacvl.competencesapprentis.beans.Apprenti;
import fr.insacvl.competencesapprentis.repository.ApprentiRepos;
import fr.insacvl.competencesapprentis.services.interfaces.IApprenti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService  {


    @Autowired
    private ApprentiRepos apprentiRepos;

    public void ApprentiService(ApprentiRepos apprentiRepos){
        this.apprentiRepos = apprentiRepos;
    }

    public Apprenti save(Apprenti apprenti){
        return apprentiRepos.save(apprenti);
    }

    public Apprenti findById(Long id) {
        Optional<Apprenti> optionalApprenti = apprentiRepos.findById(id);
        return optionalApprenti.orElse(null);
    }

    public List<Apprenti> findAll(){
        return apprentiRepos.findAll();
    }
    public Apprenti findByEmail(String email) {
        return apprentiRepos.findByEmail(email);
    }

    
    public void delete(Long id) {
        apprentiRepos.deleteById(id);
    }

    public List<Apprenti> findByTuteurInsa(Long id){
        return apprentiRepos.findByTuteurInsaId(id);
    }
}
    