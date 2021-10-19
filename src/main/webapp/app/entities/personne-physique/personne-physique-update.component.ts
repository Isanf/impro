import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPersonnePhysique, PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonnePhysiqueService } from './personne-physique.service';
import { IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IProfil } from 'app/shared/model/profil.model';
import { ProfilService } from 'app/entities/profil/profil.service';

@Component({
  selector: 'jhi-personne-physique-update',
  templateUrl: './personne-physique-update.component.html'
})
export class PersonnePhysiqueUpdateComponent implements OnInit {
  isSaving: boolean;

  docidentifications: IDocIdentificationPP[];

  users: IUser[];

  organisations: IOrganisation[];

  profils: IProfil[];
  dateNaissanceDp: any;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    dateNaissance: [],
    lieuNaissance: [],
    telephone: [],
    residence: [],
    docIdentificationId: [],
    userId: [],
    organisationId: [],
    profilId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected personnePhysiqueService: PersonnePhysiqueService,
    protected docIdentificationPPService: DocIdentificationPPService,
    protected userService: UserService,
    protected organisationService: OrganisationService,
    protected profilService: ProfilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ personnePhysique }) => {
      this.updateForm(personnePhysique);
    });
    this.docIdentificationPPService.querylist({ filter: 'personnephysique-is-null' }).subscribe(
      (res: HttpResponse<IDocIdentificationPP[]>) => {
        if (!this.editForm.get('docIdentificationId').value) {
          this.docidentifications = res.body;
        } else {
          this.docIdentificationPPService
            .find(this.editForm.get('docIdentificationId').value)
            .subscribe(
              (subRes: HttpResponse<IDocIdentificationPP>) => (this.docidentifications = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.profilService
      .query()
      .subscribe((res: HttpResponse<IProfil[]>) => (this.profils = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(personnePhysique: IPersonnePhysique) {
    this.editForm.patchValue({
      id: personnePhysique.id,
      nom: personnePhysique.nom,
      prenom: personnePhysique.prenom,
      dateNaissance: personnePhysique.dateNaissance,
      lieuNaissance: personnePhysique.lieuNaissance,
      telephone: personnePhysique.telephone,
      residence: personnePhysique.residence,
      docIdentificationId: personnePhysique.docIdentificationId,
      userId: personnePhysique.userId,
      organisationId: personnePhysique.organisationId,
      profilId: personnePhysique.profilId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const personnePhysique = this.createFromForm();
    if (personnePhysique.id !== undefined) {
      this.subscribeToSaveResponse(this.personnePhysiqueService.update(personnePhysique));
    } else {
      this.subscribeToSaveResponse(this.personnePhysiqueService.create(personnePhysique));
    }
  }

  private createFromForm(): IPersonnePhysique {
    return {
      ...new PersonnePhysique(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      prenom: this.editForm.get(['prenom']).value,
      dateNaissance: this.editForm.get(['dateNaissance']).value,
      lieuNaissance: this.editForm.get(['lieuNaissance']).value,
      telephone: this.editForm.get(['telephone']).value,
      residence: this.editForm.get(['residence']).value,
      gerant: null,
      docIdentificationId: this.editForm.get(['docIdentificationId']).value,
      userId: this.editForm.get(['userId']).value,
      organisationId: this.editForm.get(['organisationId']).value,
      profilId: this.editForm.get(['profilId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonnePhysique>>) {
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

  trackDocIdentificationPPById(index: number, item: IDocIdentificationPP) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackOrganisationById(index: number, item: IOrganisation) {
    return item.id;
  }

  trackProfilById(index: number, item: IProfil) {
    return item.id;
  }
}
