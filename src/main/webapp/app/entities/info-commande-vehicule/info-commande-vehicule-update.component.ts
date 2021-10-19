import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInfoCommandeVehicule, InfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';
import { InfoCommandeVehiculeService } from './info-commande-vehicule.service';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { CommandeVehiculeService } from 'app/entities/commande-vehicule/commande-vehicule.service';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';

type SelectableEntity = ICommandeVehicule | IMarqueVehicule;

@Component({
  selector: 'jhi-info-commande-vehicule-update',
  templateUrl: './info-commande-vehicule-update.component.html'
})
export class InfoCommandeVehiculeUpdateComponent implements OnInit {
  isSaving = false;
  commandevehicules: ICommandeVehicule[] = [];
  marquevehicules: IMarqueVehicule[] = [];

  editForm = this.fb.group({
    id: [],
    numeroCommande: [],
    dateCommande: [],
    quantiteCommande: [],
    commandeVehiculeId: [],
    marqueVehiculeId: []
  });

  constructor(
    protected infoCommandeVehiculeService: InfoCommandeVehiculeService,
    protected commandeVehiculeService: CommandeVehiculeService,
    protected marqueVehiculeService: MarqueVehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoCommandeVehicule }) => {
      if (!infoCommandeVehicule.id) {
        const today = moment().startOf('day');
        infoCommandeVehicule.dateCommande = today;
      }

      this.updateForm(infoCommandeVehicule);

      this.commandeVehiculeService.query().subscribe((res: HttpResponse<ICommandeVehicule[]>) => (this.commandevehicules = res.body || []));

      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body || []));
    });
  }

  updateForm(infoCommandeVehicule: IInfoCommandeVehicule): void {
    this.editForm.patchValue({
      id: infoCommandeVehicule.id,
      numeroCommande: infoCommandeVehicule.numeroCommande,
      dateCommande: infoCommandeVehicule.dateCommande ? infoCommandeVehicule.dateCommande.format(DATE_TIME_FORMAT) : null,
      quantiteCommande: infoCommandeVehicule.quantiteCommande,
      commandeVehiculeId: infoCommandeVehicule.commandeVehiculeId,
      marqueVehiculeId: infoCommandeVehicule.marqueVehiculeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const infoCommandeVehicule = this.createFromForm();
    if (infoCommandeVehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.infoCommandeVehiculeService.update(infoCommandeVehicule));
    } else {
      this.subscribeToSaveResponse(this.infoCommandeVehiculeService.create(infoCommandeVehicule));
    }
  }

  private createFromForm(): IInfoCommandeVehicule {
    return {
      ...new InfoCommandeVehicule(),
      id: this.editForm.get(['id'])!.value,
      numeroCommande: this.editForm.get(['numeroCommande'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantiteCommande: this.editForm.get(['quantiteCommande'])!.value,
      commandeVehiculeId: this.editForm.get(['commandeVehiculeId'])!.value,
      marqueVehiculeId: this.editForm.get(['marqueVehiculeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoCommandeVehicule>>): void {
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
}
