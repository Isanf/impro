import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPosePlaque } from 'app/shared/model/pose-plaque.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PosePlaqueService } from './pose-plaque.service';
import { PosePlaqueDeleteDialogComponent } from './pose-plaque-delete-dialog.component';

@Component({
  selector: 'jhi-pose-plaque',
  templateUrl: './pose-plaque.component.html'
})
export class PosePlaqueComponent implements OnInit, OnDestroy {
  posePlaques: IPosePlaque[];
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

  constructor(
    protected posePlaqueService: PosePlaqueService,
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
    this.posePlaqueService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPosePlaque[]>) => this.paginatePosePlaques(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/pose-plaque'], {
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
      '/pose-plaque',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPosePlaques();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPosePlaque) {
    return item.id;
  }

  registerChangeInPosePlaques() {
    this.eventSubscriber = this.eventManager.subscribe('posePlaqueListModification', () => this.loadAll());
  }

  delete(posePlaque: IPosePlaque) {
    const modalRef = this.modalService.open(PosePlaqueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.posePlaque = posePlaque;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePosePlaques(data: IPosePlaque[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.posePlaques = data;
  }
}
