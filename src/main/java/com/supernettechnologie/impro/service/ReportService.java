package com.supernettechnologie.impro.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private ResourceLoader loader;

    public void generateCertificat(List<JasperPrint> jpList, String filename) throws IOException, JRException {

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jpList)); //Set as export input
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filename)); //Set output stream
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//set your configuration
        exporter.setConfiguration(configuration);
        exporter.exportReport();

//        response.setContentType("application/x-pdf");
//        response.setHeader("Content-disposition", "inline; filename=CarteW.pdf");

    }
    public void generateBandes(List<JasperPrint> jpList, String filename) throws IOException, JRException {


        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jpList)); //Set as export input
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filename)); //Set output stream
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
//set your configuration
        exporter.setConfiguration(configuration);
        exporter.exportReport();

//        response.setContentType("application/x-pdf");
//        response.setHeader("Content-disposition", "inline; filename=CarteW.pdf");

    }


    public void generateCarteW(Map<String, Object> values) throws IOException, JRException {

        String path = loader.getResource("classpath:carteW.jrxml").getURI().getPath();
        JasperReport jasperReport
            = JasperCompileManager.compileReport(path);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, values, new JREmptyDataSource());
//        response.setContentType("application/x-pdf");
//        response.setHeader("Content-disposition", "inline; filename=CarteW.pdf");

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add (OrientationRequested.LANDSCAPE);
        printRequestAttributeSet.add(MediaSizeName.ISO_A5);
        printRequestAttributeSet.add(new Copies(1));

        PrinterName printerName = new PrinterName("HP ColorLaserJet MFP M278-M281", null); //gets printer

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
    public void generatePlaque(Map<String, Object> values, String plaq) throws IOException, JRException {

        String path = loader.getResource("classpath:plaques.jrxml").getURI().getPath();
        String path2 = loader.getResource("classpath:plaqueim.jrxml").getURI().getPath();
        String pat = loader.getResource("classpath:vehiculesPlaques.jrxml").getURI().getPath();
        JasperReport jasperReport
            = JasperCompileManager.compileReport(plaq);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, values, new JREmptyDataSource());
//        response.setContentType("application/x-pdf");
//        response.setHeader("Content-disposition", "inline; filename=plaque.pdf");

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add (OrientationRequested.LANDSCAPE);
        printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
        printRequestAttributeSet.add(new Copies(1));

        PrinterName printerName = new PrinterName("HP ColorLaserJet MFP M278-M281", null); //gets printer

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);


        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();

//        final OutputStream outStream = response.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

    }
    public void generatePlaqueMoto(Map<String, Object> values, String plaq) throws IOException, JRException {

        String path = loader.getResource("classpath:plaques.jrxml").getURI().getPath();
        String path2 = loader.getResource("classpath:plaqueim.jrxml").getURI().getPath();
        String pat = loader.getResource("classpath:vehiculesPlaques.jrxml").getURI().getPath();
        JasperReport jasperReport
            = JasperCompileManager.compileReport(plaq);
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, values, new JREmptyDataSource());
//        response.setContentType("application/x-pdf");
//        response.setHeader("Content-disposition", "inline; filename=plaque.pdf");

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add (OrientationRequested.PORTRAIT);
        printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
        printRequestAttributeSet.add(new Copies(1));

        PrinterName printerName = new PrinterName("HP ColorLaserJet MFP M278-M281", null); //gets printer

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);


        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();

//        final OutputStream outStream = response.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

    }

    public HttpServletResponse downloadPdfFile(HttpServletResponse response, String fileName, List<JasperPrint> jpList) throws IOException, JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jpList)); //Set as export input
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); //Set output stream
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename="+fileName+".pdf;");
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return response;
    }

}
