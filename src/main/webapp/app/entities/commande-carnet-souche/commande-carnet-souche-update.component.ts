import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommandeCarnetSouche, CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { CommandeCarnetSoucheService } from './commande-carnet-souche.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { ITypeCarnet, TypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from 'app/entities/type-carnet/type-carnet.service';
import { InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { TypePayement } from 'app/shared/model/enumerations/type-payement';

type SelectableEntity = ICommandeCarnetSouche | ITypeCarnet;

@Component({
  selector: 'jhi-commande-carnet-souche-update',
  templateUrl: './commande-carnet-souche-update.component.html'
})
export class CommandeCarnetSoucheUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  typecarnets: ITypeCarnet[] = [];
  infosList: InfoCommandeCarnetASouche[] = [];
  listShow: InfoCommandeCarnetASouche[] = [];
  iccas: InfoCommandeCarnetASouche;
  iccasShow: InfoCommandeCarnetASouche;
  typeLibele: TypeCarnet;
  typePaiement: TypePayement;
  total: any = 0;
  paiement: string;
  tt: any = 0;
  total1: any = 0;
  totalT: any = 0;
  totalv: any = 0;
  total2: any = 0;
  totalv2: any = 0;
  total3: any = 0;
  totalv3: any = 0;
  @ViewChild('totalPrix', { static: true }) totalPrix;

  editForm = this.fb.group({
    id: [],
    numeroCommandeCS: [],
    dateCommandeCS: [],
    estValide: [],
    isTransited: [],
    concessionnaireId: [],
    supernetId: [],
    numeroCommande: [],
    dateCommande: [],
    quantiteCommande: [],
    typeCarnetId: [],
    typePaiement: [],
    telephone: [],
    otp: [],
    telephoneMobicash: [],
    otpMobicash: [],
    numeroCheque: []
  });
  checked = false;
  checked2 = false;

  constructor(
    protected commandeCarnetSoucheService: CommandeCarnetSoucheService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected typeCarnetService: TypeCarnetService
  ) {}

  ngOnInit(): void {
    this.infosList.splice(0);
    this.activatedRoute.data.subscribe(({ commandeCarnetSouche }) => {
      if (!commandeCarnetSouche.id) {
        const today = moment().startOf('day');
        commandeCarnetSouche.dateCommandeCS = today;
      }
      this.updateForm(commandeCarnetSouche);
      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));
      this.typeCarnetService.query().subscribe((res: HttpResponse<ITypeCarnet[]>) => (this.typecarnets = res.body || []));
    });
  }

  updateForm(commandeCarnetSouche: ICommandeCarnetSouche): void {
    this.editForm.patchValue({
      id: commandeCarnetSouche.id,
      numeroCommandeCS: commandeCarnetSouche.numeroCommandeCS,
      dateCommandeCS: commandeCarnetSouche.dateCommandeCS ? commandeCarnetSouche.dateCommandeCS.format(DATE_TIME_FORMAT) : null,
      estValide: commandeCarnetSouche.estValide,
      concessionnaireId: commandeCarnetSouche.concessionnaireId,
      supernetId: commandeCarnetSouche.supernetId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeCarnetSouche = this.createFromForm();
    if (commandeCarnetSouche.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeCarnetSoucheService.update(commandeCarnetSouche));
    } else {
      this.subscribeToSaveResponse(this.commandeCarnetSoucheService.create(commandeCarnetSouche));
    }
  }

  private createFromForm(): ICommandeCarnetSouche {
    return {
      ...new CommandeCarnetSouche(),
      id: this.editForm.get(['id'])!.value,
      numeroCommandeCS: this.editForm.get(['numeroCommandeCS'])!.value,
      dateCommandeCS: this.editForm.get(['dateCommandeCS'])!.value
        ? moment(this.editForm.get(['dateCommandeCS'])!.value, DATE_TIME_FORMAT)
        : undefined,
      typePaiement: this.paiement,
      estValide: false,
      prixCommande: this.totalT,
      concessionnaireId: this.editForm.get(['concessionnaireId'])!.value,
      supernetId: this.editForm.get(['supernetId'])!.value,
      infoCommandeCarnetASouches: this.infosList
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeCarnetSouche>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
  trackByIdInfos(index: number, item: SelectableEntity): any {
    return item.id;
  }

  trackById(index: number, item: IOrganisation): any {
    return item.id;
  }

  add() {
    this.typeLibele = this.typecarnets.find(t => t.id === this.editForm.get(['typeCarnetId']).value);

    this.iccas = this.infosList.find(x => x.typeCarnetId === this.editForm.get(['typeCarnetId']).value);
    this.iccasShow = this.listShow.find(x => x.typeCarnetId === this.typeLibele.id);
    if (this.iccas && this.iccasShow) {
      this.iccas.quantiteCommande = this.iccas.quantiteCommande + this.editForm.get(['quantiteCommande']).value;
      this.iccasShow.quantiteCommande = this.iccasShow.quantiteCommande + this.editForm.get(['quantiteCommande']).value;
      this.tt = this.iccasShow.quantiteCommande * this.typeLibele.quantiteCertificat * 25000;
      this.iccasShow.numeroCommande = this.tt.toLocaleString().replace(/,/g, ' ') + ' FCFA';
    } else {
      this.infosList.push(
        new InfoCommandeCarnetASouche(
          null,
          this.editForm.get(['numeroCommande']).value,
          null,
          this.editForm.get(['quantiteCommande']).value,
          false,
          this.editForm.get(['isTransited']).value,
          null,
          this.editForm.get(['typeCarnetId']).value
        )
      );

      if (this.infosList.length > 0) {
        for (const qte of this.infosList) {
          const typ = this.typecarnets.find(t => t.id === qte.typeCarnetId);
          this.total = qte.quantiteCommande * typ.quantiteCertificat * 25000;
        }
      }

      this.listShow.push(
        new InfoCommandeCarnetASouche(
          null,
          this.total.toLocaleString().replace(/,/g, ' ') + ' FCFA',
          null,
          this.editForm.get(['quantiteCommande']).value,
          null,
          null,
          null,
          this.editForm.get(['typeCarnetId']).value,
          this.typeLibele.libelle
        )
      );

      //this.iccas = this.infosList.find(t => t.typeCarnetId === this.editForm.get(['typeCarnetId']).value);
    }
    this.editForm.get(['numeroCommande']).reset();
    this.editForm.get(['quantiteCommande']).reset();
    this.editForm.get(['typeCarnetId']).reset();
  }

  delete(infos: InfoCommandeCarnetASouche) {
    const index = this.infosList.findIndex(x => x === infos);
    this.infosList.splice(index, 1);
    const indexs = this.listShow.findIndex(x => x === infos);
    this.listShow.splice(indexs, 1);
    const typ = this.typecarnets.find(t => t.id === infos.typeCarnetId);
    if (typ.libelle.includes('Carnet de 25 Cycle à Moteur')) {
      if (typ.quantiteCertificat === 25) {
        this.total1 = 0;
      } else if (typ.quantiteCertificat === 50) {
        this.total2 = 0;
      } else if (typ.quantiteCertificat === 100) {
        this.total3 = 0;
      }
    } else {
      if (typ.quantiteCertificat === 25) {
        this.totalv = 0;
      } else if (typ.quantiteCertificat === 50) {
        this.totalv2 = 0;
      } else if (typ.quantiteCertificat === 100) {
        this.totalv3 = 0;
      }
    }
  }

  getPrix() {
    if (this.listShow.length > 0) {
      for (const qte of this.listShow) {
        const typ = this.typecarnets.find(t => t.id === qte.typeCarnetId);
        if (typ.libelle.includes('Carnet de 25 Cycle à Moteur')) {
          if (typ.quantiteCertificat === 25) {
            this.total1 = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          } else if (typ.quantiteCertificat === 50) {
            this.total2 = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          } else if (typ.quantiteCertificat === 100) {
            this.total3 = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          }
        } else {
          if (typ.quantiteCertificat === 25) {
            this.totalv = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          } else if (typ.quantiteCertificat === 50) {
            this.totalv2 = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          } else if (typ.quantiteCertificat === 100) {
            this.totalv3 = qte.quantiteCommande * typ.quantiteCertificat * 25000;
          }
        }
      }
      this.totalT =
        (this.total1 + this.total2 + this.total3 + this.totalv + this.totalv2 + this.totalv3).toLocaleString().replace(/,/g, ' ') + ' FCFA';
    }
  }

  edit(infos: InfoCommandeCarnetASouche) {
    this.editForm.patchValue({
      numeroCommande: infos.numeroCommande,
      quantiteCommande: infos.quantiteCommande,
      typeCarnetId: infos.typeCarnetId
    });
    const index = this.infosList.findIndex(x => x === infos);
    this.infosList.splice(index, 1);
    const indexs = this.listShow.findIndex(x => x === infos);
    this.listShow.splice(indexs, 1);
  }

  toggle($event: boolean) {
    this.checked = $event;
    this.checked2 = false;
    this.paiement = 'ORANGE MONEY';
  }
  toggle2($event: boolean) {
    this.checked2 = $event;
    this.checked = false;
    this.paiement = 'MOBICASH';
  }
}
