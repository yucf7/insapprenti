package fr.insacvl.competencesapprentis.services.interfaces;


import fr.insacvl.competencesapprentis.beans.Apprenti;

import java.util.List;

public interface IApprenti {

    List<Apprenti> getAllApprentis();

    Apprenti getApprentiById(Long id);

    void saveApprenti(Apprenti apprenti);

    void deleteApprenti(Long id);
}
