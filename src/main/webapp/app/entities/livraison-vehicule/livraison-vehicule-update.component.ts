import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILivraisonVehicule, LivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { LivraisonVehiculeService } from './livraison-vehicule.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';
import { CommandeVehicule, ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { CommandeVehiculeService } from 'app/entities/commande-vehicule/commande-vehicule.service';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { Logger } from 'angular2-logger/core';
import { debuglog } from 'util';
import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { InfoCommandeVehiculeService } from 'app/entities/info-commande-vehicule/info-commande-vehicule.service';

type SelectableEntity = IOrganisation | ICommandeVehicule;

@Component({
  selector: 'jhi-livraison-vehicule-update',
  templateUrl: './livraison-vehicule-update.component.html'
})
export class LivraisonVehiculeUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  organi: IOrganisation[] = [];
  commandevehicules: ICommandeVehicule[] = [];
  commandev: ICommandeVehicule;
  infoCommandeVehicules: IInfoCommandeVehicule[] = [];
  vehicules: IVehicule[];
  vehicule: IVehicule[] = [];
  infocommandev: IInfoCommandeVehicule[] = [];

  editForm = this.fb.group({
    id: [],
    numeroLivraison: [],
    dateLivraison: [],
    revendeurId: [],
    concessionnaireId: [],
    commandeVehiculeId: [],
    vehiculeslist: [],
    infoCommandeVehicule: []
  });
  /*private vehicule: any;*/
  private selectCom: any;

  @ViewChild('revId', { static: true }) revId;

  constructor(
    protected livraisonVehiculeService: LivraisonVehiculeService,
    protected organisationService: OrganisationService,
    protected vehiculeService: VehiculeService,
    protected commandeVehiculeService: CommandeVehiculeService,
    protected infoCommandeVehiculeService: InfoCommandeVehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonVehicule }) => {
      if (!livraisonVehicule.id) {
        const today = moment().startOf('day');
        livraisonVehicule.dateLivraison = today;
      }

      this.updateForm(livraisonVehicule);

      this.organisationService.queryRevendeurs().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.commandeVehiculeService
        .queryNotDelivery()
        .subscribe((res: HttpResponse<ICommandeVehicule[]>) => (this.commandevehicules = res.body || []));

      this.infoCommandeVehiculeService
        .query()
        .subscribe((res: HttpResponse<IInfoCommandeVehicule[]>) => (this.infoCommandeVehicules = res.body || []));
    });

    this.vehiculeService.queryNDelivNSell().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules = res.body || []));
  }

  updateForm(livraisonVehicule: ILivraisonVehicule): void {
    this.editForm.patchValue({
      id: livraisonVehicule.id,
      numeroLivraison: livraisonVehicule.numeroLivraison,
      dateLivraison: livraisonVehicule.dateLivraison ? livraisonVehicule.dateLivraison.format(DATE_TIME_FORMAT) : null,
      revendeurId: livraisonVehicule.revendeurId,
      concessionnaireId: livraisonVehicule.concessionnaireId,
      commandeVehiculeId: livraisonVehicule.commandeVehiculeId,
      vehicules: livraisonVehicule.vehiculeDTOS
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livraisonVehicule = this.createFromForm();
    if (livraisonVehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonVehiculeService.update(livraisonVehicule));
    } else {
      this.subscribeToSaveResponse(this.livraisonVehiculeService.create(livraisonVehicule));
    }
  }

  private createFromForm(): ILivraisonVehicule {
    return {
      ...new LivraisonVehicule(),
      id: this.editForm.get(['id'])!.value,
      numeroLivraison: this.editForm.get(['numeroLivraison'])!.value,
      dateLivraison: this.editForm.get(['dateLivraison'])!.value
        ? moment(this.editForm.get(['dateLivraison'])!.value, DATE_TIME_FORMAT)
        : undefined,
      revendeurId: this.revId.nativeElement.value,
      concessionnaireId: this.editForm.get(['concessionnaireId'])!.value,
      commandeVehiculeId: this.editForm.get(['commandeVehiculeId'])!.value,
      vehiculeDTOS: this.editForm.get(['vehiculeslist']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraisonVehicule>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getv() {
    for (const v of this.vehicules) {
      if (v.marqueVehiculeId === this.editForm.get(['infoCommandeVehicule']).value) {
        this.vehicule.push(v);
      }
    }
    /*for (const com of this.organisations) {
      if (com.commandeVRevendeurs.find()) {
        this.organi.push(com);
      }
    }*/
    /*this.editForm.get(['infoCommandeVehicule']).reset();*/
  }

  getc() {
    this.commandev = this.commandevehicules.find(x => x.id === this.editForm.get(['commandeVehiculeId']).value);
    this.revId.nativeElement.value = this.commandev.revendeurId;
    for (const infos of this.infoCommandeVehicules) {
      if (infos.commandeVehiculeId === this.editForm.get(['commandeVehiculeId']).value) {
        this.infocommandev.push(infos);
      }
    }
  }
}
