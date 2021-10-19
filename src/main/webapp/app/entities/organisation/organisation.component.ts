import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OrganisationService } from './organisation.service';
import { OrganisationDeleteDialogComponent } from './organisation-delete-dialog.component';
import { FormBuilder } from '@angular/forms';
import { TypeActeurService } from 'app/entities/type-acteur/type-acteur.service';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';
import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { OrganisationLocaliteService } from 'app/entities/organisation-localite/organisation-localite.service';
import { PersonnePhysiqueService } from 'app/entities/personne-physique/personne-physique.service';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';
import { TypeOrganisationService } from 'app/entities/type-organisation/type-organisation.service';

@Component({
  selector: 'jhi-organisation',
  templateUrl: './organisation.component.html'
})
export class OrganisationComponent implements OnInit, OnDestroy {
  organisations?: IOrganisation[];
  localites?: IOrganisationLocalite[];
  organisationtout?: IOrganisation[];
  typeActeurs?: ITypeActeur[];
  typeOrganisations?: ITypeOrganisation[];
  personphysiques?: IPersonnePhysique[];
  selectedorg: IOrganisation[] = [];
  Myorganisation: IOrganisation[] = [];
  recuacteur: number;
  organisationsRevendeur?: IOrganisation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;
  orga: string;
  allorganisationfils: IOrganisation[];
  orgConcessionnairetotal: IOrganisation[];
  typeacteurs: ITypeActeur[];
  /////////////////////////
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
  ////////////////////////

  editForm = this.fb.group({
    org: []
  });
  checked = false;
  checked1 = false;
  checked2 = false;
  checked3 = false;
  checked4 = false;
  checked5 = false;
  checked6 = false;

  constructor(
    protected organisationService: OrganisationService,
    protected organisationLocaliteService: OrganisationLocaliteService,
    protected personnePhysiqueService: PersonnePhysiqueService,
    protected typeActeurService: TypeActeurService,
    protected typeOrganisationService: TypeOrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private fb: FormBuilder,
    private httpClient: HttpClient
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.organisationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOrganisation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());

    this.organisationService
      .queryRevendeurCollaborant({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOrganisation[]>) => this.onSuccess1(res.body, res.headers, pageToLoad), () => this.onError());
    this.typeActeurService.query().subscribe((res: HttpResponse<ITypeActeur[]>) => (this.typeActeurs = res.body));
    this.typeOrganisationService.query().subscribe((res: HttpResponse<ITypeOrganisation[]>) => (this.typeOrganisations = res.body));

    this.organisationService.queryMyorganisation().subscribe((res: HttpResponse<IOrganisation[]>) => (this.Myorganisation = res.body));
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInOrganisations();

    this.organisationService
      .queryAllorganisationsfils()
      .subscribe((res: HttpResponse<IOrganisation[]>) => (this.allorganisationfils = res.body), (res: HttpErrorResponse) => this.onError());

    this.organisationService
      .queryConcessionnairetotal()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.orgConcessionnairetotal = res.body),
        (res: HttpErrorResponse) => this.onError()
      );

    this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisationtout = res.body));
    this.typeActeurService.query().subscribe((res: HttpResponse<ITypeActeur[]>) => (this.typeacteurs = res.body));
    this.organisationLocaliteService.query().subscribe((res: HttpResponse<IOrganisationLocalite[]>) => (this.localites = res.body));
    this.personnePhysiqueService.query().subscribe((res: HttpResponse<IPersonnePhysique[]>) => (this.personphysiques = res.body));
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrganisation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrganisations(): void {
    this.eventSubscriber = this.eventManager.subscribe('organisationListModification', () => this.loadPage());
  }

  delete(organisation: IOrganisation): void {
    const modalRef = this.modalService.open(OrganisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organisation = organisation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IOrganisation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/organisation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.organisations = data || [];
  }

  protected onSuccess1(data: IOrganisation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/organisation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.organisationsRevendeur = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  changeHandler() {
    this.orga = this.editForm.get(['org']).value;
  }

  getorg() {
    this.recuacteur = this.editForm.get(['org']).value;
    for (const oneorg of this.organisations) {
      if (oneorg.typeActeurId === this.recuacteur) {
        this.selectedorg.push(oneorg);
      }
    }
  }

  vidselectedorg() {
    for (let i = 0; i < this.selectedorg.length; i++) {
      this.selectedorg.splice(i);
    }
  }

  //Gets called when the user selects an image
  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload(Org: IOrganisation) {
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    //Make a call to the Spring Boot Application to save the image
    this.httpClient.post(`${'http://localhost:8080/image/upload'}/${Org}`, uploadImageData, { observe: 'response' }).subscribe(response => {
      if (response.status === 200) {
        this.message = 'Image uploaded successfully';
      } else {
        this.message = 'Image not uploaded successfully';
      }
    });
  }

  //Gets called when the user clicks on retieve image button to get the image from back end
  getImage(Org: IOrganisation) {
    //Make a call to Sprinf Boot to get the Image Bytes.
    this.httpClient.get(`${'http://localhost:8080/image/get'}/${Org}`).subscribe(res => {
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
    });
  }

  toggle(checked: boolean, id: number) {
    this.checked = false;
    this.checked = !checked;
    this.selectedorg = this.organisations.filter(orga => orga.typeActeurId === id);
  }
}
