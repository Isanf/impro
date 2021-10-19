import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicule } from 'app/shared/model/vehicule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VehiculeService } from './vehicule.service';
import { VehiculeDeleteDialogComponent } from './vehicule-delete-dialog.component';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';

@Component({
  selector: 'jhi-vehicule',
  templateUrl: './vehicule.component.html'
})
export class VehiculeComponent implements OnInit, OnDestroy {
  vehicules: IVehicule[];
  vehicules1: IVehicule[];
  vehiculesh: IVehicule[];
  marques: IMarqueVehicule[];
  types: ITypeVehicule[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  ascending!: boolean;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  searchText: string;
  checked = false;
  checked2 = false;

  constructor(
    protected vehiculeService: VehiculeService,
    protected marqueVehiculeService: MarqueVehiculeService,
    protected typeVehiculeService: TypeVehiculeService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.vehiculeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehicule[]>) => this.paginateVehicules(res.body, res.headers));

    this.vehiculeService
      .queryVehiculesForRevendeur({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehicule[]>) => this.onSuccess1(res.body, res.headers));

    this.vehiculeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehicule[]>) => this.onSuccess(res.body, res.headers));

    this.vehiculeService
      .queryh({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVehicule[]>) => this.onSuccessh(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'desc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/vehicule',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInVehicules();

    this.typeVehiculeService.query().subscribe((res: HttpResponse<ITypeVehicule[]>) => (this.types = res.body));

    this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marques = res.body));
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVehicule) {
    return item.id;
  }

  registerChangeInVehicules() {
    this.eventSubscriber = this.eventManager.subscribe('vehiculeListModification', () => this.loadAll());
  }

  delete(vehicule: IVehicule) {
    const modalRef = this.modalService.open(VehiculeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehicule = vehicule;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVehicules(data: IVehicule[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.vehicules = data;
  }

  protected paginateVehicules1(data: IVehicule[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.vehicules1 = data;
  }

  protected onSuccess1(data: IVehicule[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.router.navigate(['/vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.vehicules1 = data || [];
  }

  protected onSuccess(data: IVehicule[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.router.navigate(['/vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.vehicules = data || [];
  }

  protected onSuccessh(data: IVehicule[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.router.navigate(['/vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.vehiculesh = data || [];
  }

  toggle(checked: boolean) {
    this.checked2 = false;
    this.checked = checked;
  }
  toggle2(checked2: boolean) {
    this.checked2 = checked2;
    this.checked = false;
  }
}
