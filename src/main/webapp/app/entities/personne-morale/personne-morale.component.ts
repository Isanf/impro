import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonneMorale } from 'app/shared/model/personne-morale.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PersonneMoraleService } from './personne-morale.service';
import { PersonneMoraleDeleteDialogComponent } from './personne-morale-delete-dialog.component';

@Component({
  selector: 'jhi-personne-morale',
  templateUrl: './personne-morale.component.html'
})
export class PersonneMoraleComponent implements OnInit, OnDestroy {
  personneMorales: IPersonneMorale[];
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
    protected personneMoraleService: PersonneMoraleService,
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
    this.personneMoraleService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPersonneMorale[]>) => this.paginatePersonneMorales(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/personne-morale'], {
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
      '/personne-morale',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInPersonneMorales();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonneMorale) {
    return item.id;
  }

  registerChangeInPersonneMorales() {
    this.eventSubscriber = this.eventManager.subscribe('personneMoraleListModification', () => this.loadAll());
  }

  delete(personneMorale: IPersonneMorale) {
    const modalRef = this.modalService.open(PersonneMoraleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.personneMorale = personneMorale;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePersonneMorales(data: IPersonneMorale[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.personneMorales = data;
  }
}
