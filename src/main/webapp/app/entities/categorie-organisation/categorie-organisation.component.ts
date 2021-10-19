import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieOrganisation } from 'app/shared/model/categorie-organisation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CategorieOrganisationService } from './categorie-organisation.service';
import { CategorieOrganisationDeleteDialogComponent } from './categorie-organisation-delete-dialog.component';

@Component({
  selector: 'jhi-categorie-organisation',
  templateUrl: './categorie-organisation.component.html'
})
export class CategorieOrganisationComponent implements OnInit, OnDestroy {
  categorieOrganisations?: ICategorieOrganisation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected categorieOrganisationService: CategorieOrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.categorieOrganisationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICategorieOrganisation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInCategorieOrganisations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategorieOrganisation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategorieOrganisations(): void {
    this.eventSubscriber = this.eventManager.subscribe('categorieOrganisationListModification', () => this.loadPage());
  }

  delete(categorieOrganisation: ICategorieOrganisation): void {
    const modalRef = this.modalService.open(CategorieOrganisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categorieOrganisation = categorieOrganisation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICategorieOrganisation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/categorie-organisation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.categorieOrganisations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
