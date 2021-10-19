package com.supernettechnologie.impro.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.mail.Multipart;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Stock} entity.
 */
@ApiModel(description = "The Stock entity.\n@author A true hipster")
public class StockDTO implements Serializable {

    private Long id;

    /**
     * numeroStock
     */
    @ApiModelProperty(value = "numeroStock")
    private String numeroStock;

    @Lob
    private byte[] fichierStock;

    private String fichierStockContentType;
    private ZonedDateTime dateStock;

    private Long concessionnaireId;
    private File file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroStock() {
        return numeroStock;
    }

    public void setNumeroStock(String numeroStock) {
        this.numeroStock = numeroStock;
    }

    public byte[] getFichierStock() {
        return fichierStock;
    }

    public void setFichierStock(byte[] fichierStock) {
        this.fichierStock = fichierStock;
    }

    public String getFichierStockContentType() {
        return fichierStockContentType;
    }

    public void setFichierStockContentType(String fichierStockContentType) {
        this.fichierStockContentType = fichierStockContentType;
    }

    public ZonedDateTime getDateStock() {
        return dateStock;
    }

    public void setDateStock(ZonedDateTime dateStock) {
        this.dateStock = dateStock;
    }

    public Long getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(Long organisationId) {
        this.concessionnaireId = organisationId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (stockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", numeroStock='" + getNumeroStock() + "'" +
            ", fichierStock='" + getFichierStock() + "'" +
            ", dateStock='" + getDateStock() + "'" +
            ", concessionnaireId=" + getConcessionnaireId() +
            "}";
    }
}
