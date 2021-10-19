package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.domain.*;
import com.supernettechnologie.impro.repository.*;
import com.supernettechnologie.impro.security.SecurityUtils;
import com.supernettechnologie.impro.service.StockService;
import com.supernettechnologie.impro.service.util.RandomUtil;
import com.supernettechnologie.impro.web.rest.errors.BadRequestAlertException;
import com.supernettechnologie.impro.service.dto.StockDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.supernettechnologie.impro.domain.Stock}.
 */
@RestController
@RequestMapping("/api")
public class StockResource {

    private final Logger log = LoggerFactory.getLogger(StockResource.class);

    private static final String ENTITY_NAME = "stock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockService stockService;

    @Autowired
    private PersonnePhysiqueRepository personnePhysiqueRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TypeVehiculeRepository typeVehiculeRepository;

    @Autowired
    private MarqueVehiculeRepository marqueVehiculeRepository;

    public StockResource(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * {@code POST  /stocks} : Create a new stock.
     *
     * @param stockDTO the stockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockDTO, or with status {@code 400 (Bad Request)} if the stock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stocks")
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) throws URISyntaxException {
        log.debug("REST request to save Stock : {}", stockDTO);
        if (stockDTO.getId() != null) {
            throw new BadRequestAlertException("A new stock cannot already have an ID", ENTITY_NAME, "idexists");
        }

        StockDTO result = stockService.save(stockDTO);
        return ResponseEntity.created(new URI("/api/stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PostMapping("/stocks/file")
    public void createStockFile(@RequestParam("file") MultipartFile file) {

        Stock s = new Stock();
        s.setNumeroStock(""+(stockRepository.count()+1));
        s.setDateStock(ZonedDateTime.now());
        Optional<PersonnePhysique> personnePhysique = personnePhysiqueRepository.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        s.setConcessionnaire(personnePhysique.get().getOrganisation());
        s = stockRepository.save(s);

        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file.getBytes())));
            while((line = br.readLine()) != null) {
                String[] data = line.split(";");
                Vehicule v = new Vehicule();
                v.setNumeroChassis(data[0]);
                TypeVehicule type = typeVehiculeRepository.findOneByLibelle(data[1]);
                v.setTypeVehicule(type);
                MarqueVehicule marque = marqueVehiculeRepository.findOneByLibelle(data[2]);
                v.setMarqueVehicule(marque);
                v.setModel(data[3]);
                v.setPuissanceReel(data[4]);
                v.setPuissanceAdmin(data[5]);
                v.setCouleur(data[6]);
                v.setPoidsVide(Integer.parseInt(data[7]));
                v.setChargeUtile(Integer.parseInt(data[8]));
                v.setPtac(Integer.parseInt(data[9]));
                v.setPtra(Integer.parseInt(data[10]));
                v.setNbrPlace(Integer.parseInt(data[11]));
                v.setCapacite(Integer.parseInt(data[12]));
                SimpleDateFormat  simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
                //LocalDate d = LocalDate.parse(data[13]);
                LocalDate d = LocalDate.parse(data[13]);
                //simpleFormat.format(d);
                v.setDateMiseCirculation(d);
                v.setRegime(data[14]);
                v.setNoDedouanement(data[15]);
                //LocalDate d1 = LocalDate.parse(data[16]);
                LocalDate d1 = LocalDate.parse(data[16]);
                //simpleFormat.format(d1);
                v.setDateDedouanement(d1);
                //v.setEnergie(data[17]);
                v.setStock(s);
                log.debug("**************************** : {}", v.getModel());
                log.debug("**************************** : {}", v.getModel());
                log.debug("**************************** : {}", v.getModel());
                v = vehiculeRepository.save(v);
                System.out.println("Vehicules : "+v.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@code PUT  /stocks} : Updates an existing stock.
     *
     * @param stockDTO the stockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockDTO,
     * or with status {@code 400 (Bad Request)} if the stockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stocks")
    public ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDTO) throws URISyntaxException {
        log.debug("REST request to update Stock : {}", stockDTO);
        if (stockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockDTO result = stockService.save(stockDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stocks} : get all the stocks.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stocks in body.
     */
    @GetMapping("/stocks")
    public ResponseEntity<List<StockDTO>> getAllStocks(Pageable pageable) {
        log.debug("REST request to get a page of Stocks");
        Page<StockDTO> page = stockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/stocksForConcessionnaire")
    public ResponseEntity<List<StockDTO>> getAllStocksForConcessionnaire(Pageable pageable) {
        log.debug("REST request to get a page of Stocks");
        Page<StockDTO> page = stockService.findAllForConcessionnaire(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stocks/:id} : get the "id" stock.
     *
     * @param id the id of the stockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stocks/{id}")
    public ResponseEntity<StockDTO> getStock(@PathVariable Long id) {
        log.debug("REST request to get Stock : {}", id);
        Optional<StockDTO> stockDTO = stockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockDTO);
    }

    /**
     * {@code DELETE  /stocks/:id} : delete the "id" stock.
     *
     * @param id the id of the stockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        log.debug("REST request to delete Stock : {}", id);
        stockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
