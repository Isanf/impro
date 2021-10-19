package com.supernettechnologie.impro.service;

import com.github.royken.converter.FrenchNumberToWords;
import com.supernettechnologie.impro.decodage.service.DecodeQuitanceService;
import com.supernettechnologie.impro.decodage.util.Pdf417Utils;
import com.supernettechnologie.impro.decodage.util.Signer;
import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.dto.*;
import com.supernettechnologie.impro.service.mapper.*;
import com.supernettechnologie.impro.service.util.RandomUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * Service Implementation for managing {@link CommandeCarnetSouche}.
 */
@Service
@Transactional
public class CommandeCarnetSoucheService {

    private final Logger log = LoggerFactory.getLogger(CommandeCarnetSoucheService.class);

    private final CommandeCarnetSoucheRepository commandeCarnetSoucheRepository;

    private final CommandeCarnetSoucheMapper commandeCarnetSoucheMapper;

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    private final PersonnePhysiqueRepository personnePhysiqueRepository;

    private final PersonnePhysiqueMapper personnePhysiqueMapper;
    @Autowired
    private CarnetASoucheRepository carnetASoucheRepository;

    private final InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;

    @Autowired
    private CertificatImmatriculationRepository certificatImmatriculationRepository;
    @Autowired
    private TypeCarnetRepository typeCarnetRepository;
    @Autowired
    private LivraisonCarnetSoucheRepository livraisonCarnetSoucheRepository;
    @Autowired
    private LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper;
    @Autowired
    private PlaqueImmatriculationRepository plaqueImmatriculationRepository;
    @Autowired
    private CarteWRepository carteWRepository;

    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;
    @Autowired
    private ResourceLoader loader;
    @Autowired
    private ReportService reportService;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private DecodeQuitanceService decodeQuitanceService;
    @Autowired
    private DocIdentificationPMRepository docIdentificationPMRepository;
    @Autowired
    private InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;
    @Autowired
    private TypeCarnetMapper typeCarnetMapper;
    @Autowired
    private LogActivityService logActivityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocIdentificationPPMapper docIdentificationPPMapper;
    @Autowired
    private DocIdentificationPMMapper docIdentificationPMMapper;


    public CommandeCarnetSoucheService(CommandeCarnetSoucheRepository commandeCarnetSoucheRepository, CommandeCarnetSoucheMapper commandeCarnetSoucheMapper, ProfilRepository profilRepository, ProfilMapper profilMapper, PersonnePhysiqueRepository personnePhysiqueRepository, PersonnePhysiqueMapper personnePhysiqueMapper, InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository) {
        this.commandeCarnetSoucheRepository = commandeCarnetSoucheRepository;
        this.commandeCarnetSoucheMapper = commandeCarnetSoucheMapper;
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.personnePhysiqueRepository = personnePhysiqueRepository;
        this.personnePhysiqueMapper = personnePhysiqueMapper;
        this.infoCommandeCarnetASoucheRepository = infoCommandeCarnetASoucheRepository;
    }

    /**
     * Save a commandeCarnetSouche.
     *
     * @param commandeCarnetSoucheDTO the entity to save.
     * @return the persisted entity.
     */
    public CommandeCarnetSoucheDTO save(CommandeCarnetSoucheDTO commandeCarnetSoucheDTO) throws IOException, JRException {
        log.debug("Request to save CommandeCarnetSouche : {}", commandeCarnetSoucheDTO);
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        commandeCarnetSoucheDTO.setConcessionnaireId(personnePhysique.get().getOrganisation().getId());
        commandeCarnetSoucheDTO.setDateCommandeCS(ZonedDateTime.now());
        User user = userRepository.findById(personnePhysique.get().getUser().getId()).get();
        LogActivityDTO logActivityDTO = new LogActivityDTO();
        logActivityDTO.setAction("création d'une commande de canet à souche");
        logActivityDTO.setPrincipal(user.getLogin().toUpperCase());
        logActivityService.save(logActivityDTO);



        List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTOS = commandeCarnetSoucheDTO.getInfoCommandeCarnetASouches();
        commandeCarnetSoucheDTO.setNumeroCommandeCS(RandomUtil.generateRandomSerialNumericStringc());
        CommandeCarnetSouche commandeCarnetSouche = commandeCarnetSoucheMapper.toEntity(commandeCarnetSoucheDTO);

        commandeCarnetSouche = commandeCarnetSoucheRepository.save(commandeCarnetSouche);
        for (InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO : infoCommandeCarnetASoucheDTOS){

            TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnetRepository.findById(infoCommandeCarnetASoucheDTO.getTypeCarnetId()).get());
            infoCommandeCarnetASoucheDTO.setCommandeCarnetSoucheId(commandeCarnetSouche.getId());
            infoCommandeCarnetASoucheDTO.setNumeroCommande(RandomUtil.generateRandomSerialNumericStringc());
            infoCommandeCarnetASoucheDTO.setDateCommande(ZonedDateTime.now());

            infoCommandeCarnetASoucheRepository.save(infoCommandeCarnetASoucheMapper.toEntity(infoCommandeCarnetASoucheDTO));
        }


        return commandeCarnetSoucheMapper.toDto(commandeCarnetSouche);
    }

    /**
     * Get all the commandeCarnetSouches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommandeCarnetSoucheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeCarnetSouches");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Long id = personnePhysique.get().getOrganisation().getId();
        return commandeCarnetSoucheRepository.findAllByConcessionnaireId(pageable, id)
            .map(commandeCarnetSoucheMapper::toDto);
    }


    @Transactional(readOnly = true)
    public Page<CommandeCarnetSoucheDTO> findAlls(Pageable pageable) {
        log.debug("Request to get all CommandeCarnetSouches");
        Page<CommandeCarnetSoucheDTO> commandeCarnetSoucheDTOPage = commandeCarnetSoucheRepository.findAll(pageable)
            .map(commandeCarnetSoucheMapper::toDto);
        List<CommandeCarnetSoucheDTO> commandeCarnetSoucheDTOList = new ArrayList<>();
        for (CommandeCarnetSoucheDTO commandeCarnetSoucheDTO : commandeCarnetSoucheDTOPage.getContent()){
            OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(commandeCarnetSoucheDTO.getConcessionnaireId()).get());
            commandeCarnetSoucheDTO.setOrganisationDTO(organisationDTO);
            commandeCarnetSoucheDTOList.add(commandeCarnetSoucheDTO);
        }
        return commandeCarnetSoucheDTOPage;
    }

    /**
     * Get one commandeCarnetSouche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommandeCarnetSoucheDTO> findOne(Long id) {
        log.debug("Request to get CommandeCarnetSouche : {}", id);
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findByCommandeCarnetSoucheId(id);
        List<TypeCarnetDTO> typeCarnetDTOS = new ArrayList<>();
        List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTOS = new ArrayList<>();
        for (InfoCommandeCarnetASouche infoCommandeCarnetASouche : infoCommandeCarnetASoucheList){
            TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnetRepository.findById(infoCommandeCarnetASouche.getTypeCarnet().getId()).get());
            InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO = infoCommandeCarnetASoucheMapper.toDto(infoCommandeCarnetASouche);
            infoCommandeCarnetASoucheDTO.setLibelle(typeCarnetDTO.getLibelle());
            infoCommandeCarnetASoucheDTOS.add(infoCommandeCarnetASoucheDTO);
        }
        Optional<CommandeCarnetSoucheDTO> commandeCarnetSoucheDTO = commandeCarnetSoucheRepository.findById(id).map(commandeCarnetSoucheMapper::toDto);
        Optional<CommandeCarnetSouche> commandeCarnetSouche = commandeCarnetSoucheRepository.findById(id);
        commandeCarnetSoucheDTO.get().setInfoCommandeCarnetASouches(infoCommandeCarnetASoucheDTOS);
        commandeCarnetSoucheDTO.get().setTypeCarnetDTOS(typeCarnetDTOS);
        commandeCarnetSoucheDTO.get().setOrganisationDTO(organisationMapper.toDto(commandeCarnetSouche.get().getConcessionnaire()));

        return commandeCarnetSoucheDTO;
    }

    public HttpServletResponse findOneValid(Long id, HttpServletResponse response) throws JRException, IOException {
        log.debug("Request to get CommandeCarnetSouche : {}", id);
        Optional<CommandeCarnetSouche> commandeCarnetSouche = commandeCarnetSoucheRepository.findById(id);
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASoucheList = infoCommandeCarnetASoucheRepository.findByCommandeCarnetSoucheId(id);

        Long qv1 = null, qv2 = null, qv3 = null, qm1 = null, qm2 = null, qm3 = null;
        Long total1, total2, tpv = null, tpm = null;
        List<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTOS = new ArrayList<>();
        for (InfoCommandeCarnetASouche infoCommandeCarnetASouche : infoCommandeCarnetASoucheList){
            if (infoCommandeCarnetASouche.getTypeCarnet().getTypeVehicule().isEstCycleMoteur()) {
                if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 25) {
                    qm1 = infoCommandeCarnetASouche.getQuantiteCommande();
                }else if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 50) {
                    qm2 = infoCommandeCarnetASouche.getQuantiteCommande();
                }else if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 100) {
                    qm3 = infoCommandeCarnetASouche.getQuantiteCommande();
                }
            }else {
                if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 25) {
                    qv1 = infoCommandeCarnetASouche.getQuantiteCommande();
                }else if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 50) {
                    qv2 = infoCommandeCarnetASouche.getQuantiteCommande();
                }else if (infoCommandeCarnetASouche.getTypeCarnet().getQuantiteCertificat() == 100) {
                    qv3 = infoCommandeCarnetASouche.getQuantiteCommande();
                }
            }

        }

//        String path = loader.getResource("classpath:commande.jrxml").getURI().getPath();
//        String sth = loader.getResource("classpath:stht.png").getURI().getPath();

        ClassPathResource cm = new ClassPathResource("commande.jrxml");
        ClassPathResource cprSth = new ClassPathResource("stht.png");
        String lgoSth = cprSth.getPath();

        total1 = (qv1 == null ? 0 : qv1) + (qv2 == null ? 0 : qv2) + (qv3 == null ? 0 : qv3);
        total2 = (qm1 == null ? 0 : qm1) + (qm2 == null ? 0 : qm2) + (qm2 == null ? 0 : qm2);
        tpv = (qv1 == null ? 0 : qv1)*25*12500 + (qv2 == null ? 0 : qv2)*50*12500 + (qv3 == null ? 0 : qv3)*100*12500;
        String prix1 = String.format("%,d", tpv );

        tpm = (qm1 == null ? 0 : qm1)*25*12500 + (qm2 == null ? 0 : qm2)*50*12500 + (qm3 == null ? 0 : qm3)*100*12500;
        String prix2= String.format("%,d", tpm );
        String total = String.format("%,d", (tpv + tpm));
        final Map<String, Object> parameters = new HashMap<>();
        List<JasperPrint> jpList = new ArrayList<>();

        parameters.put("total1", String.valueOf(total1)  == null ? "0" : String.valueOf(total1));
        parameters.put("total2", String.valueOf(total2) == null ? "0" : String.valueOf(total2));
        parameters.put("qvun", String.valueOf(qv1)  == null ? "0" : String.valueOf(qv1));
        parameters.put("qvdeux", String.valueOf(qv2)  == null ? "0" : String.valueOf(qv2));
        parameters.put("qvtrois", String.valueOf(qv3)  == null ? "0" : String.valueOf(qv3));
        parameters.put("qmun", String.valueOf(qm1)  == null ? "0" : String.valueOf(qm1));
        parameters.put("qmdeux", String.valueOf(qm2)  == null ? "0" : String.valueOf(qm2));
        parameters.put("qmtrois", String.valueOf(qm3)  == null ? "0" : String.valueOf(qm3));
        parameters.put("pvun", String.format("%,d",(qv1 == null ? 0 : qv1)*25*12500));
        parameters.put("pvdeux", String.format("%,d",(qv2 == null ? 0 : qv2)*50*12500));
        parameters.put("pvtrois", String.format("%,d",(qv3 == null ? 0 : qv3)*100*12500));
        parameters.put("pmun", String.format("%,d",(qm1 == null ? 0 : qm1)*25*12500));
        parameters.put("pmdeux", String.format("%,d",(qm2 == null ? 0 : qm2)*50*12500));
        parameters.put("pmtrois", String.format("%,d",(qm3 == null ? 0 : qm3)*100*12500));
        parameters.put("ptv", (prix1  == null ? "0" : prix1)+" FCFA");
        parameters.put("ptm", (prix2  == null ? "0" : prix2)+" FCFA");
        parameters.put("name", commandeCarnetSouche.get().getConcessionnaire().getNom());
        parameters.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        parameters.put("sth", lgoSth);
        parameters.put("totalgene", (total  == null ? "0" : total)+" FCFA");
        parameters.put("numero", "00" + String.valueOf(commandeCarnetSoucheRepository.countAllByConcessionnaireId(commandeCarnetSouche.get().getConcessionnaire().getId())) +"/"+ String.valueOf(LocalDate.now().getYear()));
        //parameters.put("itemsm", comItem1List);

        JasperReport jasperReport
            = JasperCompileManager.compileReport(cm.getInputStream());
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        jpList.add(jasperPrint);

        response = reportService.downloadPdfFile(response, "CommandeN°_"+commandeCarnetSouche.get().getNumeroCommandeCS(), jpList);

        /*if (SystemUtils.IS_OS_LINUX) {
            new File("/root/CommandeCarnetSouche/").mkdir();
            String chemin = "/root/CommandeCarnetSouche/";
            reportService.generateCertificat(jpList, chemin+"\\CommandeN°_"+commandeCarnetSouche.get().getNumeroCommandeCS()+".pdf");
            commandeCarnetSouche.get().setEstValide(true);

            return commandeCarnetSouche.map(commandeCarnetSoucheMapper::toDto);
        } else {
            new File("C:\\CommandeCarnetSouche").mkdir();
            String chemin = "C:\\CommandeCarnetSouche";
            reportService.generateCertificat(jpList, chemin+"\\CommandeN°_"+commandeCarnetSouche.get().getNumeroCommandeCS()+".pdf");
            commandeCarnetSouche.get().setEstValide(true);
            return commandeCarnetSouche.map(commandeCarnetSoucheMapper::toDto);
        }*/
        commandeCarnetSouche.get().setEstValide(true);
        return response;
    }


    /**
     * Delete the commandeCarnetSouche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeCarnetSouche : {}", id);
        commandeCarnetSoucheRepository.deleteById(id);
    }

    public void findOneValidAdmin(Long id) throws Exception {
        log.debug("Request to get CommandeCarnetSouche : {}", id);
        Optional<CommandeCarnetSouche> commandeCarnetSouche = commandeCarnetSoucheRepository.findById(id);
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASouches = infoCommandeCarnetASoucheRepository.findByCommandeCarnetSoucheId(id);

        //System.out.println(infoCommandeCarnetASouches.get(0).isEstTransiter());
        Optional<CarteW> carteW = carteWRepository.findByOrganisationId(commandeCarnetSouche.get().getConcessionnaire().getId());
        if (carteWRepository.existsByOrganisationId(commandeCarnetSouche.get().getConcessionnaire().getId())) {
            System.out.println("Id Commande : " +commandeCarnetSouche.get().getConcessionnaire().getId());
            DocIdentificationPM docIdentificationPM = docIdentificationPMRepository.findByOrganisationId(commandeCarnetSouche.get().getConcessionnaire().getId());
            Organisation organisation = organisationRepository.findById(commandeCarnetSouche.get().getConcessionnaire().getId()).get();
            PersonnePhysique personnePhysique = organisation.getGerant();

            String header = Pdf417Utils.getHeaders("10", "SUPERNET");
            //System.out.println("Taille header "+header.length());

            ClassPathResource bm = new ClassPathResource("bande_moto.jrxml");
            ClassPathResource bv = new ClassPathResource("bande_voiture.jrxml");
            ClassPathResource cprBf = new ClassPathResource("cdbf.jpg");
            String lgo = cprBf.getPath();

            for (InfoCommandeCarnetASouche infoCommandeCarnetASouche : infoCommandeCarnetASouches) {
                Long quantiteCommande = infoCommandeCarnetASouche.getQuantiteCommande();
                Optional<TypeCarnet> typeCarnets = typeCarnetRepository.findByInfoCommandeCarnetASouches(infoCommandeCarnetASouche);
                Optional<TypeVehicule> typeVehicule = typeVehiculeRepository.findByTypeCarnets(typeCarnets.get());
                Long nbrPlaque = typeVehicule.get().getNombrePlaque();
                Long quantiteType = typeCarnets.get().getQuantiteCertificat();

                if (nbrPlaque == 2) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 1; j++) {
                        String numero = carteW.get().getNumeroCarteW();
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        char r1 = numero.charAt(6);
                        char r2 = numero.charAt(7);
                        String region = String.valueOf(r1) + String.valueOf(r2);
                        int incremental = 1;

                        String immatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();

                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }

                        /*if (infoCommandeCarnetASouche.isEstTransiter()) {
                            immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                        }else {
                            immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n;
                        }*/

                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + "WW" + incremental  + " " + n;
                            }

                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + "WW" + incremental  + " " + n;
                            }
                        }

                        String body = Pdf417Utils.getBody(commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.getNom()+" "+personnePhysique.getPrenom());

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//                        System.out.println("Code header : "+codeDatas.getHeader().get(0).getValue());

                        for (int k = 0; k < 2; k++) {
                            PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                            plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                            plaqueImmatriculation.setCodeQR(code);
                            plaqueImmatriculation.setNumeroSerie(numéroSerie);
                            plaqueImmatriculationRepository.save(plaqueImmatriculation);
                            commandeCarnetSouche.get().setEstTraitee(true);
                            commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());
                            Map<String, Object> parameter = new HashMap<>();
//                            parameter.put("logo", logo);
//                            parameter.put("qrcode", code);
//                            parameter.put("immatriculation", immatriculation);
                            parameter.put("code", indice);
                            parameter.put("groupe", String.valueOf(incremental));
                            parameter.put("numero", n);
                            parameter.put("qr", code);
                            parameter.put("bf", lgo);

                            JasperReport jasperReport
                                = JasperCompileManager.compileReport(bv.getInputStream());
                            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                            JasperPrint jasperPrint
                                = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                            jpList.add(jasperPrint);
                            //reportService.generatePlaque(parameter, pat);
                        }
                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_"+organisation.getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_" + organisation.getNom() +RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }

                }
                //Pour cycle à moteur Concessionnaire
                else if (nbrPlaque == 1 && typeVehicule.get().isEstCycleMoteur()) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 2; j++) {
                        String numero = carteW.get().getNumeroCarteW();
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        char r1 = numero.charAt(6);
                        char r2 = numero.charAt(7);
                        String region = String.valueOf(r1) + String.valueOf(r2);
                        int incremental = 1;

                        String body = Pdf417Utils.getBody(commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.getNom()+" "+personnePhysique.getPrenom());

                        String immatriculation = null;
                        String numeroImmatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();

                        /*if (infoCommandeCarnetASouche.isEstTransiter()) {
                            immatriculation = indice + " " + incremental + "WW" +  " " + region + " " + n;
                        }else {
                            immatriculation = indice + " " + incremental + "WW" +  " " + region + " " + n + " 99";
                            n = region + " " +n+" 99";
                        }*/

                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }


                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(5))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + incremental + "WW" +  " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + incremental + "WW" + " " + n;
                            }

                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(5))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + incremental + "WW" +  " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + incremental + "WW" + " " + n;
                            }
                        }

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes()))+hex;

//                        System.out.println("Code barre : "+code);
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//                        System.out.println("Code header : "+codeDatas.getHeader().get(0).getValue());

                        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                        plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                        plaqueImmatriculation.setCodeQR(code);
                        plaqueImmatriculation.setNumeroSerie(numéroSerie);
                        plaqueImmatriculationRepository.save(plaqueImmatriculation);
                        commandeCarnetSouche.get().setEstTraitee(true);
                        commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());
                        Map<String, Object> parameter = new HashMap<>();
//                        parameter.put("logo", logo);
//                        parameter.put("qrcode", code);
//                        parameter.put("immatriculation", immatriculation);
//                        reportService.generatePlaqueMoto(parameter, path);
                        parameter.put("code", indice);
                        parameter.put("groupe", String.valueOf(incremental));
                        parameter.put("numero", n);
                        parameter.put("qr", code);
                        parameter.put("bf", lgo);

                        JasperReport jasperReport
                            = JasperCompileManager.compileReport(bm.getInputStream());
                        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                        JasperPrint jasperPrint
                            = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                        jpList.add(jasperPrint);

                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_" +organisation.getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_" +organisation.getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }
                }
                //Pour une bande et no cycle à moteur
                else if (nbrPlaque == 1 && !typeVehicule.get().isEstCycleMoteur()) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 2; j++) {
                        String numero = carteW.get().getNumeroCarteW();
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        char r1 = numero.charAt(6);
                        char r2 = numero.charAt(7);
                        String region = String.valueOf(r1) + String.valueOf(r2);
                        int incremental = 1;

                        String body = Pdf417Utils.getBody(
                            commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.getNom()+" "+personnePhysique.getPrenom());

                        String immatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();


                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }
                        /*if (infoCommandeCarnetASouche.isEstTransiter() == true) {
                            immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                            n = region + " " +n+" 99";
                        }else {
                            immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n;
                        }*/

                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + "WW" + incremental + " " + n;
                            }

                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + region + " " + n + " 99";
                                n = region + " " +n+" 99";
                            }else {
                                n = region + " " +n;
                                immatriculation = indice + " " + "WW" + incremental + " " + n;
                            }
                        }

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;

//                        System.out.println("Code : "+code);
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//
//                        System.out.println("Code Header : "+codeDatas.getHeader().get(0).getValue());

                        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                        plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                        plaqueImmatriculation.setCodeQR(code);
                        plaqueImmatriculation.setNumeroSerie(numéroSerie);
                        plaqueImmatriculationRepository.save(plaqueImmatriculation);
                        commandeCarnetSouche.get().setEstTraitee(true);
                        commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());

                        Map<String, Object> parameter = new HashMap<>();
//                        parameter.put("logo", logo);
//                        parameter.put("qrcode", code);
//                        parameter.put("immatriculation", immatriculation);
//                        reportService.generatePlaque(parameter, pat);

                        parameter.put("code", indice);
                        parameter.put("groupe", String.valueOf(incremental));

                        parameter.put("numero", n);
                        parameter.put("qr", code);
                        parameter.put("bf", lgo);

                        JasperReport jasperReport
                            = JasperCompileManager.compileReport(bv.getInputStream());
                        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                        JasperPrint jasperPrint
                            = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                        jpList.add(jasperPrint);

                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_"+organisation.getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_"+organisation.getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }

                }

            }
        }
        else {
            DocIdentificationPM docIdentificationPM = docIdentificationPMRepository.findByOrganisationId(commandeCarnetSouche.get().getConcessionnaire().getId());
            Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByOrganisationId(commandeCarnetSouche.get().getConcessionnaire().getId());
            System.out.println("Id Commande : " +id);

            String header = Pdf417Utils.getHeaders("10", "SUPERNET");

            /*System.out.println("Taille header "+header.length());
            String logo_path = loader.getResource("classpath:bfa.png").getURI().getPath();
            String logo = loader.getResource("classpath:cdbf.jpg").getURI().getPath();
            String path = loader.getResource("classpath:plaqueim.jrxml").getURI().getPath();
            String pat = loader.getResource("classpath:vehiculesPlaques.jrxml").getURI().getPath();
            String bande_moto = loader.getResource("classpath:bande_moto.jrxml").getURI().getPath();
            String bande_voiture = loader.getResource("classpath:bande_voiture.jrxml").getURI().getPath();*/

            ClassPathResource bm = new ClassPathResource("bande_moto.jrxml");
            ClassPathResource bv = new ClassPathResource("bande_voiture.jrxml");
            ClassPathResource cprBf = new ClassPathResource("cdbf.jpg");
            String lgo = cprBf.getPath();

            for (InfoCommandeCarnetASouche infoCommandeCarnetASouche : infoCommandeCarnetASouches) {
                Long quantiteCommande = infoCommandeCarnetASouche.getQuantiteCommande();
                Optional<TypeCarnet> typeCarnets = typeCarnetRepository.findByInfoCommandeCarnetASouches(infoCommandeCarnetASouche);
                Optional<TypeVehicule> typeVehicule = typeVehiculeRepository.findByTypeCarnets(typeCarnets.get());
                Long nbrPlaque = typeVehicule.get().getNombrePlaque();
                Long quantiteType = typeCarnets.get().getQuantiteCertificat();

                if (nbrPlaque == 2) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 1; j++) {
                        String numero = "999 W";
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        int incremental = 1;

                        String immatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();

                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }
                        /*if (infoCommandeCarnetASouche.isEstTransiter() == true) {
                            immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                        }else {
                            immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                            n = region + " " +n+" 99";
                        }*/

                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                                n = n+" 99";
                            }else {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                            }
                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                                n = n+" 99";
                            }else {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                            }
                        }
                        String body = Pdf417Utils.getBody(commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.get().getNom()+" "+personnePhysique.get().getPrenom());

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//                        System.out.println("Code header : "+codeDatas.getHeader().get(1).getValue());

                        for (int k = 0; k < 2; k++) {
                            PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                            plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                            plaqueImmatriculation.setCodeQR(code);
                            plaqueImmatriculation.setNumeroSerie(numéroSerie);
                            plaqueImmatriculationRepository.save(plaqueImmatriculation);
                            commandeCarnetSouche.get().setEstTraitee(true);
                            commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());
                            Map<String, Object> parameter = new HashMap<>();
//                            parameter.put("logo", logo);
//                            parameter.put("qrcode", code);
//                            parameter.put("immatriculation", immatriculation);

                            parameter.put("code", indice);
                            parameter.put("groupe", String.valueOf(incremental));

                            parameter.put("numero", n);
                            parameter.put("qr", code);
                            parameter.put("bf", lgo);

                            //reportService.generatePlaque(parameter, pat);

                            JasperReport jasperReport
                                = JasperCompileManager.compileReport(bv.getInputStream());
                            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                            JasperPrint jasperPrint
                                = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                            jpList.add(jasperPrint);
                        }
                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }
                }
                // Pour une bande et non cyle à moteur STH
                else if (nbrPlaque == 1 && !typeVehicule.get().isEstCycleMoteur()) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 1; j++) {
                        String numero = "999 W";
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        int incremental = 1;

                        String body = Pdf417Utils.getBody(commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.get().getNom()+" "+personnePhysique.get().getPrenom());

                        String immatriculation = null;
                        String numeroImmatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();



                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }
                        /*if (infoCommandeCarnetASouche.isEstTransiter()) {
                            immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                                n = region + " " +n+" 99";
                        }else {
                            immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                        }*/

                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                                n = n+" 99";
                            }else {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                            }

                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(7))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n + " 99";
                                n = n+" 99";
                            }else {
                                immatriculation = indice + " " + "WW" + incremental + " " + " " + n;
                            }
                        }

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes()))+hex;

//                        System.out.println("Code barre : "+code);
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//                        System.out.println("Code header : "+codeDatas.getHeader().get(1).getValue());

                        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                        plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                        plaqueImmatriculation.setCodeQR(code);
                        plaqueImmatriculation.setNumeroSerie(numéroSerie);
                        plaqueImmatriculationRepository.save(plaqueImmatriculation);
                        commandeCarnetSouche.get().setEstTraitee(true);
                        commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());
                        Map<String, Object> parameter = new HashMap<>();
//                        parameter.put("logo", logo);
//                        parameter.put("qrcode", code);
//                        parameter.put("immatriculation", immatriculation);

                        parameter.put("code", indice);
                        parameter.put("groupe", String.valueOf(incremental));

                        parameter.put("numero", n);
                        parameter.put("qr", code);
                        parameter.put("bf", lgo);
                        //reportService.generatePlaque(parameter, pat);
                        JasperReport jasperReport
                            = JasperCompileManager.compileReport(bv.getInputStream());
                        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                        JasperPrint jasperPrint
                            = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                        jpList.add(jasperPrint);

                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }
                }
                //Pour cycle à moteur STH
                else if (nbrPlaque == 1 && typeVehicule.get().isEstCycleMoteur()) {
                    Long quantiteCertificat = quantiteCommande * quantiteType;
                    List<JasperPrint> jpList = new ArrayList<>();
                    for (int j = 0; j < 2; j++) {
                        String numero = "999 W";
                        char zero = numero.charAt(0);
                        char un = numero.charAt(1);
                        char deux = numero.charAt(2);
                        String indice = String.valueOf(zero) + String.valueOf(un) + String.valueOf(deux);
                        int incremental = 1;

                        String body = Pdf417Utils.getBody(
                            commandeCarnetSouche.get().getConcessionnaire().getNom(),
                            personnePhysique.get().getNom()+" "+personnePhysique.get().getPrenom());

                        String immatriculation = null;
                        String n = RandomUtil.generateRandomNumericString();
                        String numéroSerie = RandomUtil.generateRandomSerialNumericString();


                        if (plaqueImmatriculationRepository.existsByNumeroSerie(numéroSerie)) {
                            numéroSerie = RandomUtil.generateRandomSerialNumericString();
                        }

                        if (plaqueImmatriculationRepository.existsByNumeroImmatriculation(immatriculation)) {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(5))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + incremental + "WW" + " " + n + " 99";
                                n =n+" 99";
                            }else {
                                immatriculation = indice + " " + incremental + "WW" + " " + n;
                            }

                        } else {
                            if (plaqueImmatriculationRepository.findAllByNumeroImmatriculationContains(indice).size() >= incremental*99999) {
                                Optional<PlaqueImmatriculation> plaqueImmatriculation = plaqueImmatriculationRepository.findTopByNumeroImmatriculationContainsOrderByIdDesc(indice);
                                incremental = Integer.parseInt(String.valueOf(plaqueImmatriculation.get().getNumeroImmatriculation().charAt(5))) + 1;
                            }
                            n = RandomUtil.generateRandomNumericString();
                            if (infoCommandeCarnetASouche.isEstTransiter()) {
                                immatriculation = indice + " " + incremental + "WW" + " " + n + " 99";
                                n = n+" 99";
                            }else {
                                immatriculation = indice + " " + incremental + "WW" + " " + n;
                            }
                        }

                        String data = header+body;

                        byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                        String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                        signToString = URLEncoder.encode(signToString, "UTF-8");
                        String hex = new String (Hex.encode(signToString.getBytes()));
                        String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;

//                        System.out.println("Code : "+code);
//                        CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//
//                        System.out.println("Code header : "+codeDatas.getHeader().get(1).getValue());

                        PlaqueImmatriculation plaqueImmatriculation = new PlaqueImmatriculation();
                        plaqueImmatriculation.setNumeroImmatriculation(immatriculation);
                        plaqueImmatriculation.setCodeQR(code);
                        plaqueImmatriculation.setNumeroSerie(numéroSerie);
                        plaqueImmatriculationRepository.save(plaqueImmatriculation);
                        commandeCarnetSouche.get().setEstTraitee(true);
                        commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());

                        Map<String, Object> parameter = new HashMap<>();
//                        parameter.put("logo", logo);
//                        parameter.put("qrcode", code);
//                        parameter.put("immatriculation", immatriculation);

                        parameter.put("code", indice);
                        parameter.put("groupe", String.valueOf(incremental));

                        parameter.put("numero", n);
                        parameter.put("qr", code);
                        parameter.put("bf", lgo);
//                        reportService.generatePlaqueMoto(parameter, path);
                        JasperReport jasperReport
                            = JasperCompileManager.compileReport(bm.getInputStream());
                        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                        JasperPrint jasperPrint
                            = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());

                        jpList.add(jasperPrint);

                    }
                    if (SystemUtils.IS_OS_LINUX) {
                        new File("/root/BandesAdhésives/").mkdir();
                        String chemin = "/root/BandesAdhésives/";
                        reportService.generateCertificat(jpList, chemin+"BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");

                    } else {
                        new File("C:\\BandesAdhésives").mkdir();
                        String chemin = "C:\\BandesAdhésives";
                        reportService.generateCertificat(jpList, chemin+"\\BandesN_"+personnePhysique.get().getOrganisation().getNom()+RandomUtil.generateRandomSerialNumericStringc()+".pdf");
                    }
                }

            }
        }

    }


    public void findOneLivrer(Long id) {

        log.debug("Request to get : {}", id);
        Optional<CommandeCarnetSouche> commandeCarnetSouche = commandeCarnetSoucheRepository.findById(id);
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = new LivraisonCarnetSoucheDTO();
        livraisonCarnetSoucheDTO.setConcessionnaireId(commandeCarnetSouche.get().getConcessionnaire().getId());
        livraisonCarnetSoucheDTO.setCommandeCarnetSoucheId(commandeCarnetSouche.get().getId());
        livraisonCarnetSoucheDTO.setDateLivraison(ZonedDateTime.now());
        LivraisonCarnetSouche livraisonCarnetSouche = livraisonCarnetSoucheMapper.toEntity(livraisonCarnetSoucheDTO);
        LivraisonCarnetSouche result = livraisonCarnetSoucheRepository.save(livraisonCarnetSouche);

        List<CarnetASouche> carnetASouche = carnetASoucheRepository.findByConcessionnaireId(commandeCarnetSouche.get().getConcessionnaire().getId());
        for (int i=0; i<carnetASouche.size(); i++){
            carnetASouche.get(i).setLivraisonCarnetSouche(result);
        }
        carnetASoucheRepository.saveAll(carnetASouche);
    }

    public HttpServletResponse printFacture(Long id, HttpServletResponse response) throws IOException, JRException {
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO = commandeCarnetSoucheMapper
            .toDto(commandeCarnetSoucheRepository.findById(id).get());
        OrganisationDTO organisationDTO = organisationMapper
            .toDto(organisationRepository.findById(commandeCarnetSoucheDTO.getConcessionnaireId()).get());
        DocIdentificationPMDTO docIdentificationPMDTO = docIdentificationPMMapper
            .toDto(docIdentificationPMRepository.findByOrganisationId(organisationDTO.getId()));
        List<InfoCommandeCarnetASouche> infoCommandeCarnetASouches = infoCommandeCarnetASoucheRepository.findByCommandeCarnetSoucheId(id);

//        String path = loader.getResource("classpath:facture.jrxml").getURI().getPath();
//        String sth = loader.getResource("classpath:stht.png").getURI().getPath();

        ClassPathResource fa = new ClassPathResource("facture.jrxml");
        //ClassPathResource bv = new ClassPathResource("bande_voiture.jrxml");
        ClassPathResource cprSth = new ClassPathResource("stht.png");
        String lgoSth = cprSth.getPath();
        List<Map<String, Object>> dataSource = new ArrayList<>();

        List<JasperPrint> jpList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("sth", lgoSth);
        params.put("numero", commandeCarnetSoucheDTO.getNumeroCommandeCS());
        params.put("client", organisationDTO.getNom());
        params.put("tel", organisationDTO.getNumeroPhone());
        params.put("email", docIdentificationPMDTO.getEmail());
        params.put("ifu", docIdentificationPMDTO.getNumeroIFU());
        params.put("rccm", docIdentificationPMDTO.getNumeroRCCM());
        params.put("pseudo", docIdentificationPMDTO.getSiegeSocial());
        Long pu;
        Long pt = null;
        Long totalHt = Long.parseLong("0");
        String slettre = null;

        for (InfoCommandeCarnetASouche infoCommandeCarnetASouche : infoCommandeCarnetASouches){
            TypeCarnet typeCarnet = typeCarnetRepository.findById(infoCommandeCarnetASouche.getTypeCarnet().getId()).get();
            if (typeCarnet.getTypeVehicule().isEstCycleMoteur()) {
                pu = typeCarnet.getQuantiteCertificat() * 9500;
            }else {
                pu = typeCarnet.getQuantiteCertificat() * 12500;
            }
            pt = pu * infoCommandeCarnetASouche.getQuantiteCommande();
            totalHt = totalHt + pt;
            slettre = FrenchNumberToWords.convert(totalHt) + " ("+ String.format("%,d", totalHt) + ",00) FCFA";
            Map<String, Object> data = new HashMap<>();
            data.put("designation", typeCarnet.getLibelle());
            data.put("quantite", String.valueOf(infoCommandeCarnetASouche.getQuantiteCommande()));
            data.put("prixuni", String.format("%,d", pu) + ",00");
            data.put("prixtotal", String.format("%,d", pt) + ",00");
            dataSource.add(data);

//            if (SystemUtils.IS_OS_LINUX) {
//                new File("/root/Facture/").mkdir();
//                String chemin = "/root/Facture/";
//                reportService.generateCertificat(jpList, chemin+"FactureN°_"+commandeCarnetSoucheDTO.getNumeroCommandeCS()+".pdf");
//            } else {
//                new File("C:\\Facture").mkdir();
//                String chemin = "C:\\Facture";
//                reportService.generateCertificat(jpList, chemin+"\\FactureN°_"+commandeCarnetSoucheDTO.getNumeroCommandeCS()+".pdf");
//            }

        }
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);
        params.put("totalht", String.format("%,d", totalHt) + ",00");
        params.put("somme", "Arrêté la présente facture à la somme de   "+slettre);
        params.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));

        JasperReport jasperReport
            = JasperCompileManager.compileReport(fa.getInputStream());
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, params, jrBeanCollectionDataSource);

        jpList.add(jasperPrint);

        return reportService.downloadPdfFile(response, "facture_N°"+commandeCarnetSoucheDTO.getNumeroCommandeCS(), jpList);
    }
}
