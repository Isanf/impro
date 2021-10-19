import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeOrganisation } from 'app/shared/model/type-organisation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeOrganisationService } from './type-organisation.service';
import { TypeOrganisationDeleteDialogComponent } from './type-organisation-delete-dialog.component';

@Component({
  selector: 'jhi-type-organisation',
  templateUrl: './type-organisation.component.html'
})
export class TypeOrganisationComponent implements OnInit, OnDestroy {
  typeOrganisations?: ITypeOrganisation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected typeOrganisationService: TypeOrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.typeOrganisationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeOrganisation[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInTypeOrganisations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeOrganisation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeOrganisations(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeOrganisationListModification', () => this.loadPage());
  }

  delete(typeOrganisation: ITypeOrganisation): void {
    const modalRef = this.modalService.open(TypeOrganisationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeOrganisation = typeOrganisation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITypeOrganisation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/type-organisation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.typeOrganisations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
