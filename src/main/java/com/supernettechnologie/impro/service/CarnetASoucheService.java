package com.supernettechnologie.impro.service;

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
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.SystemUtils;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service Implementation for managing {@link CarnetASouche}.
 */
@Service
@Transactional
public class CarnetASoucheService {

    private final Logger log = LoggerFactory.getLogger(CarnetASoucheService.class);

    private final CarnetASoucheRepository carnetASoucheRepository;

    private final CarnetASoucheMapper carnetASoucheMapper;
    @Autowired
    private TypeCarnetRepository typeCarnetRepository;
    @Autowired
    TypeCarnetMapper typeCarnetMapper;
    @Autowired
    private CertificatImmatriculationRepository certificatImmatriculationRepository;
    @Autowired
    private CertificatImmatriculationMapper certificatImmatriculationMapper;
    @Autowired
    private ResourceLoader loader;
    @Autowired
    ReportService reportService;
    @Autowired
    DecodeQuitanceService decodeQuitanceService;
    @Autowired
    private InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;
    @Autowired
    private InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;
    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogActivityService logActivityService;
    @Autowired
    private LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper;
    @Autowired
    private LivraisonCarnetSoucheRepository livraisonCarnetSoucheRepository;
    @Autowired
    private ImmatriculationRepository immatriculationRepository;

    public CarnetASoucheService(CarnetASoucheRepository carnetASoucheRepository, CarnetASoucheMapper carnetASoucheMapper) {
        this.carnetASoucheRepository = carnetASoucheRepository;
        this.carnetASoucheMapper = carnetASoucheMapper;
    }

    /**
     * Save a carnetASouche.
     *
     * @param carnetASoucheDTO the entity to save.
     * @return the persisted entity.
     */
    public CarnetASoucheDTO save(CarnetASoucheDTO carnetASoucheDTO) throws Exception {
        log.debug("Request to save CarnetASouche : {}", carnetASoucheDTO);
        int nombre = Integer.parseInt(carnetASoucheDTO.getNumero());
        Optional<TypeCarnet> typeCarnet = typeCarnetRepository.findById(carnetASoucheDTO.getTypeCarnetId());
        Long nombreCertificat = typeCarnet.get().getQuantiteCertificat();
        String path = loader.getResource("classpath:immatricul.jasper").getURI().getPath();
        String paths = loader.getResource("classpath:immtri_back.jrxml").getURI().getPath();
        String logo_path = loader.getResource("classpath:bfa.png").getURI().getPath();
        String transp = loader.getResource("classpath:bftrans.png").getURI().getPath();
        String signature = loader.getResource("classpath:signer.png").getURI().getPath();

        ClassPathResource classPathResource = new ClassPathResource("immatricul.jasper");
        ClassPathResource cprLogo = new ClassPathResource("bfa.png");
        ClassPathResource cprSignatur = new ClassPathResource("signer.png");

        String lgo = cprLogo.getPath();
        String signe = cprSignatur.getPath();

        String header = Pdf417Utils.getHeaders("10", "SUPERNET");
        System.out.println("Taille du header : "+header.length());

        List<CarnetASouche> carnetASoucheList = new ArrayList<>();
        for (int i = 0; i<nombre; i++){
            CarnetASouche carnetASouche = new CarnetASouche();
            carnetASouche.setNumero(RandomUtil.generateRandomSerialNumericStringc());
            carnetASouche.setDateImpression(ZonedDateTime.now());
            carnetASouche.setTypeCarnet(typeCarnet.get());
            List<CertificatImmatriculation> certificatImmatriculationList = new ArrayList<>();

            for (int j =0; j<nombreCertificat; j++){
                CertificatImmatriculation certificatImmatriculation = new CertificatImmatriculation();
                String numéroSerie = RandomUtil.generateRandomSerialNumericStrings();
                if (certificatImmatriculationRepository.existsByNumero(numéroSerie)) {
                    numéroSerie = RandomUtil.generateRandomSerialNumericStrings();
                }
                certificatImmatriculation.setNumero(numéroSerie);
                certificatImmatriculation.setCarnetASouche(carnetASouche);

                String body = Pdf417Utils.getBody("STH", numéroSerie);

                String data = header+body;
                byte[] signatureBytes = Pdf417Utils.sign(data, Signer.PRIVATE_KEY_PATH);
                String signToString = DatatypeConverter.printBase64Binary(signatureBytes);
                signToString = URLEncoder.encode(signToString, "UTF-8");
                String hex = new String (Hex.encode(signToString.getBytes()));
                String code =  data +"FF" + new String(Hex.encode(("" + (hex.length() / 2)).getBytes())) + hex;
//                CodeDatas codeDatas = decodeQuitanceService.decodage(code);
//                System.out.println("Code header : "+codeDatas.getHeader().get(0).getValue());

                certificatImmatriculation.setCodeQr(code);
                certificatImmatriculationList.add(certificatImmatriculation);
            }
            carnetASouche.setCertificatImmatriculations(new HashSet<>(certificatImmatriculationList));
            carnetASoucheList.add(carnetASouche);
        }
        List<CarnetASouche> carnetASouches = carnetASoucheRepository.saveAll(carnetASoucheList);
        for (CarnetASouche carnetASouche : carnetASoucheList){
            List<JasperPrint> jpList = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("logo", lgo);
            for (CertificatImmatriculation certificatImmatriculation : carnetASouche.getCertificatImmatriculations()){

                params.put("code", certificatImmatriculation.getCodeQr());
                params.put("numero", "N° : "+certificatImmatriculation.getNumero());
                params.put("signer", signe);
                JasperReport jasperReport
                    = (JasperReport) JRLoader.loadObject(classPathResource.getInputStream());
                jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
                JasperPrint jasperPrint
                    = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

                jpList.add(jasperPrint);
            }
            if (SystemUtils.IS_OS_LINUX) {
                new File("/root/CarnetASouches").mkdir();
                String chemin = "/root/CarnetASouches/";
                reportService.generateCertificat(jpList, chemin+"carnet_"+carnetASouche.getNumero()+".pdf");

            } else {
                new File("C:\\CarnetASouches").mkdir();
                String chemin = "C:\\CarnetASouches";
                reportService.generateCertificat(jpList, chemin+"\\carnet_"+carnetASouche.getNumero()+".pdf");
            }

        }

        return carnetASoucheMapper.toDto(carnetASouches.get(0));
    }

    /**
     * Get all the carnetASouches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CarnetASoucheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarnetASouches");
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Page<CarnetASoucheDTO> carnetASoucheDTOPage = carnetASoucheRepository.findByConcessionnaireId(pageable, personnePhysique.get().getOrganisation().getId())
            .map(carnetASoucheMapper::toDto);

        List<CarnetASoucheDTO> carnetASoucheDTOList = new ArrayList<>();
        for (CarnetASoucheDTO carnetASoucheDTO :carnetASoucheDTOPage){
            TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnetRepository.findById(carnetASoucheDTO.getTypeCarnetId()).get());
            OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(carnetASoucheDTO.getConcessionnaireId()).get());
            LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper
                .toDto(livraisonCarnetSoucheRepository.findById(carnetASoucheDTO.getLivraisonCarnetSoucheId()).get());
            carnetASoucheDTO.setLivraisonCarnetSoucheDTO(livraisonCarnetSoucheDTO);
            carnetASoucheDTO.setTypeCarnetDTO(typeCarnetDTO);
            carnetASoucheDTO.setOrganisationDTO(organisationDTO);
            carnetASoucheDTOList.add(carnetASoucheDTO);
        }
        return carnetASoucheDTOPage;
    }
    @Transactional(readOnly = true)
    public Page<CarnetASoucheDTO> findAllAdmin(Pageable pageable) {
        log.debug("Request to get all CarnetASouches");
        Page<CarnetASoucheDTO> carnetASoucheDTOPage = carnetASoucheRepository.findAll(pageable)
            .map(carnetASoucheMapper::toDto);
        List<CarnetASoucheDTO> carnetASoucheDTOList = new ArrayList<>();
        for (CarnetASoucheDTO carnetASoucheDTO :carnetASoucheDTOPage){
            TypeCarnetDTO typeCarnetDTO = typeCarnetMapper.toDto(typeCarnetRepository.findById(carnetASoucheDTO.getTypeCarnetId()).get());
            if (carnetASoucheDTO.getConcessionnaireId() != null) {
                OrganisationDTO organisationDTO = organisationMapper.toDto(organisationRepository.findById(carnetASoucheDTO.getConcessionnaireId()).get());
                carnetASoucheDTO.setOrganisationDTO(organisationDTO);
            }
            if (carnetASoucheDTO.getLivraisonCarnetSoucheId() != null) {
                LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO = livraisonCarnetSoucheMapper
                    .toDto(livraisonCarnetSoucheRepository.findById(carnetASoucheDTO.getLivraisonCarnetSoucheId()).get());
                carnetASoucheDTO.setLivraisonCarnetSoucheDTO(livraisonCarnetSoucheDTO);
            }
            carnetASoucheDTO.setTypeCarnetDTO(typeCarnetDTO);
            carnetASoucheDTOList.add(carnetASoucheDTO);
        }
        //carnetASoucheDTOPage = new PageImpl<>(carnetASoucheDTOList);
        return carnetASoucheDTOPage;
    }


    /**
     * Get one carnetASouche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarnetASoucheDTO> findOne(Long id) {
        log.debug("Request to get CarnetASouche : {}", id);
        return carnetASoucheRepository.findById(id)
            .map(carnetASoucheMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CarnetASoucheDTO> findOneByInfoId(Long id) {
        log.debug("Request to get CarnetASouche : {}", id);
        Optional<InfoCommandeCarnetASoucheDTO> infoCommandeCarnetASoucheDTO =
            infoCommandeCarnetASoucheRepository.findById(id).map(infoCommandeCarnetASoucheMapper::toDto);
        int taille = infoCommandeCarnetASoucheDTO.get().getQuantiteCommande().intValue();
        Pageable limit = PageRequest.of(0, taille);
        Page<CarnetASoucheDTO> carnetASoucheDTOPage = carnetASoucheRepository.findAllByTypeCarnetIdAndConcessionnaireIsNull(limit, infoCommandeCarnetASoucheDTO.get().getTypeCarnetId()).map(carnetASoucheMapper::toDto);

        return carnetASoucheDTOPage;
    }

    /**
     * Delete the carnetASouche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarnetASouche : {}", id);

        carnetASoucheRepository.deleteById(id);
    }

    public Long nbCarnet() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        Long totalCarnet = carnetASoucheRepository.countAllByConcessionnaireId(personnePhysique.get().getOrganisation().getId());
        return totalCarnet;
    }

    public int nbCarnetCertif() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<CarnetASoucheDTO> carnetASoucheDTOS = carnetASoucheMapper
            .toDto(carnetASoucheRepository.findByConcessionnaireId(personnePhysique.get().getOrganisation().getId()));
        int certificats = 0;
        for (CarnetASoucheDTO carnetASoucheDTO : carnetASoucheDTOS){
            certificats = certificats + certificatImmatriculationRepository.findAllByCarnetASoucheId(carnetASoucheDTO.getId()).size();
        }
        return certificats;
    }

    public int nbCarnetCertif4Org(ZonedDateTime dat, ZonedDateTime dat2, Long idOrg) {
        List<CarnetASoucheDTO> carnetASoucheDTOS = carnetASoucheMapper
            .toDto(carnetASoucheRepository.
                findAllByDateLivraisonBetweenAndConcessionnaireId(dat,dat2,idOrg));
        int certificats = 0;
        for (CarnetASoucheDTO carnetASoucheDTO : carnetASoucheDTOS){
            certificats = certificats + certificatImmatriculationRepository.findAllByCarnetASoucheId(carnetASoucheDTO.getId()).size();
        }
        return certificats;
    }

    public int nbCarnet4Org(ZonedDateTime dat, ZonedDateTime dat2, Long idOrg) {
        return carnetASoucheRepository.
            countAllByDateLivraisonBetweenAndConcessionnaireId(dat,dat2,idOrg);
    }

    public int nbUsed() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());

        List<CarnetASoucheDTO> carnetASoucheDTOS = carnetASoucheMapper
            .toDto(carnetASoucheRepository.findByConcessionnaireId(personnePhysique.get().getOrganisation().getId()));
        for (CarnetASoucheDTO carnetASoucheDTO : carnetASoucheDTOS){
            if (!certificatImmatriculationRepository.existsByCarnetASoucheId(carnetASoucheDTO.getId())) {
                carnetASoucheDTOS.remove(carnetASoucheDTO);
            }
        }
        return carnetASoucheDTOS.size();
    }

    public Long nbCarnetAdmin() {
        return carnetASoucheRepository.count();
    }

    public int nbCarnetAdminUsed() {
        List<CarnetASoucheDTO> carnetASoucheDTOS = carnetASoucheMapper
            .toDto(carnetASoucheRepository.findAll());

        for (CarnetASoucheDTO carnetASoucheDTO : carnetASoucheDTOS){
            if (!certificatImmatriculationRepository.existsByCarnetASoucheId(carnetASoucheDTO.getId())) {
                carnetASoucheDTOS.remove(carnetASoucheDTO);
            }
        }
        return carnetASoucheDTOS.size();
    }
    public int nbCarnetAdminAllCertif() {

        return Integer.parseInt(String.valueOf(certificatImmatriculationRepository.count()));
    }

    public int nbCarnetCertifUsed() {
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        List<CarnetASoucheDTO> carnetASoucheDTOS = carnetASoucheMapper
            .toDto(carnetASoucheRepository.findByConcessionnaireId(personnePhysique.get().getOrganisation().getId()));
        List<CertificatImmatriculationDTO> certificatImmatriculationDTOS = new ArrayList<>();
        for (CarnetASoucheDTO carnetASoucheDTO : carnetASoucheDTOS){
            List<CertificatImmatriculationDTO> certificatImmatriculationDTOList = certificatImmatriculationMapper
                .toDto(certificatImmatriculationRepository.findAllByCarnetASoucheId(carnetASoucheDTO.getId()));
            for (CertificatImmatriculationDTO certificatImmatriculationDTO : certificatImmatriculationDTOList){
                if (!immatriculationRepository.existsByCertificatImmatriculationId(certificatImmatriculationDTO.getId())) {
                    certificatImmatriculationDTOS.add(certificatImmatriculationDTO);
                }
            }
        }
        return certificatImmatriculationDTOS.size();
    }

}
