import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFirstlogin } from 'app/shared/model/firstlogin.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FirstloginService } from './firstlogin.service';
import { FirstloginDeleteDialogComponent } from './firstlogin-delete-dialog.component';
import { LoginService } from 'app/core/login/login.service';

@Component({
  selector: 'jhi-firstlogin',
  templateUrl: './firstlogin.component.html'
})
export class FirstloginComponent implements OnInit, OnDestroy {
  firstlogins?: IFirstlogin[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  first: string;

  constructor(
    protected firstloginService: FirstloginService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private loginService: LoginService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.firstloginService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFirstlogin[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInFirstlogins();

    this.firstloginService.firstlogin().subscribe(val => {
      this.first = val;
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFirstlogin): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFirstlogins(): void {
    this.eventSubscriber = this.eventManager.subscribe('firstloginListModification', () => this.loadPage());
  }

  delete(firstlogin: IFirstlogin): void {
    const modalRef = this.modalService.open(FirstloginDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.firstlogin = firstlogin;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IFirstlogin[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/firstlogin'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.firstlogins = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
