import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStatistique, Statistique } from 'app/shared/model/statistique.model';
import { StatistiqueService } from './statistique.service';

@Component({
  selector: 'jhi-statistique-update',
  templateUrl: './statistique-update.component.html'
})
export class StatistiqueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: []
  });

  constructor(protected statistiqueService: StatistiqueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statistique }) => {
      this.updateForm(statistique);
    });
  }

  updateForm(statistique: IStatistique): void {
    this.editForm.patchValue({
      id: statistique.id,
      nom: statistique.nom
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statistique = this.createFromForm();
    if (statistique.id !== undefined) {
      this.subscribeToSaveResponse(this.statistiqueService.update(statistique));
    } else {
      this.subscribeToSaveResponse(this.statistiqueService.create(statistique));
    }
  }

  private createFromForm(): IStatistique {
    return {
      ...new Statistique(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatistique>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
