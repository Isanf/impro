import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMarqueVehicule } from 'app/shared/model/marque-vehicule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MarqueVehiculeService } from './marque-vehicule.service';
import { MarqueVehiculeDeleteDialogComponent } from './marque-vehicule-delete-dialog.component';

@Component({
  selector: 'jhi-marque-vehicule',
  templateUrl: './marque-vehicule.component.html'
})
export class MarqueVehiculeComponent implements OnInit, OnDestroy {
  marqueVehicules?: IMarqueVehicule[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected marqueVehiculeService: MarqueVehiculeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.marqueVehiculeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMarqueVehicule[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInMarqueVehicules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMarqueVehicule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMarqueVehicules(): void {
    this.eventSubscriber = this.eventManager.subscribe('marqueVehiculeListModification', () => this.loadPage());
  }

  delete(marqueVehicule: IMarqueVehicule): void {
    const modalRef = this.modalService.open(MarqueVehiculeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.marqueVehicule = marqueVehicule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IMarqueVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/marque-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.marqueVehicules = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
