package com.ecole221.application.service.paiement;

import com.ecole221.application.port.out.pdf.PdfGeneratorPort;
import com.ecole221.domain.entity.paiement.Paiement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PDFGeneratorService {
    private final PdfGeneratorPort pdfGeneratorPort;

    public PDFGeneratorService(PdfGeneratorPort pdfGeneratorPort) {
        this.pdfGeneratorPort = pdfGeneratorPort;
    }

    public byte[] generer(RecuPaiementData data) {
        return pdfGeneratorPort.generate("recu-paiement", data);
    }
}
