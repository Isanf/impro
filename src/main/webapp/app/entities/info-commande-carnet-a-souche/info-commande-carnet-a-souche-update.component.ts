import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInfoCommandeCarnetASouche, InfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { InfoCommandeCarnetASoucheService } from './info-commande-carnet-a-souche.service';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from 'app/entities/type-carnet/type-carnet.service';

type SelectableEntity = ICommandeCarnetSouche | ITypeCarnet;

@Component({
  selector: 'jhi-info-commande-carnet-a-souche-update',
  templateUrl: './info-commande-carnet-a-souche-update.component.html'
})
export class InfoCommandeCarnetASoucheUpdateComponent implements OnInit {
  isSaving = false;
  commandecarnetsouches: ICommandeCarnetSouche[] = [];
  typecarnets: ITypeCarnet[] = [];

  editForm = this.fb.group({
    id: [],
    numeroCommande: [],
    dateCommande: [],
    quantiteCommande: [],
    commandeCarnetSoucheId: [],
    typeCarnetId: []
  });

  constructor(
    protected infoCommandeCarnetASoucheService: InfoCommandeCarnetASoucheService,
    protected commandeCarnetSoucheService: CommandeCarnetSoucheService,
    protected typeCarnetService: TypeCarnetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infoCommandeCarnetASouche }) => {
      if (!infoCommandeCarnetASouche.id) {
        const today = moment().startOf('day');
        infoCommandeCarnetASouche.dateCommande = today;
      }

      this.updateForm(infoCommandeCarnetASouche);

      this.commandeCarnetSoucheService
        .query()
        .subscribe((res: HttpResponse<ICommandeCarnetSouche[]>) => (this.commandecarnetsouches = res.body || []));

      this.typeCarnetService.query().subscribe((res: HttpResponse<ITypeCarnet[]>) => (this.typecarnets = res.body || []));
    });
  }

  updateForm(infoCommandeCarnetASouche: IInfoCommandeCarnetASouche): void {
    this.editForm.patchValue({
      id: infoCommandeCarnetASouche.id,
      numeroCommande: infoCommandeCarnetASouche.numeroCommande,
      dateCommande: infoCommandeCarnetASouche.dateCommande ? infoCommandeCarnetASouche.dateCommande.format(DATE_TIME_FORMAT) : null,
      quantiteCommande: infoCommandeCarnetASouche.quantiteCommande,
      commandeCarnetSoucheId: infoCommandeCarnetASouche.commandeCarnetSoucheId,
      typeCarnetId: infoCommandeCarnetASouche.typeCarnetId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const infoCommandeCarnetASouche = this.createFromForm();
    if (infoCommandeCarnetASouche.id !== undefined) {
      this.subscribeToSaveResponse(this.infoCommandeCarnetASoucheService.update(infoCommandeCarnetASouche));
    } else {
      this.subscribeToSaveResponse(this.infoCommandeCarnetASoucheService.create(infoCommandeCarnetASouche));
    }
  }

  private createFromForm(): IInfoCommandeCarnetASouche {
    return {
      ...new InfoCommandeCarnetASouche(),
      id: this.editForm.get(['id'])!.value,
      numeroCommande: this.editForm.get(['numeroCommande'])!.value,
      dateCommande: this.editForm.get(['dateCommande'])!.value
        ? moment(this.editForm.get(['dateCommande'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantiteCommande: this.editForm.get(['quantiteCommande'])!.value,
      commandeCarnetSoucheId: this.editForm.get(['commandeCarnetSoucheId'])!.value,
      typeCarnetId: this.editForm.get(['typeCarnetId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfoCommandeCarnetASouche>>): void {
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
