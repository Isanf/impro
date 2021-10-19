import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from './organisation.service';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from 'app/entities/type-organisation/type-organisation.service';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeActeurService } from 'app/entities/type-acteur/type-acteur.service';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { OrganisationLocaliteService } from 'app/entities/organisation-localite/organisation-localite.service';
import { IOrganisationLocalite, OrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { map, startWith } from 'rxjs/operators';

type SelectableEntity = IOrganisation | ITypeOrganisation | ITypeActeur;

@Component({
  selector: 'jhi-organisation-update',
  templateUrl: './organisation-update.component.html'
})
export class OrganisationUpdateComponent implements OnInit {
  isSaving = false;
  nouv = false;
  organisations: IOrganisation[] = [];
  typeorganisations: ITypeOrganisation[] = [];
  typeacteurs: ITypeActeur[] = [];
  organisationlocalites: IOrganisationLocalite[] = [];
  fileToUpload: File = null;
  selected = [];
  /////////////////////////
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
  ////////////////////////
  @ViewChild('autoInput', { static: true }) input;

  filteredOptions$: Observable<IOrganisationLocalite[]>;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [null, [Validators.maxLength(1000)]],
    numeroOrdre: [],
    organisationLocaliteId: [],
    pereId: [],
    typeOrganisationId: [],
    typeActeurId: [],
    numeroPhone: [],
    fichierSign: [],
    fichierLogo: [],
    SignContentType: [],
    nomlocalite: [],
    LogoContentType: []
  });

  docIdentificationPMeditForm = this.fb.group({
    id: [],
    numero: [],
    numeroIFU: [],
    numeroRCCM: [],
    email: [],
    codePostal: []
  });
  checked = false;
  checked2 = false;
  checked3 = false;
  checked4 = false;

  constructor(
    protected organisationService: OrganisationService,
    protected typeOrganisationService: TypeOrganisationService,
    protected typeActeurService: TypeActeurService,
    protected organisationLocaliteService: OrganisationLocaliteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected elementRef: ElementRef,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisation }) => {
      this.updateForm(organisation);

      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.typeOrganisationService.query().subscribe((res: HttpResponse<ITypeOrganisation[]>) => (this.typeorganisations = res.body || []));

      this.organisationLocaliteService
        .query()
        .subscribe((res: HttpResponse<IOrganisationLocalite[]>) => (this.organisationlocalites = res.body || []));

      this.typeActeurService.query().subscribe((res: HttpResponse<ITypeActeur[]>) => (this.typeacteurs = res.body || []));
    });
  }

  updateForm(organisation: IOrganisation): void {
    this.editForm.patchValue({
      id: organisation.id,
      nom: organisation.nom,
      description: organisation.description,
      numeroOrdre: organisation.numeroOrdre,
      pereId: organisation.pereId,
      typeOrganisationId: organisation.typeOrganisationId,
      typeActeurId: organisation.typeActeurId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisation = this.createFromForm();
    if (organisation.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationService.update(organisation));
    } else {
      this.subscribeToSaveResponse(this.organisationService.create(organisation));
      this.organisationService.createFile(this.fileToUpload);
    }
  }

  private createFromForm(): IOrganisation {
    return {
      ...new Organisation(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      numeroOrdre: this.editForm.get(['numeroOrdre'])!.value,
      organisationLocaliteId: this.editForm.get(['organisationLocaliteId']).value,
      pereId: this.editForm.get(['pereId'])!.value,
      typeOrganisationId: this.editForm.get(['typeOrganisationId'])!.value,
      typeActeurId: null,
      docIdentificationPMDTO: new DocIdentificationPM(
        null,
        this.docIdentificationPMeditForm.get(['numero']).value,
        this.docIdentificationPMeditForm.get(['numeroIFU']).value,
        this.docIdentificationPMeditForm.get(['numeroRCCM']).value,
        null,
        this.editForm.get(['organisationLocaliteId']).value,
        this.docIdentificationPMeditForm.get(['codePostal']).value,
        this.docIdentificationPMeditForm.get(['email']).value
      ),
      numeroPhone: this.editForm.get(['numeroPhone'])!.value,
      organisationlocaliteDTO: new OrganisationLocalite(null, null, this.editForm.get(['nomlocalite'])!.value, null, null),
      acteur: this.editForm.get(['typeActeurId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisation>>): void {
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

  onSelectionChange($event) {
    this.filteredOptions$ = this.getFilteredOptions($event);
  }

  viewHandle(value: string) {
    return value;
  }

  private filter(value: string): IMarqueVehicule[] {
    const filterValue = value;
    return this.organisationlocalites.filter(optionValue => optionValue.nom.toLowerCase().includes(filterValue));
  }

  getFilteredOptions(value: string): Observable<IMarqueVehicule[]> {
    return of(value).pipe(map(filterString => this.filter(filterString)));
  }

  onChange() {
    this.filteredOptions$ = this.getFilteredOptions(this.input.nativeElement.value);
  }

  clic() {
    this.activatedRoute.data.subscribe(() => {
      this.organisationLocaliteService.query().subscribe((res: HttpResponse<IOrganisationLocalite[]>) => {
        this.organisationlocalites = res.body || [];
        this.filteredOptions$ = of(res.body);
        this.filteredOptions$ = this.editForm.get(['organisationLocaliteId'])!.valueChanges.pipe(
          startWith(''),
          map(filterString => this.filter(filterString.toString().toLowerCase()))
        );
      });
    });
  }

  new() {
    this.nouv = true;
  }

  toggle(checked: boolean) {
    this.checked2 = false;
    this.checked3 = false;
    this.checked4 = false;
    this.checked = checked;
  }
  toggle2(checked: boolean) {
    this.checked2 = checked;
    this.checked = false;
    this.checked3 = false;
    this.checked4 = false;
  }
  toggle3(checked: boolean) {
    this.checked3 = checked;
    this.checked = false;
    this.checked2 = false;
    this.checked4 = false;
  }
  toggle4(checked: boolean) {
    this.checked4 = checked;
    this.checked = false;
    this.checked3 = false;
    this.checked2 = false;
  }
}
