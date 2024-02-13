package fr.insacvl.competencesapprentis.services.implementations;
import fr.insacvl.competencesapprentis.services.interfaces.IPdfGenerationService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import fr.insacvl.competencesapprentis.beans.CompetenceAcquise;
import fr.insacvl.competencesapprentis.beans.Mission;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGenerationService implements IPdfGenerationService {
    @Override
    public byte[] generateMissionPDF(Mission mission) throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD,BaseColor.BLUE);
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD,BaseColor.BLACK);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

        // Title
        Paragraph title = new Paragraph("Mission Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Mission Details
        document.add(new Paragraph("Mission Details:", headingFont));
        document.add(new Paragraph("Title: " + mission.getNom(), normalFont)); // Replace with actual mission title
        document.add(new Paragraph("Description: " + mission.getDescription(), normalFont)); // Replace with actual mission description
        document.add(new Paragraph("Objectif: " + mission.getObjectif(), normalFont)); // Replace with actual mission objectif
        document.add(new Paragraph("Date de début: " + mission.getDateDebut(), normalFont)); // Replace with actual mission start date
        document.add(new Paragraph("Date de fin: " + mission.getDateFin(), normalFont)); // Replace with actual mission end date
        document.add(new Paragraph("Résultat: " + mission.getResultat(), normalFont)); // Replace with actual mission result

        // Competences Acquises
        document.add(new Paragraph("Compétences Acquises:", headingFont));
        List<CompetenceAcquise> competencesAcquises = mission.getCompetencesAcquises();
        for (CompetenceAcquise competenceAcquise : competencesAcquises) {
            document.add(new Paragraph("Competence: " + competenceAcquise.getCompetence().getNom(), normalFont));
            document.add(new Paragraph("Niveau: " + competenceAcquise.getNiveau(), normalFont));
            document.add(new Paragraph("Justification: " + competenceAcquise.getJustification(), normalFont));
            // Add other competence details as needed
        }
        document.close();

        return baos.toByteArray();
    }


}
