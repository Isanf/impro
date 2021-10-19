import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeActeur, TypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeActeurService } from './type-acteur.service';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from 'app/entities/type-organisation/type-organisation.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-type-acteur-update',
  templateUrl: './type-acteur-update.component.html'
})
export class TypeActeurUpdateComponent implements OnInit {
  @Input() typeActeur: ITypeActeur;

  isSaving = false;
  typeorganisations: ITypeOrganisation[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    typeOrganisations: []
  });

  constructor(
    protected typeActeurService: TypeActeurService,
    protected typeOrganisationService: TypeOrganisationService,
    protected activatedRoute: ActivatedRoute,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeActeur }) => {
      this.typeOrganisationService.query().subscribe((res: HttpResponse<ITypeOrganisation[]>) => {
        this.typeorganisations = res.body || [];
      });
    });
  }

  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  updateForm(typeActeur: ITypeActeur): void {
    this.editForm.patchValue({
      id: typeActeur.id,
      nom: typeActeur.nom,
      description: typeActeur.description,
      typeOrganisations: typeActeur.typeOrganisations
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeActeur = this.createFromForm();
    if (typeActeur.id !== null) {
      this.subscribeToSaveResponse(this.typeActeurService.update(typeActeur));
    } else {
      this.subscribeToSaveResponse(this.typeActeurService.create(typeActeur));
    }
  }

  private createFromForm(): ITypeActeur {
    return {
      ...new TypeActeur(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      typeOrganisations: this.editForm.get(['typeOrganisations'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeActeur>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.eventManager.broadcast({
      name: 'typeActeurListModification',
      content: 'Saving an TypeActeur'
    });
    this.activeModal.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
    this.eventManager.broadcast({
      name: 'typeActeurListModification',
      content: 'Saving an TypeActeur'
    });
    this.activeModal.close();
  }

  trackById(index: number, item: ITypeOrganisation): any {
    return item.id;
  }

  getSelected(selectedVals: ITypeOrganisation[], option: ITypeOrganisation): ITypeOrganisation {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
