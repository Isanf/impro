import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganisationLocalite } from 'app/shared/model/organisation-localite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { OrganisationLocaliteService } from './organisation-localite.service';
import { OrganisationLocaliteDeleteDialogComponent } from './organisation-localite-delete-dialog.component';

@Component({
  selector: 'jhi-organisation-localite',
  templateUrl: './organisation-localite.component.html'
})
export class OrganisationLocaliteComponent implements OnInit, OnDestroy {
  organisationLocalites?: IOrganisationLocalite[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected organisationLocaliteService: OrganisationLocaliteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.organisationLocaliteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IOrganisationLocalite[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInOrganisationLocalites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrganisationLocalite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrganisationLocalites(): void {
    this.eventSubscriber = this.eventManager.subscribe('organisationLocaliteListModification', () => this.loadPage());
  }

  delete(organisationLocalite: IOrganisationLocalite): void {
    const modalRef = this.modalService.open(OrganisationLocaliteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.organisationLocalite = organisationLocalite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IOrganisationLocalite[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/organisation-localite'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.organisationLocalites = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
