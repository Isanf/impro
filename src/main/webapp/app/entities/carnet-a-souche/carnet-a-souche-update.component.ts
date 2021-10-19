import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICarnetASouche, CarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { CarnetASoucheService } from './carnet-a-souche.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';
import { ITypeCarnet } from 'app/shared/model/type-carnet.model';
import { TypeCarnetService } from 'app/entities/type-carnet/type-carnet.service';

type SelectableEntity = IOrganisation | ILivraisonCarnetSouche | ITypeCarnet;

@Component({
  selector: 'jhi-carnet-a-souche-update',
  templateUrl: './carnet-a-souche-update.component.html'
})
export class CarnetASoucheUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  livraisoncarnetsouches: ILivraisonCarnetSouche[] = [];
  typecarnets: ITypeCarnet[] = [];
  loading = false;

  editForm = this.fb.group({
    id: [],
    numero: [],
    dateImpression: [],
    concessionnaireId: [],
    livraisonCarnetSoucheId: [],
    typeCarnetId: []
  });

  constructor(
    protected carnetASoucheService: CarnetASoucheService,
    protected organisationService: OrganisationService,
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected typeCarnetService: TypeCarnetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carnetASouche }) => {
      if (!carnetASouche.id) {
        const today = moment().startOf('day');
        carnetASouche.dateImpression = today;
      }

      this.updateForm(carnetASouche);

      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.livraisonCarnetSoucheService
        .query()
        .subscribe((res: HttpResponse<ILivraisonCarnetSouche[]>) => (this.livraisoncarnetsouches = res.body || []));

      this.typeCarnetService.query().subscribe((res: HttpResponse<ITypeCarnet[]>) => (this.typecarnets = res.body || []));
    });
  }

  updateForm(carnetASouche: ICarnetASouche): void {
    this.editForm.patchValue({
      id: carnetASouche.id,
      numero: carnetASouche.numero,
      dateImpression: carnetASouche.dateImpression ? carnetASouche.dateImpression.format(DATE_TIME_FORMAT) : null,
      concessionnaireId: carnetASouche.concessionnaireId,
      livraisonCarnetSoucheId: carnetASouche.livraisonCarnetSoucheId,
      typeCarnetId: carnetASouche.typeCarnetId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.loading = true;
    const carnetASouche = this.createFromForm();
    if (carnetASouche.id !== undefined) {
      this.subscribeToSaveResponse(this.carnetASoucheService.update(carnetASouche));
    } else {
      this.subscribeToSaveResponse(this.carnetASoucheService.create(carnetASouche));
    }
  }

  private createFromForm(): ICarnetASouche {
    return {
      ...new CarnetASouche(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      dateImpression: this.editForm.get(['dateImpression'])!.value
        ? moment(this.editForm.get(['dateImpression'])!.value, DATE_TIME_FORMAT)
        : undefined,
      concessionnaireId: this.editForm.get(['concessionnaireId'])!.value,
      livraisonCarnetSoucheId: this.editForm.get(['livraisonCarnetSoucheId'])!.value,
      typeCarnetId: this.editForm.get(['typeCarnetId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarnetASouche>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.loading = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
    this.loading = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
