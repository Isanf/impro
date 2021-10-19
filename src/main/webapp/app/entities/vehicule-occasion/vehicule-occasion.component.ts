import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehiculeOccasion } from 'app/shared/model/vehicule-occasion.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VehiculeOccasionService } from './vehicule-occasion.service';
import { VehiculeOccasionDeleteDialogComponent } from './vehicule-occasion-delete-dialog.component';
import { IVehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';
import { VehiculeTraversantService } from 'app/entities/vehicule-traversant/vehicule-traversant.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { PersonnePhysiqueService } from 'app/entities/personne-physique/personne-physique.service';
import { PersonneMoraleService } from 'app/entities/personne-morale/personne-morale.service';
import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';
import { IPersonneMorale } from 'app/shared/model/personne-morale.model';
import { VehiculeOccasionalService } from 'app/entities/vehicule-occasional/vehicule-occasional.service';
import { IVehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';
import { ICarteW } from 'app/shared/model/carte-w.model';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';

@Component({
  selector: 'jhi-vehicule-occasion',
  templateUrl: './vehicule-occasion.component.html'
})
export class VehiculeOccasionComponent implements OnInit, OnDestroy {
  vehiculeOccasions?: IVehiculeOccasion[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  vehiculeTraversants?: IVehiculeTraversant[];
  vehiculeOccas?: IVehiculeOccasional[];
  personnePhysiques?: IPersonnePhysique[];
  personneMorales?: IPersonneMorale[];
  checked = false;
  checked2 = false;
  searchText: string;
  listcartew: ICarteW[];

  constructor(
    protected vehiculeOccasionService: VehiculeOccasionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected vehiculeTraversantService: VehiculeTraversantService,
    protected personnePhysiqueService: PersonnePhysiqueService,
    protected personneMoraleService: PersonneMoraleService,
    protected vehiculeOccasionalService: VehiculeOccasionalService,
    protected carteWService: CarteWService,
    protected jhiAlertService: JhiAlertService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page;

    this.vehiculeTraversantService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehiculeTraversant[]>) => (this.vehiculeTraversants = res.body));

    this.vehiculeOccasionalService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehiculeOccasional[]>) => (this.vehiculeOccas = res.body));

    this.personnePhysiqueService.query().subscribe((res: HttpResponse<IPersonnePhysique[]>) => (this.personnePhysiques = res.body));

    this.personneMoraleService.query().subscribe((res: HttpResponse<IPersonneMorale[]>) => (this.personneMorales = res.body));

    this.carteWService
      .query()
      .subscribe((res: HttpResponse<ICarteW[]>) => (this.listcartew = res.body), (res: HttpErrorResponse) => this.onError1(res.message));
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.handleBackNavigation();
    this.registerChangeInVehiculeOccasions();
  }

  handleBackNavigation(): void {
    this.activatedRoute.queryParamMap.subscribe((params: ParamMap) => {
      const prevPage = params.get('page');
      const prevSort = params.get('sort');
      const prevSortSplit = prevSort.split(',');
      if (prevSortSplit) {
        this.predicate = prevSortSplit[0];
        this.ascending = prevSortSplit[1] === 'asc';
      }
      if (prevPage && +prevPage !== this.page) {
        this.ngbPaginationPage = +prevPage;
        this.loadPage(+prevPage);
      } else {
        this.loadPage(this.page);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVehiculeOccasion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVehiculeOccasions(): void {
    this.eventSubscriber = this.eventManager.subscribe('vehiculeOccasionListModification', () => this.loadPage());
  }

  delete(vehiculeOccasion: IVehiculeOccasion): void {
    const modalRef = this.modalService.open(VehiculeOccasionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehiculeOccasion = vehiculeOccasion;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IVehiculeOccasion[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/vehicule-occasion'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.vehiculeOccasions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
  protected onError1(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  toggle(checked: boolean) {
    this.checked2 = false;
    this.checked = checked;
  }
  toggle2(checked2: boolean) {
    this.checked2 = checked2;
    this.checked = false;
  }

  test() {
    alert('Hi');
    alert(this.vehiculeOccasions.length);
    /*for(let v of this.vehiculeOccasions){
      alert(v.chassis);
    }*/
  }
}
