import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVehiculeOccasion, VehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';
import { VehiculeOccasionService } from './vehicule-occasion.service';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';
import { VehiculeTraversantService } from 'app/entities/vehicule-traversant/vehicule-traversant.service';
import { IVehiculeTraversant, VehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { DocIdentificationPP, IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPM, IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { INation } from 'app/shared/model/nation.model';
import { JhiAlertService } from 'ng-jhipster';
import { NationService } from 'app/entities/nation/nation.service';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { DocIdentificationPMService } from 'app/entities/doc-identification-pm/doc-identification-pm.service';
import { PersonneMorale } from 'app/shared/model/personne-morale.model';
import { VehiculeOccasionalService } from 'app/entities/vehicule-occasional/vehicule-occasional.service';
import { IVehiculeOccasional, VehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';
import { ICarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

@Component({
  selector: 'jhi-vehicule-occasion-update',
  templateUrl: './vehicule-occasion-update.component.html'
})
export class VehiculeOccasionUpdateComponent implements OnInit {
  isSaving = false;
  isSavingTrav = false;
  marquevehicules: IMarqueVehicule[] = [];
  checked = false;
  checked2 = false;
  exist: boolean = null;
  exist1: boolean = null;
  docexist: HttpResponse<IDocIdentificationPP>;
  docexist1: HttpResponse<IDocIdentificationPM>;
  listnation: INation[];
  listcartew: ICarteW[];
  carteW: ICarteW;
  orgsRevendWith: IOrganisation[];

  editForm = this.fb.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    nomPrenom: [],
    telephone: [],
    carteW: [],
    createdAt: [],
    numeroCNIB: [],
    nation: []
  });

  editFormTrav = this.fbTrav.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    dateEntre: [],
    dateSortie: [],
    provenance: [],
    destination: [],
    createdAt: [],
    nom: [],
    prenom: [],
    dateNaissance: [],
    lieuNaissance: [],
    telephone: [],
    residence: [],
    numeroDoc: [],
    dateEtablissement: [],
    lieuEtablissement: [],
    autoriteEmettrice: [],
    typeDocIdentification: [],
    vehiculeStock: [],
    marqueVehiculeId: [],
    nip: [],
    nation: []
  });
  editFormTrav1 = this.fbTrav.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    dateEntre: [],
    dateSortie: [],
    provenance: [],
    destination: [],
    createdAt: [],
    denomination: [],
    dateCreate: [],
    numeroDoc1: [],
    numeroIFU: [],
    telephone: [],
    numeroRCCM: [],
    nation: []
  });

  editFormOcca = this.fbTrav.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    createdAt: [],
    nom: [],
    prenom: [],
    dateNaissance: [],
    lieuNaissance: [],
    telephone: [],
    residence: [],
    numeroDoc: [],
    dateEtablissement: [],
    lieuEtablissement: [],
    autoriteEmettrice: [],
    typeDocIdentification: [],
    vehiculeStock: [],
    marqueVehiculeId: [],
    nip: [],
    nation: [],
    carteW: []
  });
  editFormOcca1 = this.fbTrav.group({
    id: [],
    chassis: [],
    marque: [],
    model: [],
    createdAt: [],
    denomination: [],
    dateCreate: [],
    numeroDoc1: [],
    numeroIFU: [],
    telephone: [],
    numeroRCCM: [],
    nation: [],
    carteW: []
  });

  constructor(
    protected vehiculeOccasionService: VehiculeOccasionService,
    protected activatedRoute: ActivatedRoute,
    protected marqueVehiculeService: MarqueVehiculeService,
    private fb: FormBuilder,
    private fbTrav: FormBuilder,
    protected vehiculeTraversantService: VehiculeTraversantService,
    private docIdentificationPPService: DocIdentificationPPService,
    protected jhiAlertService: JhiAlertService,
    private docIdentificationPMService: DocIdentificationPMService,
    protected nationService: NationService,
    protected vehiculeOccasionalService: VehiculeOccasionalService,
    protected carteWService: CarteWService,
    protected organisationService: OrganisationService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehiculeOccasion }) => {
      if (!vehiculeOccasion.id) {
        const today = moment().startOf('day');
        vehiculeOccasion.createdAt = today;
      }

      this.updateForm(vehiculeOccasion);
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body || []));
    });

    this.activatedRoute.data.subscribe(({ vehiculeTraversant }) => {
      if (!vehiculeTraversant.id) {
        const today = moment().startOf('day');
        vehiculeTraversant.dateEntre = today;
        vehiculeTraversant.dateSortie = today;
        vehiculeTraversant.createdAt = today;
      }

      this.updateFormTrav(vehiculeTraversant);
    });

    this.nationService
      .querylist()
      .subscribe((res: HttpResponse<INation[]>) => (this.listnation = res.body), (res: HttpErrorResponse) => this.onError(res.message));

    this.carteWService
      .query()
      .subscribe((res: HttpResponse<ICarteW[]>) => (this.listcartew = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vehiculeOccasion: IVehiculeOccasion): void {
    this.editForm.patchValue({
      id: vehiculeOccasion.id,
      chassis: vehiculeOccasion.chassis,
      marque: vehiculeOccasion.marque,
      model: vehiculeOccasion.model,
      nomPrenom: vehiculeOccasion.nomPrenom,
      telephone: vehiculeOccasion.telephone,
      carteW: vehiculeOccasion.carteW,
      createdAt: vehiculeOccasion.createdAt ? vehiculeOccasion.createdAt.format(DATE_TIME_FORMAT) : null,
      numeroCNIB: vehiculeOccasion.numeroCNIB
    });
  }

  updateFormTrav(vehiculeTraversant: IVehiculeTraversant): void {
    this.editFormTrav.patchValue({
      id: vehiculeTraversant.id,
      chassis: vehiculeTraversant.chassis,
      marque: vehiculeTraversant.marque,
      model: vehiculeTraversant.model,
      dateEntre: vehiculeTraversant.dateEntre ? vehiculeTraversant.dateEntre.format(DATE_TIME_FORMAT) : null,
      dateSortie: vehiculeTraversant.dateSortie ? vehiculeTraversant.dateSortie.format(DATE_TIME_FORMAT) : null,
      provenance: vehiculeTraversant.provenance,
      destination: vehiculeTraversant.destination,
      createdAt: vehiculeTraversant.createdAt ? vehiculeTraversant.createdAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehiculeOccasion = this.createFromForm();
    if (vehiculeOccasion.id !== undefined) {
      this.subscribeToSaveResponse(this.vehiculeOccasionService.update(vehiculeOccasion));
    } else {
      this.subscribeToSaveResponse(this.vehiculeOccasionService.create(vehiculeOccasion));
    }
  }

  private createFromForm(): IVehiculeOccasion {
    return {
      ...new VehiculeOccasion(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editForm.get(['chassis'])!.value,
      marque: this.editForm.get(['marque'])!.value,
      model: this.editForm.get(['model'])!.value,
      nomPrenom: this.editForm.get(['nomPrenom'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      carteW: this.editForm.get(['carteW'])!.value,
      createdAt: null,
      numeroCNIB: this.editForm.get(['numeroCNIB'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehiculeOccasion>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  protected onSaveSuccessTrav(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveErrorTrav(): void {
    this.isSaving = false;
  }

  saveTrav(): void {
    this.isSavingTrav = true;
    const vehiculeTraversant = this.createFromFormTrav();
    if (vehiculeTraversant.id !== undefined) {
      this.subscribeToSaveResponseTrav(this.vehiculeTraversantService.update(vehiculeTraversant));
    } else {
      this.subscribeToSaveResponseTrav(this.vehiculeTraversantService.create(vehiculeTraversant));
    }
  }

  saveTrav1(): void {
    this.isSavingTrav = true;
    const vehiculeTraversant = this.createFromFormTrav1();
    if (vehiculeTraversant.id !== undefined) {
      this.subscribeToSaveResponseTrav(this.vehiculeTraversantService.update(vehiculeTraversant));
    } else {
      this.subscribeToSaveResponseTrav(this.vehiculeTraversantService.create(vehiculeTraversant));
    }
  }

  saveOcca(): void {
    this.isSavingTrav = true;
    const vehiculeOcca = this.createFromFormOcca();
    if (vehiculeOcca.id !== undefined) {
      this.subscribeToSaveResponseOcca(this.vehiculeOccasionalService.update(vehiculeOcca));
    } else {
      this.subscribeToSaveResponseOcca(this.vehiculeOccasionalService.create(vehiculeOcca));
    }
  }

  saveOcca1(): void {
    this.isSavingTrav = true;
    const vehiculeOcca1 = this.createFromFormOcca1();
    if (vehiculeOcca1.id !== undefined) {
      this.subscribeToSaveResponseOcca(this.vehiculeOccasionalService.update(vehiculeOcca1));
    } else {
      this.subscribeToSaveResponseOcca(this.vehiculeOccasionalService.create(vehiculeOcca1));
    }
  }

  private createFromFormTrav(): IVehiculeTraversant {
    return {
      ...new VehiculeTraversant(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editFormTrav.get(['chassis'])!.value,
      marque: this.editFormTrav.get(['marque'])!.value,
      model: this.editFormTrav.get(['model'])!.value,
      dateEntre: this.editFormTrav.get(['dateEntre'])!.value
        ? moment(this.editFormTrav.get(['dateEntre'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateSortie: this.editFormTrav.get(['dateSortie'])!.value
        ? moment(this.editFormTrav.get(['dateSortie'])!.value, DATE_TIME_FORMAT)
        : undefined,
      provenance: this.editFormTrav.get(['provenance'])!.value,
      destination: this.editFormTrav.get(['destination'])!.value,
      createdAt: this.editFormTrav.get(['createdAt'])!.value
        ? moment(this.editFormTrav.get(['createdAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      personnePhysiqueDTO: new PersonnePhysique(
        null,
        this.editFormTrav.get(['nom']).value,
        this.editFormTrav.get(['prenom']).value,
        this.editFormTrav.get(['dateNaissance']).value,
        this.editFormTrav.get(['lieuNaissance']).value,
        this.editFormTrav.get(['residence']).value,
        this.editFormTrav.get(['telephone']).value
      ),
      docIdentificationPPDTO: new DocIdentificationPP(
        null,
        this.editFormTrav.get(['numeroDoc']).value,
        this.editFormTrav.get(['nip']).value,
        this.editFormTrav.get(['dateEtablissement']).value,
        this.editFormTrav.get(['lieuEtablissement']).value,
        this.editFormTrav.get(['autoriteEmettrice']).value,
        this.editFormTrav.get(['typeDocIdentification']).value
      ),
      nationDTO: this.editFormTrav.get(['nation']).value
    };
  }
  private createFromFormTrav1(): IVehiculeTraversant {
    return {
      ...new VehiculeTraversant(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editFormTrav1.get(['chassis'])!.value,
      marque: this.editFormTrav1.get(['marque'])!.value,
      model: this.editFormTrav1.get(['model'])!.value,
      dateEntre: this.editFormTrav1.get(['dateEntre'])!.value
        ? moment(this.editFormTrav1.get(['dateEntre'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateSortie: this.editFormTrav1.get(['dateSortie'])!.value
        ? moment(this.editFormTrav1.get(['dateSortie'])!.value, DATE_TIME_FORMAT)
        : undefined,
      provenance: this.editFormTrav1.get(['provenance'])!.value,
      destination: this.editFormTrav1.get(['destination'])!.value,
      createdAt: this.editFormTrav1.get(['createdAt'])!.value
        ? moment(this.editFormTrav1.get(['createdAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      personneMoraleDTO: new PersonneMorale(
        null,
        this.editFormTrav1.get(['numeroIFU']).value,
        this.editFormTrav1.get(['denomination']).value,
        this.editFormTrav1.get(['dateCreate']).value,
        this.editFormTrav1.get(['telephone']).value
      ),
      docIdentificationPMDTO: new DocIdentificationPM(
        null,
        this.editFormTrav1.get(['numeroDoc1']).value,
        this.editFormTrav1.get(['numeroIFU']).value,
        this.editFormTrav1.get(['numeroRCCM']).value
      ),
      nationDTO: this.editFormTrav1.get(['nation']).value
    };
  }

  private createFromFormOcca(): IVehiculeOccasional {
    return {
      ...new VehiculeOccasional(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editFormOcca.get(['chassis'])!.value,
      marque: this.editFormOcca.get(['marque'])!.value,
      model: this.editFormOcca.get(['model'])!.value,
      createdAt: this.editFormOcca.get(['createdAt'])!.value
        ? moment(this.editFormOcca.get(['createdAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      personnePhysiqueDTO: new PersonnePhysique(
        null,
        this.editFormOcca.get(['nom']).value,
        this.editFormOcca.get(['prenom']).value,
        this.editFormOcca.get(['dateNaissance']).value,
        this.editFormOcca.get(['lieuNaissance']).value,
        this.editFormOcca.get(['residence']).value,
        this.editFormOcca.get(['telephone']).value
      ),
      docIdentificationPPDTO: new DocIdentificationPP(
        null,
        this.editFormOcca.get(['numeroDoc']).value,
        this.editFormOcca.get(['nip']).value,
        this.editFormOcca.get(['dateEtablissement']).value,
        this.editFormOcca.get(['lieuEtablissement']).value,
        this.editFormOcca.get(['autoriteEmettrice']).value,
        this.editFormOcca.get(['typeDocIdentification']).value
      ),
      nationDTO: this.editFormOcca.get(['nation']).value,
      carteWDTO: this.editFormOcca.get(['carteW']).value
    };
  }
  private createFromFormOcca1(): IVehiculeOccasional {
    return {
      ...new VehiculeOccasional(),
      id: this.editForm.get(['id'])!.value,
      chassis: this.editFormOcca1.get(['chassis'])!.value,
      marque: this.editFormOcca1.get(['marque'])!.value,
      model: this.editFormOcca1.get(['model'])!.value,
      createdAt: this.editFormOcca1.get(['createdAt'])!.value
        ? moment(this.editFormOcca1.get(['createdAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      personneMoraleDTO: new PersonneMorale(
        null,
        this.editFormOcca1.get(['numeroIFU']).value,
        this.editFormOcca1.get(['denomination']).value,
        this.editFormOcca1.get(['dateCreate']).value,
        this.editFormOcca1.get(['telephone']).value
      ),
      docIdentificationPMDTO: new DocIdentificationPM(
        null,
        this.editFormOcca1.get(['numeroDoc1']).value,
        this.editFormOcca1.get(['numeroIFU']).value,
        this.editFormOcca1.get(['numeroRCCM']).value
      ),
      nationDTO: this.editFormOcca1.get(['nation']).value,
      carteWDTO: this.editFormOcca.get(['carteW']).value
    };
  }

  protected subscribeToSaveResponseTrav(result: Observable<HttpResponse<IVehiculeTraversant>>): void {
    result.subscribe(() => this.onSaveSuccessTrav(), () => this.onSaveErrorTrav());
  }

  protected subscribeToSaveResponseOcca(result: Observable<HttpResponse<IVehiculeOccasional>>): void {
    result.subscribe(() => this.onSaveSuccessTrav(), () => this.onSaveErrorTrav());
  }

  toggle(checked: boolean) {
    this.checked2 = false;
    this.checked = checked;
  }
  toggle2(checked2: boolean) {
    this.checked2 = checked2;
    this.checked = false;
  }

  verifNip() {
    this.docIdentificationPPService.findByNip(this.editFormTrav.get(['nip']).value).subscribe(res => {
      this.docexist = res;
      if (this.docexist.body.nip != null) {
        this.exist = true;
      }
    });
  }

  videNip() {
    this.editFormTrav.get(['nip']).reset(null);
    this.docexist = null;
    this.exist = null;
  }

  videIFU() {
    this.editFormTrav1.get(['numeroIFU']).reset(null);
    this.docexist1 = null;
    this.exist1 = null;
  }

  verifIFU() {
    this.docIdentificationPMService.findByIFU(this.editFormTrav1.get(['numeroIFU']).value).subscribe(res => {
      this.docexist1 = res;
      if (this.docexist1.body.numeroIFU != null) {
        this.exist1 = true;
      }
    });
  }

  verifNipOcca() {
    this.docIdentificationPPService.findByNip(this.editFormOcca.get(['nip']).value).subscribe(res => {
      this.docexist = res;
      if (this.docexist.body.nip != null) {
        this.exist = true;
      }
    });
  }

  videNipOcca() {
    this.editFormOcca.get(['nip']).reset(null);
    this.docexist = null;
    this.exist = null;
  }

  videIFUOcca() {
    this.editFormOcca1.get(['numeroIFU']).reset(null);
    this.docexist1 = null;
    this.exist1 = null;
  }

  verifIFUOcca() {
    this.docIdentificationPMService.findByIFU(this.editFormOcca1.get(['numeroIFU']).value).subscribe(res => {
      this.docexist1 = res;
      if (this.docexist1.body.numeroIFU != null) {
        this.exist1 = true;
      }
    });
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  verifRevend() {
    this.carteW = this.editFormOcca.get(['carteW'])!.value;
    this.organisationService
      .queryRevendeurCollaborantWith(this.carteW.organisationId)
      .subscribe((res: HttpResponse<IOrganisation[]>) => (this.orgsRevendWith = res.body));
  }
}
