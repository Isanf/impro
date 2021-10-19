import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrixCertificat } from 'app/shared/model/prix-certificat.model';

@Component({
  selector: 'jhi-prix-certificat-detail',
  templateUrl: './prix-certificat-detail.component.html'
})
export class PrixCertificatDetailComponent implements OnInit {
  prixCertificat: IPrixCertificat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prixCertificat }) => (this.prixCertificat = prixCertificat));
  }

  previousState(): void {
    window.history.back();
  }
}
