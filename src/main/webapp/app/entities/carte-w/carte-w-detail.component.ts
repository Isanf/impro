import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

import { ICarteW } from 'app/shared/model/carte-w.model';
import { NbGlobalPhysicalPosition, NbToastrService } from '@nebular/theme';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { ITypeActeur } from 'app/shared/model/type-acteur.model';
import { TypeActeurService } from 'app/entities/type-acteur/type-acteur.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { PlaqueGarageService } from 'app/entities/plaque-garage/plaque-garage.service';
import { IPlaqueGarage } from 'app/shared/model/plaque-garage.model';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-carte-w-detail',
  templateUrl: './carte-w-detail.component.html'
})
export class CarteWDetailComponent implements OnInit, OnDestroy {
  carteW: ICarteW | null = null;
  typeActeur: ITypeActeur | null = null;
  loading = false;
  plaqueGarages?: IPlaqueGarage[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private toastrService: NbToastrService,
    private typeActeurService: TypeActeurService,
    private plqaueGarageService: PlaqueGarageService,
    private service: CarteWService,
    protected eventManager: JhiEventManager,
    private router: Router
  ) {}
  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.plqaueGarageService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IPlaqueGarage[]>) => this.onSuccess(res.body, res.headers, pageToLoad), () => this.onError());
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carteW }) => {
      this.carteW = carteW;
      const pageToLoad: number = this.page;
      this.plqaueGarageService.queryByGarage(carteW.id).subscribe((res: HttpResponse<IPlaqueGarage[]>) => {
        this.plaqueGarages = res.body;
      });
    });
    this.handleBackNavigation();
    this.registerChangeInPlaqueGarages();

    this.typeActeurService.find(this.carteW.organisationDTO.typeActeurId).subscribe((ta: HttpResponse<ITypeActeur>) => {
      this.typeActeur = ta.body;
    });
  }

  toggleLoadingAnimation(id: number) {
    this.loading = true;
    this.service.findPrint(id).subscribe(
      (blob: Blob) => {
        this.toastrService.show('Carte W imprimé', 'Success', {
          duplicatesBehaviour: undefined,
          duration: 3000,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          preventDuplicates: false,
          status: 'success'
        });
        this.loading = false;
        this.router.navigate(['carte-w']);
      },
      error => {
        this.loading = false;
      }
    );
  }
  handleBackNavigation(): void {
    this.activatedRoute.queryParamMap.subscribe((params: ParamMap) => {
      const prevPage = params.get('page');
      const prevSort = params.get('sort');
      const prevSortSplit = prevSort.split(',');
      if (prevSortSplit) {
        this.predicate = prevSortSplit[0];
        this.ascending = prevSortSplit[1] === 'asc';
      }
      if (prevPage && +prevPage !== this.page) {
        this.ngbPaginationPage = +prevPage;
        this.loadPage(+prevPage);
      } else {
        this.loadPage(this.page);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlaqueGarage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlaqueGarages(): void {
    this.eventSubscriber = this.eventManager.subscribe('plaqueGarageListModification', () => this.loadPage());
  }

  previousState(): void {
    window.history.back();
  }
  protected onSuccess(data: IPlaqueGarage[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['//carte-w/1451/view'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.plaqueGarages = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }
}
