import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Collaboration, ICollaboration } from 'app/shared/model/collaboration.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CollaborationService } from './collaboration.service';
import { CollaborationDeleteDialogComponent } from './collaboration-delete-dialog.component';
import { CollaborationUpdateComponent } from 'app/entities/collaboration/collaboration-update.component';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-collaboration',
  templateUrl: './collaboration.component.html'
})
export class CollaborationComponent implements OnInit, OnDestroy {
  collaborations?: ICollaboration[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected collaborationService: CollaborationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private account: AccountService
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    if (this.account.hasAnyAuthority(['ROLE_ADMIN'])) {
      this.collaborationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICollaboration[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    } else {
      this.collaborationService
        .queryByme({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICollaboration[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInCollaborations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICollaboration): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCollaborations(): void {
    this.eventSubscriber = this.eventManager.subscribe('collaborationListModification', () => this.loadPage());
  }

  delete(collaboration: ICollaboration): void {
    const modalRef = this.modalService.open(CollaborationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.collaboration = collaboration;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICollaboration[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/collaboration'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.collaborations = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
  open() {
    const modalRef = this.modalService.open(CollaborationUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.collaboration = new Collaboration();
  }
}
