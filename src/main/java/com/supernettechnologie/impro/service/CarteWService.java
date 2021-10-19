package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.decodage.domain.CodeDatas;
import com.supernettechnologie.impro.decodage.service.DecodeQuitanceService;
import com.supernettechnologie.impro.decodage.util.Pdf417Utils;
import com.supernettechnologie.impro.decodage.util.Signer;
import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.apache.commons.lang3.SystemUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * Service Implementation for managing {@link CarteW}.
 */
@Service
@Transactional
public class CarteWService {

    private final Logger log = LoggerFactory.getLogger(CarteWService.class);
    @Autowired
    private ResourceLoader loader;

    private final CarteWRepository carteWRepository;

    private final CarteWMapper carteWMapper;

    private final DocIdentificationPMRepository docIdentificationPMRepository;
    private final DocIdentificationPMMapper docIdentificationPMMapper;

    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;

    private final DocIdentificationPPMapper docIdentificationPPMapper;
    private final DocIdentificationPPRepository docIdentificationPPRepository;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private ReportService reportService;
    @Autowired
    private OrganisationLocaliteRepository organisationLocaliteRepository;
    @Autowired
    private OrganisationLocaliteMapper organisationLocaliteMapper;
    @Autowired
    private DecodeQuitanceService decodeQuitanceService;


    public CarteWService(CarteWRepository carteWRepository, CarteWMapper carteWMapper, DocIdentificationPMRepository docIdentificationPMRepository, DocIdentificationPMMapper docIdentificationPMMapper, OrganisationRepository organisationRepository, OrganisationMapper organisationMapper, PersonnePhysiqueMapper personnePhysiqueMapper, DocIdentificationPPMapper docIdentificationPPMapper, DocIdentificationPPRepository docIdentificationPPRepository) {
        this.carteWRepository = carteWRepository;
        this.carteWMapper = carteWMapper;
        this.docIdentificationPMRepository = docIdentificationPMRepository;
        this.docIdentificationPMMapper = docIdentificationPMMapper;
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.docIdentificationPPMapper = docIdentificationPPMapper;
        this.docIdentificationPPRepository = docIdentificationPPRepository;
    }

    /**
     * Save a carteW.
     *
     * @param carteWDTO the entity to save.
     * @return the persisted entity.
     */
    public CarteWDTO save(CarteWDTO carteWDTO) throws Exception {
        log.debug("Request to save CarteW ******* : {}", carteWDTO.getLieuEtablissement());
        CarteW carteW = carteWMapper.toEntity(carteWDTO);
        DocIdentificationPM docIdentificationPM = docIdentificationPMMapper.toEntity(carteWDTO.getDocIdentificationPMDTO());
        Organisation organisation = organisationMapper.toEntity(carteWDTO.getOrganisationDTO());
        OrganisationLocalite organisationLocalite = organisationLocaliteRepository.findById(organisation.getOrganisationLocalite().getId()).get();
        String region = organisationLocalite.getCode();

        String header = Pdf417Utils.getHeaders("10", "SUPERNET");
        System.out.println("Code header : "+ header.length());

        organisation.setNumeroOrdre((int) organisationRepository.count()+1);
        log.debug("*********************************** : {}", organisationRepository.count());
        organisation.setNumeroPhone(docIdentificationPM.getTelephone());
        organisation = organisationRepository.save(organisation);
        docIdentificationPM.setOrganisation(organisation);
        docIdentificationPMRepository.save(docIdentificationPM);
        carteW.setOrganisation(organisation);
        String w = "00"+String.valueOf(carteWRepository.count()+1)+" W"+" "+region;
        if (carteWRepository.count() < 10) {
            w = "00"+String.valueOf(carteWRepository.count()+1)+" W"+" "+region;
        }else {
            if (carteWRepository.count() > 99) {
                w = String.valueOf(carteWRepository.count()+1)+" W"+" "+region;
            }else {
                w = "0"+String.valueOf(carteWRepository.count()+1)+" W"+" "+region;
            }
        }
        LocalDate dt = LocalDate.now();
        String jour = String.valueOf(dt.getDayOfMonth()-1);
        String mois = String.valueOf(dt.getMonthValue());
        String year = String.valueOf(dt.getYear()+1);
        if (Integer.parseInt(jour) < 10) {
            jour = "0"+jour;
        }
        if (Integer.parseInt(mois) < 10) {
            mois = "0"+mois;
        }
        carteW.setNumeroCarteW(w);
        carteW.setDateEtablissementCarteW(LocalDate.now());
        carteW.setDateExpirationCarteW(LocalDate.parse(year+"-"+mois+"-"+jour));
        log.debug("**************************************** : {}", carteWRepository.count());

        String body = Pdf417Utils.getBody(organisation.getNom(), w);

        String data = header+body;

        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
        signToString = URLEncoder.encode(signToString, "UTF-8");
        String hex = new String (Hex.encode(signToString.getBytes()));
        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;
//        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//        System.out.println("Code header : "+codeDatas.getHeader().get(0).getValue());

        carteW.setCodeQr(code);
        carteW = carteWRepository.save(carteW);
        return carteWMapper.toDto(carteW);
    }

    /**
     * Get all the carteWS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarteWDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarteWS");
        Page<CarteWDTO> carteWDTOPage = carteWRepository.findAll(pageable)
            .map(carteWMapper::toDto);
        for (CarteWDTO carteWDTO : carteWDTOPage.getContent()){
            OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(carteWDTO.getOrganisationId()).get());
            carteWDTO.setOrganisationDTO(organisationDTO);
        }
        return carteWDTOPage;
    }

    /**
     * Get one carteW by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    /*@Transactional(readOnly = true)
    public Optional<CarteWDTO> findOne(Long id) {
        log.debug("Request to get CarteW : {}", id);
        CarteW carteW = carteWRepository.findById(id).get();
        OrganisationDTO organisationDTO = organisationMapper.toDto(carteW.getOrganisation());
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPMRepository.findByOrganisationId(organisationDTO.getId()));
       // PersonnePhysique personnePhysique = carteW.getOrganisation().getGerant();
        //PersonnePhysiqueDTO personnePhysiqueDTO = personnePhysiqueMapper.toDto(personnePhysique);
       // DocIdentificationPPDTO docIdentificationPPDTO = docIdentificationPPMapper.toDto(personnePhysique.getDocIdentification());
        CarteWDTO carteWDTO = carteWMapper.toDto(carteW);
        carteWDTO.setOrganisationDTO(organisationDTO);
        carteWDTO.setDocIdentificationPMDTO(docIdentificationPMDTO);
        //carteWDTO.setPersonnePhysiqueDTO(personnePhysiqueDTO);
       // carteWDTO.setDocIdentificationPPDTO(docIdentificationPPDTO);
        return Optional.of(carteWDTO);
    }*/

    @Transactional(readOnly = true)
    public Optional<CarteWDTO> findOne(Long id) {
        log.debug("Request to get Collaboration : {}", id);
        Optional<CarteWDTO> carteWDTO = carteWRepository.findById(id)
            .map(carteWMapper::toDto);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(carteWDTO.get().getOrganisationId()).get());
        carteWDTO.get().setOrganisationDTO(organisationDTO);
        return carteWDTO;
    }

    /**
     * Delete the carteW by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarteW : {}", id);
        carteWRepository.deleteById(id);
    }

    public void findOnePrint(HttpServletResponse response, Long id) throws IOException, JRException {
        CarteW carteW = carteWRepository.findById(id).get();
        OrganisationDTO organisationDTO = organisationMapper.toDto(carteW.getOrganisation());
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper.toDto(docIdentificationPMRepository.findByOrganisationId(organisationDTO.getId()));
        CarteWDTO carteWDTO = carteWMapper.toDto(carteW);
        carteWDTO.setOrganisationDTO(organisationDTO);
        carteWDTO.setDocIdentificationPMDTO(docIdentificationPMDTO);
        OrganisationLocalite organisationLocalite = organisationLocaliteRepository.findById(organisationDTO.getOrganisationLocaliteId()).get();
        String region = organisationLocalite.getNom();

        ClassPathResource classPathResourceVerso = new ClassPathResource("carteVerso_blank.jrxml");
        ClassPathResource classPathResource = new ClassPathResource("carteVerso.jrxml");
        ClassPathResource cprLogo = new ClassPathResource("bfa.png");
        ClassPathResource cprTrans = new ClassPathResource("bftrans.png");
        ClassPathResource cprSignatur = new ClassPathResource("signer.png");

        String lgo = cprLogo.getPath();
        String tran = cprTrans.getPath();
        String signe = cprSignatur.getPath();

        final Map<String, Object> parameters = new HashMap<>();
        final Map<String, Object> verso = new HashMap<>();
        List<JasperPrint> jpList = new ArrayList<>();

        parameters.put("titulaire", organisationDTO.getNom());
        parameters.put("telephone", organisationDTO.getNumeroPhone());
        parameters.put("siege", region);
        parameters.put("ouagale", carteW.getDateEtablissementCarteW().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        parameters.put("expire", carteW.getDateExpirationCarteW().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        parameters.put("carte_w", "CARTE W : N° "+carteW.getNumeroCarteW());
        parameters.put("transparent", tran);
        verso.put("transparent", tran);
        parameters.put("code", carteW.getOrganisation().getNom()+carteW.getNumeroCarteW() );
        parameters.put("logobf", lgo);
        parameters.put("signe", signe);

        JasperReport jasperReport = JasperCompileManager.compileReport(classPathResource.getInputStream());
        JasperReport jasperReportVerso = JasperCompileManager.compileReport(classPathResourceVerso.getInputStream());
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
        jasperReportVerso.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperPrint jasperPrintVer = JasperFillManager.fillReport(jasperReportVerso, verso, new JREmptyDataSource());

        jpList.add(jasperPrint);
        jpList.add(jasperPrintVer);

        if (SystemUtils.IS_OS_LINUX) {
            new File("/root/CarteW/").mkdir();
            String chemin = "/root/CarteW/";
            reportService.generateCertificat(jpList, chemin+organisationDTO.getNom()+".pdf");

        } else {
            new File("C:\\CarteW").mkdir();
            String chemin = "C:\\CarteW";
            reportService.generateCertificat(jpList, chemin+"\\"+organisationDTO.getNom()+".pdf");
        }

        //reportService.generateCarteW(parameters);
    }
}
