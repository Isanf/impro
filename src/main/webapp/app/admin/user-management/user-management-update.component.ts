import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ProfilService } from 'app/entities/profil/profil.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IProfil } from 'app/shared/model/profil.model';
import { JhiAlertService } from 'ng-jhipster';
import { PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { DocIdentificationPP, IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent implements OnInit {
  user: User;
  languages: any[];
  authorities: any[];
  profils: IProfil[];
  profilsList: IProfil[];
  organisations: IOrganisation[];
  organisation: IOrganisation[];
  organisationRevendeur: IOrganisation[];
  profilselec: IProfil[] = [];
  isSaving: boolean;
  recugerant: IProfil;
  allorganisationfils: IOrganisation[];
  orgConcessionnairetotal: IOrganisation[];
  enregist: string;

  editForm = this.fb.group({
    id: [''],
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(100), Validators.pattern('^[_.@A-Za-z0-9-]*')]],
    passwordConfirm: [
      '',
      [Validators.required, Validators.minLength(8), Validators.maxLength(100), Validators.pattern('^[_.@A-Za-z0-9-]*')]
    ],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [true],
    langKey: [],
    authorities: [],
    profilId: [],
    dateNaissance: [],
    lieuNaissance: [],
    telephone: [],
    residence: [],
    numeroDoc: [],
    dateEtablissement: [],
    lieuEtablissement: [],
    autoriteEmettrice: [],
    typeDocIdentification: [],
    organisationId: [],
    gerant: [],
    enregi: []
  });

  constructor(
    private jhiAlertService: JhiAlertService,
    private languageHelper: JhiLanguageHelper,
    private userService: UserService,
    private route: ActivatedRoute,
    private profilService: ProfilService,
    private organisationService: OrganisationService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.route.data.subscribe(({ user }) => {
      this.user = user.body ? user.body : user;
      if (this.user.id !== null) {
        this.updateForm(this.user);
      }
    });
    this.authorities = [];
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });

    this.organisationService
      .queryMyorganisation()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisation = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .queryRevendeurCollaborant()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisationRevendeur = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .queryAllorganisationsfils()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.allorganisationfils = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .queryConcessionnairetotal()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.orgConcessionnairetotal = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.profilService
      .query()
      .subscribe((res: HttpResponse<IProfil[]>) => (this.profils = res.body), (res: HttpErrorResponse) => this.onError(res.message));

    //this.profilsList = [];
    /*this.profilService
      .queryList()
      .subscribe((res: HttpResponse<IProfil[]>) => (this.profilsList = res.body),
      (res: HttpErrorResponse) => this.onError(res.message));*/

    /*this.profilService.queryList({ filter: 'user-is-null' }).subscribe(
      (res: HttpResponse<IProfil[]>) => {
        if (!this.editForm.get('profilId').value) {
          this.profilsList = res.body;
        } else {
          this.profilService
            .find(this.editForm.get('profilId').value)
            .subscribe(
              (subRes: HttpResponse<IProfil>) => (this.profilsList = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );*/

    this.languages = this.languageHelper.getAll();

    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.languages = this.languageHelper.getAll();
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      password: user.password,
      passwordConfirm: user.password,
      firstName: user.firstName,
      lastName: user.lastName,
      dateNaissance: user.personnePhysiqueDTO.dateNaissance ? null : user.personnePhysiqueDTO.dateNaissance,
      lieuNaissance: user.personnePhysiqueDTO.lieuNaissance ? null : user.personnePhysiqueDTO.lieuNaissance,
      residence: user.personnePhysiqueDTO.residence,
      telephone: user.personnePhysiqueDTO.telephone,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      profilId: user.profilId,
      personnePhysiqueDTO: user.personnePhysiqueDTO,
      numeroDoc: user.docIdentificationPPDTO.numeroDoc,
      dateEtablissement: user.docIdentificationPPDTO.dateEtablissement ? null : user.docIdentificationPPDTO.dateEtablissement,
      lieuEtablissement: user.docIdentificationPPDTO.lieuEtablissement,
      autoriteEmettrice: user.docIdentificationPPDTO.autoriteEmettrice,
      typeDocIdentification: user.docIdentificationPPDTO.typeDocIdentification
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== null) {
      this.userService.update(this.user).subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    } else {
      this.userService.create(this.user).subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }
  }

  private updateUser(user: User): void {
    user.personnePhysiqueDTO = new PersonnePhysique();
    user.docIdentificationPPDTO = new DocIdentificationPP();
    user.login = this.editForm.get(['login']).value;
    user.password = this.editForm.get(['password']).value;
    user.passwordConfirm = this.editForm.get(['passwordConfirm']).value;
    user.firstName = this.editForm.get(['firstName']).value;
    user.lastName = this.editForm.get(['lastName']).value;
    user.email = this.editForm.get(['email']).value;
    user.activated = this.editForm.get(['activated']).value;
    user.langKey = this.editForm.get(['langKey']).value;
    user.authorities = this.editForm.get(['authorities']).value;
    user.profilId = this.editForm.get(['profilId']).value;

    user.personnePhysiqueDTO.nom = this.editForm.get(['lastName']).value;
    user.personnePhysiqueDTO.prenom = this.editForm.get(['firstName']).value;
    user.personnePhysiqueDTO.dateNaissance = this.editForm.get(['dateNaissance']).value;
    user.personnePhysiqueDTO.lieuNaissance = this.editForm.get(['lieuNaissance']).value;
    user.personnePhysiqueDTO.residence = this.editForm.get(['residence']).value;
    user.personnePhysiqueDTO.gerant = this.editForm.get(['gerant']).value;
    user.personnePhysiqueDTO.telephone = this.editForm.get(['telephone']).value;
    //user.organisationDTO = this.editForm.get(['organisationId']).value;
    user.organisationDTO = this.editForm.get(['organisationId']).value;
    user.docIdentificationPPDTO.numeroDoc = this.editForm.get(['numeroDoc']).value;
    user.docIdentificationPPDTO.dateEtablissement = this.editForm.get(['dateEtablissement']).value;
    user.docIdentificationPPDTO.lieuEtablissement = this.editForm.get(['lieuEtablissement']).value;
    user.docIdentificationPPDTO.autoriteEmettrice = this.editForm.get(['autoriteEmettrice']).value;
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProfilById(index: number, item: IProfil) {
    return item.id;
  }

  getprofilorg() {
    this.recugerant = this.editForm.get(['organisationId']).value;
    this.profilselec = this.profils.filter(profil => profil.organisationId === this.recugerant.id);
  }

  vidprofilorg() {
    for (let i = 0; i < this.profilselec.length; i++) {
      this.profilselec.splice(i);
    }
  }

  changeHandler() {
    this.enregist = this.editForm.get(['enregi']).value;
  }
}
