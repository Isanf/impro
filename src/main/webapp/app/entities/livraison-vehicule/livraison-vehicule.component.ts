import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivraisonVehicule } from 'app/shared/model/livraison-vehicule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LivraisonVehiculeService } from './livraison-vehicule.service';
import { LivraisonVehiculeDeleteDialogComponent } from './livraison-vehicule-delete-dialog.component';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-livraison-vehicule',
  templateUrl: './livraison-vehicule.component.html'
})
export class LivraisonVehiculeComponent implements OnInit, OnDestroy {
  livraisonVehicules?: ILivraisonVehicule[];
  livraisonVehicules1?: ILivraisonVehicule[];
  organisationtout: IOrganisation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected livraisonVehiculeService: LivraisonVehiculeService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.livraisonVehiculeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILivraisonVehicule[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());

    this.livraisonVehiculeService
      .query1({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILivraisonVehicule[]>) => this.onSuccess1(res.body, res.headers, pageToLoad), () => this.onError());
    this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisationtout = res.body));
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInLivraisonVehicules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILivraisonVehicule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLivraisonVehicules(): void {
    this.eventSubscriber = this.eventManager.subscribe('livraisonVehiculeListModification', () => this.loadPage());
  }

  delete(livraisonVehicule: ILivraisonVehicule): void {
    const modalRef = this.modalService.open(LivraisonVehiculeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraisonVehicule = livraisonVehicule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILivraisonVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/livraison-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.livraisonVehicules = data || [];
  }

  protected onSuccess1(data: ILivraisonVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/livraison-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.livraisonVehicules1 = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
