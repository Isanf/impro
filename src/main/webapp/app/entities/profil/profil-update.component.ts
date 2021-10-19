import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IProfil, Profil } from 'app/shared/model/profil.model';
import { ProfilService } from './profil.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

@Component({
  selector: 'jhi-profil-update',
  templateUrl: './profil-update.component.html'
})
export class ProfilUpdateComponent implements OnInit {
  isSaving: boolean;

  organisations: IOrganisation[];
  organisation: IOrganisation[];
  allorganisationfils: IOrganisation[];
  orgConcessionnairetotal: IOrganisation[];
  organisationtout: IOrganisation[];
  organisationConcessionnaire: IOrganisation[];
  organisationRevendeur: IOrganisation[];
  users: IUser[];
  roles: any[];
  roles4STH: any[];
  roles4DGTTM: any[];
  roles4DGDGTTM: any[];
  roles4DOUANE: any[];
  roles4DGDOUANE: any[];
  roles4STHGuichet: any[];
  roles4Concessionnaire: any[];
  roles4Revendeur: any[];
  enregist: string;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    organisationId: [],
    roles: [],
    enregi: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected profilService: ProfilService,
    protected organisationService: OrganisationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profil }) => {
      this.updateForm(profil);
    });

    this.organisationService
      .queryMyorganisation()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisation = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisationtout = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisationConcessionnaire = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.organisationService
      .queryRevendeurs()
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

    this.organisationService
      .queryRevendeurCollaborant()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.userService.authorities().subscribe(authorities => {
      this.roles = authorities;
    });

    this.userService.authorities4STH().subscribe(authorities => {
      this.roles4STH = authorities;
    });

    this.userService.authorities4STHGuichet().subscribe(authorities => {
      this.roles4STHGuichet = authorities;
    });

    this.userService.authorities4Concessionnaire().subscribe(authorities => {
      this.roles4Concessionnaire = authorities;
    });

    this.userService.authorities4Revendeur().subscribe(authorities => {
      this.roles4Revendeur = authorities;
    });

    this.userService.authorities4DGTTM().subscribe(authorities => {
      this.roles4DGTTM = authorities;
    });

    this.userService.authorities4DOUANE().subscribe(authorities => {
      this.roles4DOUANE = authorities;
    });

    this.userService.authorities4DGDGTTM().subscribe(authorities => {
      this.roles4DGDGTTM = authorities;
    });

    this.userService.authorities4DGDOUANE().subscribe(authorities => {
      this.roles4DGDOUANE = authorities;
    });
  }

  updateForm(profil: IProfil) {
    this.editForm.patchValue({
      id: profil.id,
      nom: profil.nom,
      description: profil.description,
      organisationId: profil.organisationId,
      roles: profil.roles
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profil = this.createFromForm();
    if (profil.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil));
    }
  }

  saveSTH() {
    this.isSaving = true;
    const profilSTH = this.createFromFormSTH();
    if (profilSTH.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profilSTH));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profilSTH));
    }
  }

  saveConcessionnaire() {
    this.isSaving = true;
    const profilConcessionnaire = this.createFromFormConcessionnaire();
    if (profilConcessionnaire.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profilConcessionnaire));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profilConcessionnaire));
    }
  }

  saveRevendeur() {
    this.isSaving = true;
    const profilRevendeur = this.createFromFormRevendeur();
    if (profilRevendeur.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profilRevendeur));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profilRevendeur));
    }
  }

  save4Concessionnaire() {
    this.isSaving = true;
    const profil4Concessionnaire = this.createFromForm4Concessionnaire();
    if (profil4Concessionnaire.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4Concessionnaire));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4Concessionnaire));
    }
  }

  save4Revendeur() {
    this.isSaving = true;
    const profil4Revendeur = this.createFromForm4Revendeur();
    if (profil4Revendeur.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4Revendeur));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4Revendeur));
    }
  }

  save4STHGuichet() {
    this.isSaving = true;
    const profil4STHGuichet = this.createFromForm4STHGuichet();
    if (profil4STHGuichet.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4STHGuichet));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4STHGuichet));
    }
  }
  save4DGTTM() {
    this.isSaving = true;
    const profil4DGTTM = this.createFromForm4DGTTM();
    if (profil4DGTTM.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4DGTTM));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4DGTTM));
    }
  }

  save4DOUANE() {
    this.isSaving = true;
    const profil4DOUANE = this.createFromForm4DOUANE();
    if (profil4DOUANE.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4DOUANE));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4DOUANE));
    }
  }

  save4DGDGTTM() {
    this.isSaving = true;
    const profil4DGDGTTM = this.createFromForm4DGDGTTM();
    if (profil4DGDGTTM.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4DGDGTTM));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4DGDGTTM));
    }
  }

  save4DGDOUANE() {
    this.isSaving = true;
    const profil4DGDOUANE = this.createFromForm4DGDOUANE();
    if (profil4DGDOUANE.id !== undefined) {
      this.subscribeToSaveResponse(this.profilService.update(profil4DGDOUANE));
    } else {
      this.subscribeToSaveResponse(this.profilService.create(profil4DGDOUANE));
    }
  }

  private createFromForm(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.editForm.get(['roles']).value
    };
  }

  private createFromForm4STHGuichet(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4STHGuichet
    };
  }

  private createFromFormConcessionnaire(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.editForm.get(['organisationId']).value,
      organisationId: this.organisation[0].id,
      roles: this.roles4Concessionnaire
    };
  }

  private createFromFormRevendeur(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      organisationId: this.organisation[0].id,
      roles: this.roles4Revendeur
    };
  }

  private createFromForm4Concessionnaire(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      organisationId: this.editForm.get(['organisationId']).value,
      //organisationId: this.organisation[0].id,
      roles: this.roles4Concessionnaire
    };
  }

  private createFromForm4Revendeur(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.organisation[0].id,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4Revendeur
    };
  }

  private createFromFormSTH(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      organisationId: this.organisation[0].id,
      roles: this.roles4STH
    };
  }

  private createFromForm4DGTTM(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.organisation[0].id,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4DGTTM
    };
  }

  private createFromForm4DOUANE(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.organisation[0].id,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4DOUANE
    };
  }

  private createFromForm4DGDGTTM(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.organisation[0].id,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4DGDGTTM
    };
  }

  private createFromForm4DGDOUANE(): IProfil {
    return {
      ...new Profil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      //organisationId: this.organisation[0].id,
      organisationId: this.editForm.get(['organisationId']).value,
      roles: this.roles4DGDOUANE
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfil>>) {
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
  trackOrganisationById(index: number, item: IOrganisation) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  changeHandler() {
    this.enregist = this.editForm.get(['enregi']).value;
  }
}
