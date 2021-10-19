import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommandeVehicule, CommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { CommandeVehiculeService } from './commande-vehicule.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IMarqueVehicule, MarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';
import { InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

@Component({
  selector: 'jhi-commande-vehicule-update',
  templateUrl: './commande-vehicule-update.component.html'
})
export class CommandeVehiculeUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  marquevehicules: IMarqueVehicule[] = [];
  infocomsList: InfoCommandeVehicule[] = [];
  listShow: InfoCommandeVehicule[] = [];
  marqueVehicule: MarqueVehicule;

  editForm = this.fb.group({
    id: [],
    numeroCommandeVehicule: [],
    dateCommande: [],
    revendeurId: [],
    concessionnaireId: [],
    numeroCommande: [],
    quantiteCommande: [],
    marqueVehiculeId: []
  });
  iccas: InfoCommandeVehicule;
  iccasShow: InfoCommandeVehicule;

  constructor(
    protected commandeVehiculeService: CommandeVehiculeService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected marqueVehiculeService: MarqueVehiculeService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandeVehicule }) => {
      if (!commandeVehicule.id) {
        const today = moment().startOf('day');
        commandeVehicule.dateCommande = today;
      }

      this.updateForm(commandeVehicule);

      this.organisationService
        .queryConcessionaire()
        .subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body || []));
    });
  }

  updateForm(commandeVehicule: ICommandeVehicule): void {
    this.editForm.patchValue({
      id: commandeVehicule.id,
      numeroCommandeVehicule: commandeVehicule.numeroCommandeVehicule,
      dateCommande: commandeVehicule.dateCommande ? commandeVehicule.dateCommande.format(DATE_TIME_FORMAT) : null,
      revendeurId: commandeVehicule.revendeurId,
      concessionnaireId: commandeVehicule.concessionnaireId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandeVehicule = this.createFromForm();
    if (commandeVehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.commandeVehiculeService.update(commandeVehicule));
    } else {
      this.subscribeToSaveResponse(this.commandeVehiculeService.create(commandeVehicule));
    }
  }

  private createFromForm(): ICommandeVehicule {
    return {
      ...new CommandeVehicule(),
      id: this.editForm.get(['id'])!.value,
      numeroCommandeVehicule: this.editForm.get(['numeroCommandeVehicule'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      revendeurId: this.editForm.get(['revendeurId'])!.value,
      concessionnaireId: this.editForm.get(['concessionnaireId'])!.value,
      infoCommandeVehiculeDTO: this.infocomsList
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandeVehicule>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IOrganisation): any {
    return item.id;
  }

  add() {
    this.marqueVehicule = this.marquevehicules.find(t => t.id === this.editForm.get(['marqueVehiculeId']).value);
    this.iccas = this.infocomsList.find(x => x.marqueVehiculeId === this.editForm.get(['marqueVehiculeId']).value);
    this.iccasShow = this.listShow.find(x => x.marqueVehiculeId === this.marqueVehicule.libelle);
    if (this.iccas && this.iccasShow) {
      this.iccas.quantiteCommande = this.iccas.quantiteCommande + this.editForm.get(['quantiteCommande']).value;
      this.iccasShow.quantiteCommande = this.iccasShow.quantiteCommande + this.editForm.get(['quantiteCommande']).value;
    } else {
      this.infocomsList.push(
        new InfoCommandeVehicule(
          null,
          this.editForm.get(['numeroCommande']).value,
          null,
          this.editForm.get(['quantiteCommande']).value,
          null,
          this.editForm.get(['marqueVehiculeId']).value
        )
      );

      this.listShow.push(
        new InfoCommandeVehicule(
          null,
          this.editForm.get(['numeroCommande']).value,
          null,
          this.editForm.get(['quantiteCommande']).value,
          null,
          this.marqueVehicule.libelle
        )
      );
    }

    this.editForm.get(['numeroCommande']).reset();
    this.editForm.get(['quantiteCommande']).reset();
    this.editForm.get(['marqueVehiculeId']).reset();
  }

  delete(infos: InfoCommandeVehicule) {
    const index = this.infocomsList.findIndex(x => x === infos);
    this.infocomsList.splice(index, 1);
    const indexs = this.listShow.findIndex(x => x === infos);
    this.listShow.splice(indexs, 1);
  }

  edit(infos: InfoCommandeVehicule) {
    this.editForm.patchValue({
      numeroCommande: infos.numeroCommande,
      quantiteCommande: infos.quantiteCommande,
      marqueVehiculeId: infos.marqueVehiculeId
    });
    const index = this.infocomsList.findIndex(x => x === infos);
    this.infocomsList.splice(index, 1);
    const indexs = this.listShow.findIndex(x => x === infos);
    this.listShow.splice(indexs, 1);
  }
}
