import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommandeVehiculeService } from './commande-vehicule.service';
import { CommandeVehiculeDeleteDialogComponent } from './commande-vehicule-delete-dialog.component';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-commande-vehicule',
  templateUrl: './commande-vehicule.component.html'
})
export class CommandeVehiculeComponent implements OnInit, OnDestroy {
  commandeVehicules?: ICommandeVehicule[];
  commandeVehicules1?: ICommandeVehicule[];
  organisationtout: IOrganisation[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected commandeVehiculeService: CommandeVehiculeService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.commandeVehiculeService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICommandeVehicule[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());

    this.commandeVehiculeService
      .query1({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICommandeVehicule[]>) => this.onSuccess1(res.body, res.headers, pageToLoad), () => this.onError());

    this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisationtout = res.body));
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInCommandeVehicules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommandeVehicule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommandeVehicules(): void {
    this.eventSubscriber = this.eventManager.subscribe('commandeVehiculeListModification', () => this.loadPage());
  }

  delete(commandeVehicule: ICommandeVehicule): void {
    const modalRef = this.modalService.open(CommandeVehiculeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commandeVehicule = commandeVehicule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'desc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICommandeVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/commande-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.commandeVehicules = data || [];
  }

  protected onSuccess1(data: ICommandeVehicule[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/commande-vehicule'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.commandeVehicules1 = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
