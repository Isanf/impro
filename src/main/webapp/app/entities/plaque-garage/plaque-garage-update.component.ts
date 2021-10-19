import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlaqueGarage, PlaqueGarage } from 'app/shared/model/plaque-garage.model';
import { PlaqueGarageService } from './plaque-garage.service';
import { ICarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-plaque-garage-update',
  templateUrl: './plaque-garage-update.component.html'
})
export class PlaqueGarageUpdateComponent implements OnInit {
  isSaving = false;
  cartews: ICarteW[] = [];
  plaqueGarages: IPlaqueGarage[] = [];
  plaqueGarage: IPlaqueGarage;
  cartew: ICarteW;
  organisation: IOrganisation;

  editForm = this.fb.group({
    id: [],
    numeroOrdre: [],
    numeroPlaque: [],
    codeQrPlaque: [],
    carteWId: []
  });

  constructor(
    protected plaqueGarageService: PlaqueGarageService,
    protected carteWService: CarteWService,
    private organisationServie: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plaqueGarage }) => {
      this.updateForm(plaqueGarage);
      this.cartew = plaqueGarage;

      this.organisationServie.find(this.cartew.organisationId).subscribe((rep: HttpResponse<IOrganisation>) => {
        this.organisation = rep.body;
      });

      this.carteWService.query().subscribe((res: HttpResponse<ICarteW[]>) => (this.cartews = res.body || []));
    });
  }

  updateForm(plaqueGarage: IPlaqueGarage): void {
    this.editForm.patchValue({
      id: plaqueGarage.id,
      numeroOrdre: plaqueGarage.numeroOrdre,
      numeroPlaque: plaqueGarage.numeroPlaque,
      codeQrPlaque: plaqueGarage.codeQrPlaque,
      carteWId: plaqueGarage.carteWId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plaqueGarage = this.createFromForm();

    this.subscribeToSaveResponse(this.plaqueGarageService.create(plaqueGarage));

    /*if (plaqueGarage.id !== undefined) {
      this.subscribeToSaveResponse(this.plaqueGarageService.update(plaqueGarage));
    } else {
      this.subscribeToSaveResponse(this.plaqueGarageService.create(plaqueGarage));
    }*/
  }

  private createFromForm(): IPlaqueGarage[] {
    return this.plaqueGarages;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaqueGarage>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICarteW): any {
    return item.id;
  }

  add() {
    this.plaqueGarages.push(
      new PlaqueGarage(null, null, this.cartew.numeroCarteW, this.editForm.get(['codeQrPlaque']).value, null, this.cartew.id)
    );

    this.editForm.get(['codeQrPlaque']).reset();
  }
}
