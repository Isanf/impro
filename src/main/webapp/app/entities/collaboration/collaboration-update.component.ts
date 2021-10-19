import { Component, OnInit, ViewChild, ChangeDetectionStrategy, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ICollaboration, Collaboration } from 'app/shared/model/collaboration.model';
import { CollaborationService } from './collaboration.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'jhi-collaboration-update',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './collaboration-update.component.html'
})
export class CollaborationUpdateComponent implements OnInit {
  @Input() collaboration: ICollaboration;
  isSaving = false;
  organisations: IOrganisation[] = [];
  organisation: IOrganisation;
  searchText: String;
  filteredOptions$: Observable<IOrganisation[]>;

  @ViewChild('autoInput', { static: true }) input;

  editForm = this.fb.group({
    id: [],
    dateDebut: [],
    dateFin: [],
    numeroCollaboration: [],
    revendeurId: [],
    concessionnaireId: []
  });

  organisationEditForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    numeroOrdre: [null, [Validators.required]],
    pereId: [],
    typeOrganisationId: [],
    typeActeurId: [],
    inputFormControl: []
  });

  constructor(
    protected collaborationService: CollaborationService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaboration }) => {
      this.organisationService.queryRevendeurs().subscribe((res: HttpResponse<IOrganisation[]>) => {
        this.organisations = res.body || [];
        this.filteredOptions$ = of(res.body);

        this.filteredOptions$ = this.editForm.get(['revendeurId'])!.valueChanges.pipe(
          startWith(''),
          map(filterString => this.filter(filterString.toString().toLowerCase()))
        );
      });
    });
  }

  updateForm(collaboration: ICollaboration): void {
    this.editForm.patchValue({
      id: collaboration.id,
      dateDebut: collaboration.dateDebut,
      dateFin: collaboration.dateFin,
      numeroCollaboration: collaboration.numeroCollaboration,
      revendeurId: collaboration.revendeurId,
      concessionnaireId: collaboration.concessionnaireId
    });
  }

  previousState(): void {
    //this.activeModal.dismiss();
    window.history.back();
  }
  viewHandle(value: string) {
    return value;
  }

  private filter(value: string): IOrganisation[] {
    const filterValue = value;
    return this.organisations.filter(optionValue => optionValue.nom.toLowerCase().includes(filterValue));
  }

  save(): void {
    this.isSaving = true;
    const collaboration = this.createFromForm();
    this.subscribeToSaveResponse(this.collaborationService.create(collaboration));
  }

  private createFromForm(): ICollaboration {
    return {
      ...new Collaboration(),
      id: this.editForm.get(['id'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      revendeurId: this.editForm.get(['revendeurId'])!.value,
      concessionnaireId: this.organisation.id
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaboration>>): void {
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

  getFilteredOptions(value: string): Observable<IOrganisation[]> {
    return of(value).pipe(map(filterString => this.filter(filterString)));
  }

  onChange() {
    this.filteredOptions$ = this.getFilteredOptions(this.input.nativeElement.value);
  }

  onSelectionChange($event) {
    this.filteredOptions$ = this.getFilteredOptions($event);
    this.organisation = this.organisations.find(orga => orga.nom.includes($event));
  }

  new() {
    this.router.navigate(['organisation/new']);
  }
}
