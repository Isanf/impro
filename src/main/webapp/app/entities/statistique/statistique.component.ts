import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatistique } from 'app/shared/model/statistique.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

import { Color, Label } from 'ng2-charts';
import { FormBuilder } from '@angular/forms';
import { ImmatriculationService } from 'app/entities/immatriculation/immatriculation.service';
import { DatePipe } from '@angular/common';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';
import { CarnetASoucheService } from 'app/entities/carnet-a-souche/carnet-a-souche.service';
import { Router } from '@angular/router';
import { Audit } from 'app/admin/audits/audit.model';

@Component({
  selector: 'jhi-statistique',
  templateUrl: './statistique.component.html'
})
export class StatistiqueComponent implements OnInit, OnDestroy {
  statistiques: IStatistique[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  dat: string;
  dat1: string;
  dat2: string;
  dat3: string;
  dat4: string;
  dat5: string;
  idOrg: number;
  tab: string[] = [];
  immatriculations?: any;
  fromDate: string;
  toDate: string;
  fromDat: string;
  toDat: string;
  fromDate4Im: string;
  toDate4Im: string;
  fromDate4AllOrg: string;
  toDate4AllOrg: string;
  data;
  data4Im;
  private dateFormat: any;
  private now: any = new Date();

  organisations: IOrganisation[];
  organisationsRev: IOrganisation[];
  certificatImmatriculationsTotal: number;
  CarnetTotal: number;
  certificatImmatriculationsDipo: number;
  reverse: boolean;

  lineData: any[] = [];
  lineData4Im: any[] = [];
  totIm4Org: number;
  totLiv4Org: number;
  lineChartLabels: Label[] = [];
  lineChartLabels4Im: Label[] = [];
  lineChartColors: Color[] = [
    {
      borderColor: 'green'
    }
  ];

  editForm4Org = this.fb.group({
    idOrg: []
  });

  constructor(
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    protected immatriculationService: ImmatriculationService,
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected parseLinks: JhiParseLinks,
    private organisationService: OrganisationService,
    private carnetSoucheService: CarnetASoucheService,
    private router: Router
  ) {
    this.statistiques = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {}

  reset(): void {
    this.page = 0;
    this.statistiques = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.dateFormat = require('dateformat');
    this.dateFormat(this.now, 'dddd, mmmm dS, yyyy, h:MM:ss TT');
    this.dat = this.now.getFullYear().toString();
    this.dat1 = String(this.now.getMonth() + 1);
    this.dat2 = '1';
    this.dat3 = this.now.getFullYear().toString();
    this.dat4 = String(this.now.getMonth() + 2);
    this.dat5 = '1';

    this.previousMonth();
    this.today();
    let i = 0;
    for (let x = 2020; x < 3000; x++) {
      this.tab[i] = String(x);
      i++;
    }
    this.loadAll();

    this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body));

    this.organisationService
      .queryRevendeurCollaborant()
      .subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisationsRev = res.body));
  }

  previousMonth() {
    const dateFormat = 'yyyy-MM-dd';
    let fromDate: Date = new Date();

    if (fromDate.getMonth() === 0) {
      fromDate = new Date(fromDate.getFullYear() - 1, 11, fromDate.getDate());
    } else {
      fromDate = new Date(fromDate.getFullYear(), fromDate.getMonth() - 1, fromDate.getDate());
    }

    this.fromDate = this.datePipe.transform(fromDate, dateFormat);
  }

  today() {
    const dateFormat = 'yyyy-MM-dd';
    // Today + 1 day - needed if the current day must be included
    const today: Date = new Date();
    today.setDate(today.getDate() + 1);
    const date = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    this.toDate = this.datePipe.transform(date, dateFormat);
  }

  update4AllOrg(event: Event) {
    this.immatriculationService
      .ImPeriode4AllOrg({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDate4AllOrg,
        toDate: this.toDate4AllOrg
      })
      .subscribe(value => {
        this.lineData = value;
        const from = new Date(this.fromDate4AllOrg);
        const to = new Date(this.toDate4AllOrg);
        let i = 0;
        for (const day = from; day <= to; day.setDate(day.getDate() + 1)) {
          this.lineChartLabels[i] = day.toDateString();
          i = i + 1;
        }
        this.data = {
          labels: this.lineChartLabels,
          datasets: [
            {
              label: "Nombre d'immatriculation",
              data: this.lineData
            }
          ]
        };
      });
  }

  update4Org(event: Event) {
    this.idOrg = this.editForm4Org.get(['idOrg'])!.value;
    this.immatriculationService
      .find4Org({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDat,
        toDate: this.toDat,
        idOrg: this.idOrg
      })
      .subscribe(val => {
        this.totIm4Org = val;
      });
    this.carnetSoucheService
      .findCarnetCertif4Org({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDat,
        toDate: this.toDat,
        idOrg: this.idOrg
      })
      .subscribe(res => {
        this.certificatImmatriculationsTotal = res;
        this.certificatImmatriculationsDipo = this.certificatImmatriculationsTotal - this.totIm4Org;
        this.certificatImmatriculationsDipo === undefined ? 0 : this.certificatImmatriculationsDipo;
      });
    this.carnetSoucheService
      .findCarnet4Org({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDat,
        toDate: this.toDat,
        idOrg: this.idOrg
      })
      .subscribe(res => {
        this.CarnetTotal = res;
      });
    this.livraisonCarnetSoucheService
      .find4Org({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDat,
        toDate: this.toDat,
        idOrg: this.idOrg
      })
      .subscribe(valLiv => {
        this.totLiv4Org = valLiv;
      });
  }

  queryIm(event: Event) {
    this.immatriculationService
      .queryIm({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        fromDate: this.fromDate4Im,
        toDate: this.toDate4Im
      })
      .subscribe(value => {
        this.lineData4Im = value;
        const from = new Date(this.fromDate4Im);
        const to = new Date(this.toDate4Im);
        let i = 0;
        for (const day = from; day <= to; day.setDate(day.getDate() + 1)) {
          this.lineChartLabels4Im[i] = day.toDateString();
          i = i + 1;
        }
        this.data4Im = {
          labels: this.lineChartLabels4Im,
          datasets: [
            {
              label: "Nombre d'immatriculation",
              data: this.lineData4Im
            }
          ]
        };
      });
  }

  ngOnDestroy(): void {}

  sort() {}

  transition() {
    this.router.navigate(['/admin/audits'], {
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }
}
