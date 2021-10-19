import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeActeur, TypeActeur } from 'app/shared/model/type-acteur.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TypeActeurService } from './type-acteur.service';
import { TypeActeurDeleteDialogComponent } from './type-acteur-delete-dialog.component';
import { TypeActeurUpdateComponent } from 'app/entities/type-acteur/type-acteur-update.component';

@Component({
  selector: 'jhi-type-acteur',
  templateUrl: './type-acteur.component.html'
})
export class TypeActeurComponent implements OnInit, OnDestroy {
  typeActeurs?: ITypeActeur[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  searchText: string;

  constructor(
    protected typeActeurService: TypeActeurService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.typeActeurService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITypeActeur[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInTypeActeurs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypeActeur): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypeActeurs(): void {
    this.eventSubscriber = this.eventManager.subscribe('typeActeurListModification', () => this.loadPage());
  }

  delete(typeActeur: ITypeActeur): void {
    const modalRef = this.modalService.open(TypeActeurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeActeur = typeActeur;
  }

  open() {
    const modalRef = this.modalService.open(TypeActeurUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typeActeur = new TypeActeur();
  }

  edit(typeActeur: ITypeActeur) {
    const modalRef = this.modalService.open(TypeActeurUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.updateForm(typeActeur);
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITypeActeur[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/type-acteur'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.typeActeurs = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
