package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;

public class DatesModelDTO {
    private ZonedDateTime dateDebut;
    private ZonedDateTime dateFin;

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }
}
