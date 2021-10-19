import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommandeCarnetSoucheService } from './commande-carnet-souche.service';
import { CommandeCarnetSoucheDeleteDialogComponent } from './commande-carnet-souche-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { NbTreeGridDataSource } from '@nebular/theme';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';
import { ILivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

@Component({
  selector: 'jhi-commande-carnet-souche',
  templateUrl: './commande-carnet-souche.component.html'
})
export class CommandeCarnetSoucheComponent implements OnInit, OnDestroy {
  commandeCarnetSouches?: ICommandeCarnetSouche[];
  livraisonsCS?: ILivraisonCarnetSouche[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  dataSource: NbTreeGridDataSource<ICommandeCarnetSouche>;
  searchText: string;

  constructor(
    protected commandeCarnetSoucheService: CommandeCarnetSoucheService,
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private accountService: AccountService,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;
    if (this.accountService.hasAnyAuthority(['ROLE_ADMIN'])) {
      this.commandeCarnetSoucheService
        .queryAll({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICommandeCarnetSouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
    } else {
      this.commandeCarnetSoucheService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICommandeCarnetSouche[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
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
    this.registerChangeInCommandeCarnetSouches();

    this.livraisonCarnetSoucheService.query().subscribe((res: HttpResponse<ILivraisonCarnetSouche[]>) => (this.livraisonsCS = res.body));
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommandeCarnetSouche): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommandeCarnetSouches(): void {
    this.eventSubscriber = this.eventManager.subscribe('commandeCarnetSoucheListModification', () => this.loadPage());
  }

  delete(commandeCarnetSouche: ICommandeCarnetSouche): void {
    const modalRef = this.modalService.open(CommandeCarnetSoucheDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeCarnetSouche = commandeCarnetSouche;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICommandeCarnetSouche[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/commande-carnet-souche'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.commandeCarnetSouches = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
