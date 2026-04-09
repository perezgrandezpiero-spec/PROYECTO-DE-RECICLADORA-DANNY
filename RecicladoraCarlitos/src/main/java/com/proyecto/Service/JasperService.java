package com.proyecto.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperService {

	public byte[] generarReporte(List<?> listaDatos, String nombreArchivoJrxml) throws IOException, JRException {

		ClassPathResource img = new ClassPathResource("static/img/logo.png");
		InputStream vistaImg = img.getInputStream();

		ClassPathResource reportResource = new ClassPathResource("reports/" + nombreArchivoJrxml + ".jrxml");
		InputStream reportStream = reportResource.getInputStream();
		JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDatos);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("autor", "Recicladora Carlitos");
		parametros.put("logo_input_stream", vistaImg);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
}