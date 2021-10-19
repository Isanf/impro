import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InfoCommandeCarnetASoucheService } from './info-commande-carnet-a-souche.service';
import { InfoCommandeCarnetASoucheDeleteDialogComponent } from './info-commande-carnet-a-souche-delete-dialog.component';

@Component({
  selector: 'jhi-info-commande-carnet-a-souche',
  templateUrl: './info-commande-carnet-a-souche.component.html'
})
export class InfoCommandeCarnetASoucheComponent implements OnInit, OnDestroy {
  infoCommandeCarnetASouches?: IInfoCommandeCarnetASouche[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected infoCommandeCarnetASoucheService: InfoCommandeCarnetASoucheService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.infoCommandeCarnetASoucheService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInfoCommandeCarnetASouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInInfoCommandeCarnetASouches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInfoCommandeCarnetASouche): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInfoCommandeCarnetASouches(): void {
    this.eventSubscriber = this.eventManager.subscribe('infoCommandeCarnetASoucheListModification', () => this.loadPage());
  }

  delete(infoCommandeCarnetASouche: IInfoCommandeCarnetASouche): void {
    const modalRef = this.modalService.open(InfoCommandeCarnetASoucheDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.infoCommandeCarnetASouche = infoCommandeCarnetASouche;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInfoCommandeCarnetASouche[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/info-commande-carnet-a-souche'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.infoCommandeCarnetASouches = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
