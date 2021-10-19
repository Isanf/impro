import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IImmatriculation, Immatriculation } from 'app/shared/model/immatriculation.model';
import { ImmatriculationService } from './immatriculation.service';
import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { CertificatImmatriculationService } from 'app/entities/certificat-immatriculation/certificat-immatriculation.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonnePhysiqueService } from 'app/entities/personne-physique/personne-physique.service';
import { IPersonneMorale } from 'app/shared/model/personne-morale.model';
import { PersonneMoraleService } from 'app/entities/personne-morale/personne-morale.service';
import { IVehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';

type SelectableEntity = ICertificatImmatriculation | IOrganisation | IPersonnePhysique | IPersonneMorale | IVehicule;

@Component({
  selector: 'jhi-immatriculation-update',
  templateUrl: './immatriculation-update.component.html'
})
export class ImmatriculationUpdateComponent implements OnInit {
  isSaving = false;
  certificatimmatriculations: ICertificatImmatriculation[] = [];
  organisations: IOrganisation[] = [];
  personnephysiques: IPersonnePhysique[] = [];
  personnemorales: IPersonneMorale[] = [];
  vehicules: IVehicule[] = [];

  editForm = this.fb.group({
    id: [],
    numero: [],
    dateImmatriculation: [],
    certificatImmatriculationId: [],
    organisationId: [],
    personnePhysiqueId: [],
    personneMoraleId: [],
    vehiculeId: [require]
  });

  constructor(
    protected immatriculationService: ImmatriculationService,
    protected certificatImmatriculationService: CertificatImmatriculationService,
    protected organisationService: OrganisationService,
    protected personnePhysiqueService: PersonnePhysiqueService,
    protected personneMoraleService: PersonneMoraleService,
    protected vehiculeService: VehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ immatriculation }) => {
      if (!immatriculation.id) {
        const today = moment().startOf('day');
        immatriculation.dateImmatriculation = today;
      }

      this.updateForm(immatriculation);

      this.vehiculeService.query().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules = res.body || []));
    });
  }

  updateForm(immatriculation: IImmatriculation): void {
    this.editForm.patchValue({
      id: immatriculation.id,
      numero: immatriculation.numero,
      dateImmatriculation: immatriculation.dateImmatriculation ? immatriculation.dateImmatriculation.format(DATE_TIME_FORMAT) : null,
      certificatImmatriculationId: immatriculation.certificatImmatriculationId,
      organisationId: immatriculation.organisationId,
      personnePhysiqueId: immatriculation.personnePhysiqueId,
      personneMoraleId: immatriculation.personneMoraleId,
      vehiculeId: immatriculation.vehiculeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const immatriculation = this.createFromForm();
    if (immatriculation.id !== undefined) {
      this.subscribeToSaveResponse(this.immatriculationService.update(immatriculation));
    } else {
      this.subscribeToSaveResponse(this.immatriculationService.create(immatriculation));
    }
  }

  private createFromForm(): IImmatriculation {
    return {
      ...new Immatriculation(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      dateImmatriculation: this.editForm.get(['dateImmatriculation'])!.value
        ? moment(this.editForm.get(['dateImmatriculation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      certificatImmatriculationId: this.editForm.get(['certificatImmatriculationId'])!.value,
      organisationId: this.editForm.get(['organisationId'])!.value,
      personnePhysiqueId: this.editForm.get(['personnePhysiqueId'])!.value,
      personneMoraleId: this.editForm.get(['personneMoraleId'])!.value,
      vehiculeId: this.editForm.get(['vehiculeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImmatriculation>>): void {
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
