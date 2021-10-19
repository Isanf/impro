import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IVente } from 'app/shared/model/vente.model';
import { VenteService } from 'app/entities/vente/vente.service';
import { NbGlobalPhysicalPosition, NbToastrService } from '@nebular/theme';

@Component({
  selector: 'jhi-vente-detail',
  templateUrl: './vente-detail.component.html'
})
export class VenteDetailComponent implements OnInit {
  vente: IVente | null = null;
  loading = false;
  loading2 = false;
  loading3 = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private venteService: VenteService,
    private toastrService: NbToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vente }) => (this.vente = vente));
  }

  previousState(): void {
    window.history.back();
  }

  toggleLoadingAnimationVente(id: number) {
    this.loading = true;
    this.venteService.findCertificatVente(id).subscribe(
      (blob: Blob) => {
        this.toastrService.show('Certificat de vente imprimé', 'Success', {
          duration: 3000,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          preventDuplicates: false,
          status: 'success'
        });
        this.router.navigate(['vente']);
      },
      error => {
        this.loading = false;
      }
    );
  }

  toggleLoadingAnimationConformite(id: number) {
    this.loading2 = true;
    this.venteService.findCertificatConformite(id).subscribe(
      (blob: Blob) => {
        this.toastrService.show('Certificat de conformité imprimé', 'Success', {
          duration: 3000,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          preventDuplicates: false,
          status: 'success'
        });
        this.router.navigate(['vente']);
      },
      error => {
        this.loading2 = false;
      }
    );
  }
  toggleLoadingFacture(id: number) {
    this.loading3 = true;
    this.venteService.findFactureVente(id).subscribe(
      (blob: Blob) => {
        this.toastrService.show('Facture de vente Imprimé!', 'Success', {
          duration: 3000,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          preventDuplicates: false,
          status: 'success'
        });
        this.router.navigate(['vente']);
      },
      error => {
        this.loading3 = false;
      }
    );
  }
}
