import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LivraisonCarnetSoucheService } from './livraison-carnet-souche.service';
import { LivraisonCarnetSoucheDeleteDialogComponent } from './livraison-carnet-souche-delete-dialog.component';

@Component({
  selector: 'jhi-livraison-carnet-souche',
  templateUrl: './livraison-carnet-souche.component.html'
})
export class LivraisonCarnetSoucheComponent implements OnInit, OnDestroy {
  livraisonCarnetSouches?: ILivraisonCarnetSouche[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.livraisonCarnetSoucheService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILivraisonCarnetSouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInLivraisonCarnetSouches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILivraisonCarnetSouche): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLivraisonCarnetSouches(): void {
    this.eventSubscriber = this.eventManager.subscribe('livraisonCarnetSoucheListModification', () => this.loadPage());
  }

  delete(livraisonCarnetSouche: ILivraisonCarnetSouche): void {
    const modalRef = this.modalService.open(LivraisonCarnetSoucheDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraisonCarnetSouche = livraisonCarnetSouche;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILivraisonCarnetSouche[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/livraison-carnet-souche'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.livraisonCarnetSouches = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
