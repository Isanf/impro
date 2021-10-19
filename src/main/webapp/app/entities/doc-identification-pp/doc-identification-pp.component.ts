import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DocIdentificationPPService } from './doc-identification-pp.service';
import { DocIdentificationPPDeleteDialogComponent } from './doc-identification-pp-delete-dialog.component';

@Component({
  selector: 'jhi-doc-identification-pp',
  templateUrl: './doc-identification-pp.component.html'
})
export class DocIdentificationPPComponent implements OnInit, OnDestroy {
  docIdentificationPPS: IDocIdentificationPP[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  ngbPaginationPage: any;

  constructor(
    protected docIdentificationPPService: DocIdentificationPPService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.docIdentificationPPService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDocIdentificationPP[]>) => this.paginateDocIdentificationPPS(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/doc-identification-pp'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/doc-identification-pp',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDocIdentificationPPS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDocIdentificationPP) {
    return item.id;
  }

  registerChangeInDocIdentificationPPS() {
    this.eventSubscriber = this.eventManager.subscribe('docIdentificationPPListModification', () => this.loadAll());
  }

  delete(docIdentificationPP: IDocIdentificationPP) {
    const modalRef = this.modalService.open(DocIdentificationPPDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.docIdentificationPP = docIdentificationPP;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDocIdentificationPPS(data: IDocIdentificationPP[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.docIdentificationPPS = data;
  }
}
