import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from './vehicule.service';
import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';
import { LivraisonVehiculeService } from 'app/entities/livraison-vehicule/livraison-vehicule.service';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';
import { Stock } from 'app/shared/model/stock.model';

@Component({
  selector: 'jhi-vehicule-update',
  templateUrl: './vehicule-update.component.html'
})
export class VehiculeUpdateComponent implements OnInit {
  isSaving: boolean;

  livraisonvehicules: ILivraisonVehicule[];

  typevehicules: ITypeVehicule[];

  marquevehicules: IMarqueVehicule[];
  dateMiseCirculationDp: any;
  dateDedouanementDp: any;

  editForm = this.fb.group({
    id: [],
    numeroChassis: [],
    types: [],
    model: [],
    energie: [],
    puissanceReel: [],
    puissanceAdmin: [],
    couleur: [],
    poidsVide: [],
    chargeUtile: [],
    ptac: [],
    ptra: [],
    nbrPlace: [],
    capacite: [],
    dateMiseCirculation: [],
    regime: [],
    noDedouanement: [],
    dateDedouanement: [],
    livraisonVehiculeId: [],
    typeVehiculeId: [],
    marqueVehiculeId: [],
    fileName: []
  });

  /*public String
  private nom: AbstractControl;

  uploadFile(){
    String; this.nom=this.editForm.get(['fileName']);
    let nom;
    return nom;
  }
*/
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vehiculeService: VehiculeService,
    protected livraisonVehiculeService: LivraisonVehiculeService,
    protected typeVehiculeService: TypeVehiculeService,
    protected marqueVehiculeService: MarqueVehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vehicule }) => {
      this.updateForm(vehicule);
    });
    this.livraisonVehiculeService
      .query()
      .subscribe(
        (res: HttpResponse<ILivraisonVehicule[]>) => (this.livraisonvehicules = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.typeVehiculeService
      .query()
      .subscribe(
        (res: HttpResponse<ITypeVehicule[]>) => (this.typevehicules = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.marqueVehiculeService
      .query()
      .subscribe(
        (res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(vehicule: IVehicule) {
    this.editForm.patchValue({
      id: vehicule.id,
      numeroChassis: vehicule.numeroChassis,
      types: vehicule.types,
      model: vehicule.model,
      energie: vehicule.energie,
      puissanceReel: vehicule.puissanceReel,
      puissanceAdmin: vehicule.puissanceAdmin,
      couleur: vehicule.couleur,
      poidsVide: vehicule.poidsVide,
      chargeUtile: vehicule.chargeUtile,
      ptac: vehicule.ptac,
      ptra: vehicule.ptra,
      nbrPlace: vehicule.nbrPlace,
      capacite: vehicule.capacite,
      dateMiseCirculation: vehicule.dateMiseCirculation,
      regime: vehicule.regime,
      noDedouanement: vehicule.noDedouanement,
      dateDedouanement: vehicule.dateDedouanement,
      livraisonVehiculeId: vehicule.livraisonVehiculeId,
      typeVehiculeId: vehicule.typeVehiculeId,
      marqueVehiculeId: vehicule.marqueVehiculeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vehicule = this.createFromForm();
    if (vehicule.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculeService.update(vehicule));
    } else {
      this.subscribeToSaveResponse(this.vehiculeService.create(vehicule));
      if (this.vehiculeService.create(vehicule) == null) {
        alert('Le vehicule existe déjà');
      }
    }
  }

  private createFromForm(): IVehicule {
    return {
      ...new Vehicule(),
      id: this.editForm.get(['id']).value,
      numeroChassis: this.editForm.get(['numeroChassis']).value,
      types: this.editForm.get(['types']).value,
      model: this.editForm.get(['model']).value,
      energie: this.editForm.get(['energie']).value,
      puissanceReel: this.editForm.get(['puissanceReel']).value,
      puissanceAdmin: this.editForm.get(['puissanceAdmin']).value,
      couleur: this.editForm.get(['couleur']).value,
      poidsVide: this.editForm.get(['poidsVide']).value,
      chargeUtile: this.editForm.get(['chargeUtile']).value,
      ptac: this.editForm.get(['ptac']).value,
      ptra: this.editForm.get(['ptra']).value,
      nbrPlace: this.editForm.get(['nbrPlace']).value,
      capacite: this.editForm.get(['capacite']).value,
      dateMiseCirculation: this.editForm.get(['dateMiseCirculation']).value,
      regime: this.editForm.get(['regime']).value,
      noDedouanement: this.editForm.get(['noDedouanement']).value,
      dateDedouanement: this.editForm.get(['dateDedouanement']).value,
      livraisonVehiculeId: this.editForm.get(['livraisonVehiculeId']).value,
      typeVehiculeId: this.editForm.get(['typeVehiculeId']).value,
      marqueVehiculeId: this.editForm.get(['marqueVehiculeId']).value,
      stockDTO: new Stock(null, null, null, null, null, null, null)
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicule>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackLivraisonVehiculeById(index: number, item: ILivraisonVehicule) {
    return item.id;
  }

  trackTypeVehiculeById(index: number, item: ITypeVehicule) {
    return item.id;
  }

  trackMarqueVehiculeById(index: number, item: IMarqueVehicule) {
    return item.id;
  }
}
