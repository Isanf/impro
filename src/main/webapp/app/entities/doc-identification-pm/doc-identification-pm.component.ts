import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DocIdentificationPMService } from './doc-identification-pm.service';
import { DocIdentificationPMDeleteDialogComponent } from './doc-identification-pm-delete-dialog.component';

@Component({
  selector: 'jhi-doc-identification-pm',
  templateUrl: './doc-identification-pm.component.html'
})
export class DocIdentificationPMComponent implements OnInit, OnDestroy {
  docIdentificationPMS?: IDocIdentificationPM[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected docIdentificationPMService: DocIdentificationPMService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.docIdentificationPMService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDocIdentificationPM[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInDocIdentificationPMS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocIdentificationPM): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDocIdentificationPMS(): void {
    this.eventSubscriber = this.eventManager.subscribe('docIdentificationPMListModification', () => this.loadPage());
  }

  delete(docIdentificationPM: IDocIdentificationPM): void {
    const modalRef = this.modalService.open(DocIdentificationPMDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.docIdentificationPM = docIdentificationPM;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDocIdentificationPM[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/doc-identification-pm'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.docIdentificationPMS = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
