import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CertificatImmatriculationService } from './certificat-immatriculation.service';
import { CertificatImmatriculationDeleteDialogComponent } from './certificat-immatriculation-delete-dialog.component';

@Component({
  selector: 'jhi-certificat-immatriculation',
  templateUrl: './certificat-immatriculation.component.html'
})
export class CertificatImmatriculationComponent implements OnInit, OnDestroy {
  certificatImmatriculations?: ICertificatImmatriculation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected certificatImmatriculationService: CertificatImmatriculationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.certificatImmatriculationService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICertificatImmatriculation[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInCertificatImmatriculations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICertificatImmatriculation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCertificatImmatriculations(): void {
    this.eventSubscriber = this.eventManager.subscribe('certificatImmatriculationListModification', () => this.loadPage());
  }

  delete(certificatImmatriculation: ICertificatImmatriculation): void {
    const modalRef = this.modalService.open(CertificatImmatriculationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.certificatImmatriculation = certificatImmatriculation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICertificatImmatriculation[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/certificat-immatriculation'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.certificatImmatriculations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
