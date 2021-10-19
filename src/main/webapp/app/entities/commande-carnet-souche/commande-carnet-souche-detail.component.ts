import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { CommandeCarnetSouche, ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IInfoCommandeCarnetASouche, InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { HttpResponse } from '@angular/common/http';
import { InfoCommandeCarnetASoucheService } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche.service';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { NbGlobalPhysicalPosition, NbToastrService } from '@nebular/theme';

@Component({
  selector: 'jhi-commande-carnet-souche-detail',
  templateUrl: './commande-carnet-souche-detail.component.html'
})
export class CommandeCarnetSoucheDetailComponent implements OnInit {
  commandeCarnetSouche: ICommandeCarnetSouche | null = null;
  infosCcs: InfoCommandeCarnetASouche[] = [];
  infosDeliv: InfoCommandeCarnetASouche[] = [];
  typeCarnet: ITypeCarnet[] = [];
  modalRef: NgbModalRef;
  page!: number;
  loading = false;
  loadingP = false;
  is: boolean;
  loadingF = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected infoCommandeCarnetASoucheService: InfoCommandeCarnetASoucheService,
    private modalService: NgbModal,
    private service: CommandeCarnetSoucheService,
    private route: Router,
    private toastrService: NbToastrService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeCarnetSouche }) => {
      this.commandeCarnetSouche = commandeCarnetSouche;
      const pageToLoad: number = this.page;
      this.infosCcs = this.commandeCarnetSouche.infoCommandeCarnetASouches;
      this.typeCarnet = this.commandeCarnetSouche.typeCarnetDTOS;

      for (const inf of this.infosCcs) {
        if (inf.estDeliver !== null) {
          this.infosDeliv.push(inf);
        }
      }
      //alert('Infos = '+this.infosCcs.length+'\nLivrer = '+this.infosDeliv.length)
    });
  }

  previousState(): void {
    window.history.back();
  }

  toggleLoadingAnimation(id: number) {
    this.loading = true;
    this.service.valideConcess(id).subscribe(
      resp => {
        const newBlob = new Blob([resp], { type: 'application/pdf' });

        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(newBlob);
          return;
        }

        const data = window.URL.createObjectURL(newBlob);

        const link = document.createElement('a');
        link.href = data;
        //link.download = "Je kar.pdf";
        // this is necessary as link.click() does not work on the latest firefox
        link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

        setTimeout(function() {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(data);
          link.remove();
        }, 100);
        this.toastrService.show('Bond de Commande Imprimé!', 'Success', {
          duplicatesBehaviour: undefined,
          duration: 3000,
          limit: 0,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          status: 'success'
        });
        this.route.navigate(['commande-carnet-souche']);
      },
      error => {
        this.loading = false;
      }
    );
  }

  processus(id: number) {
    this.loadingP = true;
    this.service.validAdmin(id).subscribe(
      (res: HttpResponse<ICommandeCarnetSouche>) => {
        this.loadingP = false;
        this.toastrService.show('Bandes adhésives générées avec succès!', 'Success', {
          duration: 3000,
          limit: 0,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          status: 'success'
        });
        this.route.navigate(['commande-carnet-souche']);
      },
      error => {
        this.loadingP = false;
      }
    );
  }

  isAllDelivery(): boolean {
    let isdel = false;
    for (const inf of this.infosCcs) {
      if (inf.estDeliver) {
        isdel = true;
      }
    }
    return isdel;
  }

  facture(id: number) {
    this.loadingF = true;
    this.service.printFacture(id).subscribe(
      res => {
        this.loadingF = false;
        const blob = new Blob([res], { type: 'application/pdf' });
        const blobUrl = URL.createObjectURL(blob);
        window.open(blobUrl, 'blank');

        this.toastrService.show('Facture imprimé!', 'Success', {
          duration: 3000,
          limit: 0,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          status: 'success'
        });
        this.route.navigate(['commande-carnet-souche']);
      },
      error => {
        this.loadingF = false;
      }
    );
  }
}
