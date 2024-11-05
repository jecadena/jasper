package com.actours.jasperreport.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {

    public JasperPrint generateReport(Boolean muestraColumna1, Boolean muestraColumna2, Boolean muestraColumna3, Boolean muestraColumna4, Boolean muestraColumna5, List<Map<String, Object>> data) throws JRException {
        InputStream reportStream = getClass().getResourceAsStream("/reports/DynamicColumnReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Parámetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("muestraColumna1", muestraColumna1);
        parameters.put("muestraColumna2", muestraColumna2);
        parameters.put("muestraColumna3", muestraColumna3);
        parameters.put("muestraColumna4", muestraColumna4);
        parameters.put("muestraColumna5", muestraColumna5);

        // Posición de columnas según parámetros
        int positionX = 0;
        if (muestraColumna1) { parameters.put("xColumna1", String.valueOf(positionX)); positionX += 100; }
        if (muestraColumna2) { parameters.put("xColumna2", String.valueOf(positionX)); positionX += 100; }
        if (muestraColumna3) { parameters.put("xColumna3", String.valueOf(positionX)); positionX += 100; }
        if (muestraColumna4) { parameters.put("xColumna4", String.valueOf(positionX)); positionX += 100; }
        if (muestraColumna5) { parameters.put("xColumna5", String.valueOf(positionX)); }

        // Fuente de datos
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
}
