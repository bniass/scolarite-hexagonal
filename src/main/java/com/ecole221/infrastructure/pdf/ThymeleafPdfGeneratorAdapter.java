package com.ecole221.infrastructure.pdf;


import com.ecole221.application.port.out.pdf.PdfGeneratorPort;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;

@Component
public class ThymeleafPdfGeneratorAdapter implements PdfGeneratorPort {

    private final TemplateEngine templateEngine;

    public ThymeleafPdfGeneratorAdapter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public byte[] generate(String templateName, Object data) {
        try {
            Context context = new Context();
            context.setVariable("data", data);

            String html = templateEngine.process(templateName, context);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(html, null);
                builder.toStream(outputStream);
                builder.run();

                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erreur lors de la génération du PDF", e);
        }
    }
}
