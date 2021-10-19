package com.supernettechnologie.impro.web.rest;

import com.supernettechnologie.impro.service.CertificatImmatriculationService;
import com.supernettechnologie.impro.service.CommandeCarnetSoucheService;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class validCarnetASouce {

    private final CommandeCarnetSoucheService commandeCarnetSoucheService;
    private final CertificatImmatriculationService certificatImmatriculationService;

    public validCarnetASouce(CommandeCarnetSoucheService commandeCarnetSoucheService, CertificatImmatriculationService certificatImmatriculationService) {
        this.commandeCarnetSoucheService = commandeCarnetSoucheService;
        this.certificatImmatriculationService = certificatImmatriculationService;
    }





    @GetMapping("/commande-carnet-souches/{id}/livrer")
    public String livrerCommandeCarnetSouche(@PathVariable Long id) {
        commandeCarnetSoucheService.findOneLivrer(id);
        return "Carnet created and ready to print";
    }
}
