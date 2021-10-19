import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInfoCommandeVehicule } from 'app/shared/model/info-commande-vehicule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InfoCommandeVehiculeService } from './info-commande-vehicule.service';
import { InfoCommandeVehiculeDeleteDialogComponent } from './info-commande-vehicule-delete-dialog.component';

@Component({
  selector: 'jhi-info-commande-vehicule',
  templateUrl: './info-commande-vehicule.component.html'
})
export class InfoCommandeVehiculeComponent implements OnInit, OnDestroy {
  infoCommandeVehicules?: IInfoCommandeVehicule[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected infoCommandeVehiculeService: InfoCommandeVehiculeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.infoCommandeVehiculeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInfoCommandeVehicule[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInInfoCommandeVehicules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInfoCommandeVehicule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInfoCommandeVehicules(): void {
    this.eventSubscriber = this.eventManager.subscribe('infoCommandeVehiculeListModification', () => this.loadPage());
  }

  delete(infoCommandeVehicule: IInfoCommandeVehicule): void {
    const modalRef = this.modalService.open(InfoCommandeVehiculeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.infoCommandeVehicule = infoCommandeVehicule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInfoCommandeVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/info-commande-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.infoCommandeVehicules = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
