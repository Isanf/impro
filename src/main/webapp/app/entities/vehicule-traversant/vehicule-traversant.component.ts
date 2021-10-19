import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehiculeTraversant } from 'app/shared/model/vehicule-traversant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VehiculeTraversantService } from './vehicule-traversant.service';
import { VehiculeTraversantDeleteDialogComponent } from './vehicule-traversant-delete-dialog.component';

@Component({
  selector: 'jhi-vehicule-traversant',
  templateUrl: './vehicule-traversant.component.html'
})
export class VehiculeTraversantComponent implements OnInit, OnDestroy {
  vehiculeTraversants?: IVehiculeTraversant[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected vehiculeTraversantService: VehiculeTraversantService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.vehiculeTraversantService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IVehiculeTraversant[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInVehiculeTraversants();
  }

  protected handleNavigation(): void {}

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVehiculeTraversant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVehiculeTraversants(): void {
    this.eventSubscriber = this.eventManager.subscribe('vehiculeTraversantListModification', () => this.loadPage());
  }

  delete(vehiculeTraversant: IVehiculeTraversant): void {
    const modalRef = this.modalService.open(VehiculeTraversantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehiculeTraversant = vehiculeTraversant;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IVehiculeTraversant[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/vehicule-traversant'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
        }
      });
    }
    this.vehiculeTraversants = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {}
}
