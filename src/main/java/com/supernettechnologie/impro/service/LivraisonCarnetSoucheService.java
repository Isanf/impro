package com.supernettechnologie.impro.service;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.service.dto.CarnetASoucheDTO;
import com.supernettechnologie.impro.service.dto.LivraisonCarnetSoucheDTO;
import com.supernettechnologie.impro.service.mapper.CarnetASoucheMapper;
import com.supernettechnologie.impro.service.mapper.LivraisonCarnetSoucheMapper;
import com.supernettechnologie.impro.service.util.RandomUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * Service Implementation for managing {@link LivraisonCarnetSouche}.
 */
@Service
@Transactional
public class LivraisonCarnetSoucheService {

    private final Logger log = LoggerFactory.getLogger(LivraisonCarnetSoucheService.class);

    private final LivraisonCarnetSoucheRepository livraisonCarnetSoucheRepository;

    private final LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper;

    @Autowired
    private CarnetASoucheRepository carnetASoucheRepository;
    @Autowired
    private CarnetASoucheMapper carnetASoucheMapper;
    @Autowired
    private CommandeCarnetSoucheRepository commandeCarnetSoucheRepository;
    @Autowired
    private InfoCommandeCarnetASoucheRepository infoCommandeCarnetASoucheRepository;
    @Autowired
    private ResourceLoader loader;
    @Autowired
    ReportService reportService;
    @Autowired
    private TypeCarnetRepository typeCarnetRepository;

    public LivraisonCarnetSoucheService(LivraisonCarnetSoucheRepository livraisonCarnetSoucheRepository, LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper) {
        this.livraisonCarnetSoucheRepository = livraisonCarnetSoucheRepository;
        this.livraisonCarnetSoucheMapper = livraisonCarnetSoucheMapper;
    }

    /**
     * Save a livraisonCarnetSouche.
     *
     * @param livraisonCarnetSoucheDTO the entity to save.
     * @return the persisted entity.
     */
    public LivraisonCarnetSoucheDTO save(LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO) throws IOException, JRException {
        log.debug("Request to save LivraisonCarnetSouche : {}", livraisonCarnetSoucheDTO);
        LivraisonCarnetSouche livraisonCarnetSouche = livraisonCarnetSoucheMapper.toEntity(livraisonCarnetSoucheDTO);
        Optional<CommandeCarnetSouche> commandeCarnetSouche = commandeCarnetSoucheRepository.findById(livraisonCarnetSouche.getCommandeCarnetSouche().getId());
        commandeCarnetSouche.get().setEstLivree(true);
        Optional<InfoCommandeCarnetASouche> infoCommandeCarnetASouche = infoCommandeCarnetASoucheRepository.findById(livraisonCarnetSoucheDTO.getInfosId());
        infoCommandeCarnetASouche.get().setEstDeliver(true);
        TypeCarnet typeCarnet = typeCarnetRepository.findById(infoCommandeCarnetASouche.get().getTypeCarnet().getId()).get();
        List<CarnetASoucheDTO> carnetASoucheDTOS = livraisonCarnetSoucheDTO.getCarnetASoucheDTO();
        List<CarnetASouche> carnetASouches = new ArrayList<>();
        livraisonCarnetSouche.setNumeroLivraisonCS(RandomUtil.generateRandomNumericString() );
        livraisonCarnetSouche = livraisonCarnetSoucheRepository.save(livraisonCarnetSouche);

//        String path = loader.getResource("classpath:livraisons.jrxml").getURI().getPath();
//        String logo_path = loader.getResource("classpath:bfa.png").getURI().getPath();
//        String sth = loader.getResource("classpath:sthl.png").getURI().getPath();

        ClassPathResource livraison = new ClassPathResource("livraisons.jrxml");
        ClassPathResource cprLogo = new ClassPathResource("bfa.png");
        ClassPathResource cprSt = new ClassPathResource("stht.png");


        String lgo = cprLogo.getPath();
        String sth = cprSt.getPath();


        List<JasperPrint> jpList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("logo", commandeCarnetSouche.get().getConcessionnaire().getNom());
        params.put("nomCommande", typeCarnet.getLibelle());
        params.put("qteCommande", String.valueOf(infoCommandeCarnetASouche.get().getQuantiteCommande()));
        params.put("qteLivre", String.valueOf(infoCommandeCarnetASouche.get().getQuantiteCommande()));
        //params.put("totalCommande", String.valueOf(infoCommandeCarnetASouche.get().getQuantiteCommande()));
        params.put("totalLivre", String.valueOf(infoCommandeCarnetASouche.get().getQuantiteCommande()));
        params.put("numero", "00"+livraisonCarnetSouche.getNumeroLivraisonCS()+"/"+String.valueOf(LocalDate.now().getYear()));
        params.put("date", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        params.put("sth", sth);
        params.put("client", commandeCarnetSouche.get().getConcessionnaire().getNom());

        commandeCarnetSoucheRepository.save(commandeCarnetSouche.get());
        infoCommandeCarnetASoucheRepository.save(infoCommandeCarnetASouche.get());
        for (CarnetASoucheDTO carnetASoucheDTO :carnetASoucheDTOS){
            carnetASoucheDTO.setConcessionnaireId(livraisonCarnetSoucheDTO.getConcessionnaireId());
            carnetASoucheDTO.setDateLivraison(livraisonCarnetSoucheDTO.getDateLivraison());
            carnetASoucheDTO.setLivraisonCarnetSoucheId(livraisonCarnetSouche.getId());
            carnetASouches.add(carnetASoucheMapper.toEntity(carnetASoucheDTO));
        }
        carnetASoucheRepository.saveAll(carnetASouches);

        JasperReport jasperReport
            = JasperCompileManager.compileReport(livraison.getInputStream());
        jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
        JasperPrint jasperPrint
            = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        jpList.add(jasperPrint);

        if (SystemUtils.IS_OS_LINUX) {
            new File("/root/LivraisonCAS").mkdir();
            String chemin = "/root/LivraisonCAS/";
            reportService.generateCertificat(jpList, chemin+ livraisonCarnetSouche.getNumeroLivraisonCS() +".pdf");
            return livraisonCarnetSoucheMapper.toDto(livraisonCarnetSouche);

        } else {
            new File("C:\\LivraisonCAS").mkdir();
            String chemin = "C:\\LivraisonCAS";
            reportService.generateCertificat(jpList, chemin+"\\"+livraisonCarnetSouche.getNumeroLivraisonCS()+".pdf");
            return livraisonCarnetSoucheMapper.toDto(livraisonCarnetSouche);
        }
    }

    /**
     * Get all the livraisonCarnetSouches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LivraisonCarnetSoucheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LivraisonCarnetSouches");
        return livraisonCarnetSoucheRepository.findAll(pageable)
            .map(livraisonCarnetSoucheMapper::toDto);
    }

    /**
     * Get one livraisonCarnetSouche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LivraisonCarnetSoucheDTO> findOne(Long id) {
        log.debug("Request to get LivraisonCarnetSouche : {}", id);
        return livraisonCarnetSoucheRepository.findById(id)
            .map(livraisonCarnetSoucheMapper::toDto);
    }


    public int find4Org(ZonedDateTime dat, ZonedDateTime dat2, Long idOrg){
        int totIm = 0;
        totIm = livraisonCarnetSoucheRepository.countAllByDateLivraisonBetweenAndConcessionnaireId(
            dat,dat2.plusDays(1), idOrg);
        return totIm;
    }


    /**
     * Delete the livraisonCarnetSouche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LivraisonCarnetSouche : {}", id);
        livraisonCarnetSoucheRepository.deleteById(id);
    }
}
