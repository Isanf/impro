import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IImmatriculation } from 'app/shared/model/immatriculation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ImmatriculationService } from './immatriculation.service';
import { ImmatriculationDeleteDialogComponent } from './immatriculation-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-immatriculation',
  templateUrl: './immatriculation.component.html'
})
export class ImmatriculationComponent implements OnInit, OnDestroy {
  immatriculations?: IImmatriculation[];
  immatriculationsAdmin?: IImmatriculation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected immatriculationService: ImmatriculationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accoutService: AccountService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    if (this.accoutService.hasAnyAuthority('ROLE_ADMIN')) {
      this.immatriculationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IImmatriculation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    } else {
      this.immatriculationService
        .queryOrganisations({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IImmatriculation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInImmatriculations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IImmatriculation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInImmatriculations(): void {
    this.eventSubscriber = this.eventManager.subscribe('immatriculationListModification', () => this.loadPage());
  }

  delete(immatriculation: IImmatriculation): void {
    const modalRef = this.modalService.open(ImmatriculationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.immatriculation = immatriculation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IImmatriculation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/immatriculation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.immatriculations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
