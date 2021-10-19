import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CarnetASoucheService } from './carnet-a-souche.service';
import { CarnetASoucheDeleteDialogComponent } from './carnet-a-souche-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';

@Component({
  selector: 'jhi-carnet-a-souche',
  templateUrl: './carnet-a-souche.component.html'
})
export class CarnetASoucheComponent implements OnInit, OnDestroy {
  carnetASouches?: ICarnetASouche[];
  livraisonsCS?: ILivraisonCarnetSouche[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: any;

  constructor(
    protected carnetASoucheService: CarnetASoucheService,
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private accountService: AccountService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    if (this.accountService.hasAnyAuthority(['ROLE_ADMIN'])) {
      this.carnetASoucheService
        .queryAdmin({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICarnetASouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    } else {
      this.carnetASoucheService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICarnetASouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
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
    this.registerChangeInCarnetASouches();
    this.livraisonCarnetSoucheService.query().subscribe((res: HttpResponse<ILivraisonCarnetSouche[]>) => (this.livraisonsCS = res.body));
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICarnetASouche): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCarnetASouches(): void {
    this.eventSubscriber = this.eventManager.subscribe('carnetASoucheListModification', () => this.loadPage());
  }

  delete(carnetASouche: ICarnetASouche): void {
    const modalRef = this.modalService.open(CarnetASoucheDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carnetASouche = carnetASouche;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICarnetASouche[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/carnet-a-souche'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.carnetASouches = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
