package fr.insacvl.competencesapprentis.services.interfaces;

import com.itextpdf.text.DocumentException;
import fr.insacvl.competencesapprentis.beans.Mission;

import java.io.IOException;

public interface IPdfGenerationService {

    byte[] generateMissionPDF(Mission mission) throws IOException, DocumentException;
}
