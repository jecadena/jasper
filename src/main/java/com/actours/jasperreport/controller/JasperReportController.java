package com.actours.jasperreport.controller;

import com.actours.jasperreport.service.JasperReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JasperReportController {

    @Autowired
    private JasperReportService jasperReportService;

    @GetMapping("/api/reporte")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam(defaultValue = "false") Boolean muestraColumna1,
            @RequestParam(defaultValue = "false") Boolean muestraColumna2,
            @RequestParam(defaultValue = "false") Boolean muestraColumna3,
            @RequestParam(defaultValue = "false") Boolean muestraColumna4,
            @RequestParam(defaultValue = "false") Boolean muestraColumna5) {

        try {
            // Datos de ejemplo
            List<Map<String, Object>> data = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("columna1", "Dato 1");
                    put("columna2", "Dato 2");
                    put("columna3", "Dato 3");
                    put("columna4", "Dato 4");
                    put("columna5", "Dato 5");
                }},
                new HashMap<String, Object>() {{
                    put("columna1", "Dato A");
                    put("columna2", "Dato B");
                    put("columna3", "Dato C");
                    put("columna4", "Dato D");
                    put("columna5", "Dato E");
                }}
            );

            JasperPrint jasperPrint = jasperReportService.generateReport(muestraColumna1, muestraColumna2, muestraColumna3, muestraColumna4, muestraColumna5, data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            byte[] pdfData = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "reporte.pdf");

            return ResponseEntity.ok().headers(headers).body(pdfData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
