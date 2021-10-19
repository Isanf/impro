import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

@Component({
  selector: 'jhi-certificat-immatriculation-detail',
  templateUrl: './certificat-immatriculation-detail.component.html'
})
export class CertificatImmatriculationDetailComponent implements OnInit {
  certificatImmatriculation: ICertificatImmatriculation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ certificatImmatriculation }) => (this.certificatImmatriculation = certificatImmatriculation));
  }

  previousState(): void {
    window.history.back();
  }
}
