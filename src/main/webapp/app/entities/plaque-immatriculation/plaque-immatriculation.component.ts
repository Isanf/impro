import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PlaqueImmatriculationService } from './plaque-immatriculation.service';
import { PlaqueImmatriculationDeleteDialogComponent } from './plaque-immatriculation-delete-dialog.component';

@Component({
  selector: 'jhi-plaque-immatriculation',
  templateUrl: './plaque-immatriculation.component.html'
})
export class PlaqueImmatriculationComponent implements OnInit, OnDestroy {
  plaqueImmatriculations?: IPlaqueImmatriculation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected plaqueImmatriculationService: PlaqueImmatriculationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.plaqueImmatriculationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPlaqueImmatriculation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInPlaqueImmatriculations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlaqueImmatriculation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlaqueImmatriculations(): void {
    this.eventSubscriber = this.eventManager.subscribe('plaqueImmatriculationListModification', () => this.loadPage());
  }

  delete(plaqueImmatriculation: IPlaqueImmatriculation): void {
    const modalRef = this.modalService.open(PlaqueImmatriculationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.plaqueImmatriculation = plaqueImmatriculation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPlaqueImmatriculation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/plaque-immatriculation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.plaqueImmatriculations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
