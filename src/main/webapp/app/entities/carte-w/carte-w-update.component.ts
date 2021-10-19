import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICarteW, CarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from './carte-w.service';
import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeOrganisationService } from 'app/entities/type-organisation/type-organisation.service';
import { TypeActeurService } from 'app/entities/type-acteur/type-acteur.service';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { OrganisationLocaliteService } from 'app/entities/organisation-localite/organisation-localite.service';

@Component({
  selector: 'jhi-carte-w-update',
  templateUrl: './carte-w-update.component.html'
})
export class CarteWUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  typeorganisations: ITypeOrganisation[] = [];
  typeacteurs: ITypeActeur[] = [];
  organisationlocalites: IOrganisationLocalite[] = [];

  editForm = this.fb.group({
    numeroCarteW: [],
    dateEtablissementCarteW: [],
    dateExpirationCarteW: [],
    lieuEtablissement: [],
    organisationId: [],
    idOrg: [],
    nom: [],
    description: [],
    numeroOrdre: [],
    organisationLocaliteId: [],
    pereId: [],
    typeOrganisationId: [],
    typeActeurId: [],
    idpm: [],
    numero: [],
    numeroIFU: [],
    numeroRCCM: [],
    numeroPhone: [],
    email: [],
    codePostal: []
  });

  constructor(
    protected carteWService: CarteWService,
    protected organisationService: OrganisationService,
    protected typeOrganisationService: TypeOrganisationService,
    protected typeActeurService: TypeActeurService,
    protected organisationLocaliteService: OrganisationLocaliteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carteW }) => {
      if (carteW.id) {
        this.updateForm(carteW);
      }

      this.organisationService
        .query({ filter: 'cartew-is-null' })
        .pipe(
          map((res: HttpResponse<IOrganisation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IOrganisation[]) => {
          if (!carteW.organisationId) {
            this.organisations = resBody;
          } else {
            this.organisationService
              .find(carteW.organisationId)
              .pipe(
                map((subRes: HttpResponse<IOrganisation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IOrganisation[]) => (this.organisations = concatRes));
          }
        });
      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.typeOrganisationService.query().subscribe((res: HttpResponse<ITypeOrganisation[]>) => (this.typeorganisations = res.body || []));

      this.typeActeurService.query().subscribe((res: HttpResponse<ITypeActeur[]>) => (this.typeacteurs = res.body || []));

      this.organisationLocaliteService
        .query()
        .subscribe((res: HttpResponse<IOrganisationLocalite[]>) => (this.organisationlocalites = res.body || []));
    });
  }

  updateForm(carteW: ICarteW): void {
    this.editForm.patchValue({
      id: carteW.id,
      numeroCarteW: carteW.numeroCarteW,
      dateEtablissementCarteW: carteW.dateEtablissementCarteW,
      dateExpirationCarteW: carteW.dateExpirationCarteW,
      lieuEtablissement: carteW.lieuEtablissement,
      organisationId: carteW.organisationId,
      idOrg: carteW.organisationDTO.id,
      nom: carteW.organisationDTO.nom,
      description: carteW.organisationDTO.description,
      numeroOrdre: carteW.organisationDTO.numeroOrdre,
      pereId: carteW.organisationDTO.pereId,
      typeOrganisationId: carteW.organisationDTO.typeOrganisationId,
      idpm: carteW.docIdentificationPMDTO.id,
      numero: carteW.docIdentificationPMDTO.numero,
      numeroIFU: carteW.docIdentificationPMDTO.numeroIFU,
      numeroRCCM: carteW.docIdentificationPMDTO.numeroRCCM
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carteW = this.createFromForm();
    if (carteW.id !== undefined) {
      this.subscribeToSaveResponse(this.carteWService.update(carteW));
    } else {
      this.subscribeToSaveResponse(this.carteWService.create(carteW));
    }
  }

  private createFromForm(): ICarteW {
    return {
      ...new CarteW(),
      numeroCarteW: this.editForm.get(['numeroCarteW']).value,
      dateEtablissementCarteW: this.editForm.get(['dateEtablissementCarteW']).value,
      dateExpirationCarteW: this.editForm.get(['dateExpirationCarteW']).value,
      lieuEtablissement: this.editForm.get(['lieuEtablissement']).value,
      organisationId: this.editForm.get(['organisationId']).value,
      organisationDTO: new Organisation(
        null,
        this.editForm.get(['nom'])!.value,
        this.editForm.get(['description']).value,
        this.editForm.get(['numeroOrdre']).value,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        this.editForm.get(['organisationLocaliteId']).value,
        this.editForm.get(['pereId']).value,
        this.editForm.get(['typeOrganisationId']).value,
        null,
        this.editForm.get(['typeActeurId']).value,
        null
      ),
      docIdentificationPMDTO: new DocIdentificationPM(
        null,
        this.editForm.get(['numero']).value,
        this.editForm.get(['numeroIFU']).value,
        this.editForm.get(['numeroRCCM']).value,
        this.editForm.get(['numeroPhone']).value,
        this.editForm.get(['organisationLocaliteId']).value,
        this.editForm.get(['codePostal']).value,
        this.editForm.get(['email']).value
      )
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarteW>>): void {
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

  trackTypeOrganisationById(index: number, item: ITypeOrganisation): any {
    return item.id;
  }

  trackOrganisationById(index: number, item: IOrganisation): any {
    return item.id;
  }
}
