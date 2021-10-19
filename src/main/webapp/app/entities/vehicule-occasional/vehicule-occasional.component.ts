import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehiculeOccasional } from 'app/shared/model/vehicule-occasional.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VehiculeOccasionalService } from './vehicule-occasional.service';
import { VehiculeOccasionalDeleteDialogComponent } from './vehicule-occasional-delete-dialog.component';

@Component({
  selector: 'jhi-vehicule-occasional',
  templateUrl: './vehicule-occasional.component.html'
})
export class VehiculeOccasionalComponent implements OnInit, OnDestroy {
  vehiculeOccasionals?: IVehiculeOccasional[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected vehiculeOccasionalService: VehiculeOccasionalService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.vehiculeOccasionalService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IVehiculeOccasional[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInVehiculeOccasionals();
  }

  protected handleNavigation(): void {}

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVehiculeOccasional): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVehiculeOccasionals(): void {
    this.eventSubscriber = this.eventManager.subscribe('vehiculeOccasionalListModification', () => this.loadPage());
  }

  delete(vehiculeOccasional: IVehiculeOccasional): void {
    const modalRef = this.modalService.open(VehiculeOccasionalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehiculeOccasional = vehiculeOccasional;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IVehiculeOccasional[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/vehicule-occasional'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
        }
      });
    }
    this.vehiculeOccasionals = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {}
}
