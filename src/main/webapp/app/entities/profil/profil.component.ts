import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfil } from 'app/shared/model/profil.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProfilService } from './profil.service';
import { ProfilDeleteDialogComponent } from './profil-delete-dialog.component';
import { IOrganisation } from 'app/shared/model/organisation.model';

@Component({
  selector: 'jhi-profil',
  templateUrl: './profil.component.html'
})
export class ProfilComponent implements OnInit, OnDestroy {
  profils: IProfil[];
  profilsCreatedBy: IProfil[];
  profilsList: IProfil[];
  profilsByMyOrganisation: IProfil[];
  profilsForMyRevendeurs: IProfil[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  totalItems1: any;
  totalItemsForMyRevendeurs: any;
  itemsPerPage: any;
  itemsPerPage1: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private jhiAlertService: JhiAlertService,
    protected profilService: ProfilService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.itemsPerPage1 = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.profilService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfil[]>) => this.paginateProfils(res.body, res.headers));

    this.profilService.queryCreatedBy().subscribe((res: HttpResponse<IProfil[]>) => (this.profilsCreatedBy = res.body));

    this.profilService
      .queryByMyOrganisation({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfil[]>) => this.paginateProfils1(res.body, res.headers));

    /*this.profilService
      .profilsByMyOrganisation()
      .subscribe((res: HttpResponse<IOrganisation[]>) => (this.profilsByMyOrganisation = res.body), (res: HttpErrorResponse) => this.onError());*/

    this.profilService
      .queryForMyRevendeurs({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IProfil[]>) => this.paginateProfilsForMyRevendeurs(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/profil'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'desc' : 'desc')
      }
    });
    this.loadAll();
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/profil',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInProfils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProfil) {
    return item.id;
  }

  registerChangeInProfils() {
    this.eventSubscriber = this.eventManager.subscribe('profilListModification', () => this.loadAll());
  }

  delete(profil: IProfil) {
    const modalRef = this.modalService.open(ProfilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.profil = profil;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProfils(data: IProfil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.profils = data;
  }

  protected paginateProfils1(data: IProfil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems1 = parseInt(headers.get('X-Total-Count'), 10);
    this.profilsByMyOrganisation = data;
  }

  protected paginateProfilsForMyRevendeurs(data: IProfil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItemsForMyRevendeurs = parseInt(headers.get('X-Total-Count'), 10);
    this.profilsForMyRevendeurs = data;
  }
}
