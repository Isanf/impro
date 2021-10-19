import { Component, OnInit, OnDestroy, Input, AfterViewInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { ImmatriculationService } from 'app/entities/immatriculation/immatriculation.service';
import { HttpResponse } from '@angular/common/http';
import { CarnetASoucheService } from 'app/entities/carnet-a-souche/carnet-a-souche.service';
import { IDatesModel } from 'app/shared/model/datesmodel';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { CertificatImmatriculationService } from 'app/entities/certificat-immatriculation/certificat-immatriculation.service';
import { CarteWService } from 'app/entities/carte-w/carte-w.service';
import { ICarteW } from 'app/shared/model/carte-w.model';
import { UserService } from 'app/core/user/user.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { DatePipe } from '@angular/common';
import { CollaborationService } from 'app/entities/collaboration/collaboration.service';
import { ICollaboration } from 'app/shared/model/collaboration.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  private alive = true;
  currentState = 'initial';
  let: number[];
  dat: string;
  dat1: string;
  dat2: string;
  dat3: string;
  dat4: string;
  dat5: string;
  tab: string[] = [];
  maDate: Date = new Date(Number(this.dat), 6, 1, 12, 34, 56);
  maDate2: Date = new Date(2020, 6, 30, 12, 34, 56);
  dateTest: IDatesModel;

  editForm = this.fb.group({
    dat: [],
    dat1: [],
    dat2: [],
    dat3: [],
    dat4: [],
    dat5: []
  });

  @Input() value: number;
  nbCarnet: number;
  totalCarnet: number;
  carteWS?: ICarteW[] = [];
  immatriculationList?: number;
  carnetASouchesTotal: number;
  carnetASouchesUsed: number;
  certificatImmatriculationsTotal: number;
  certificatImmatriculationsUsed: number;
  immatriculations?: any;
  revendeurs?: any;
  fromDate: string;
  toDate: string;
  private dateFormat: any;
  private now: any = new Date();
  checkAuth = true;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    protected immatriculationService: ImmatriculationService,
    private eventManager: JhiEventManager,
    private carnetSoucheService: CarnetASoucheService,
    private fb: FormBuilder,
    private certificatService: CertificatImmatriculationService,
    private carteWService: CarteWService,
    private userService: UserService,
    private router: Router,
    private datePipe: DatePipe,
    private collaborationService: CollaborationService,
    private stateStorageService: StateStorageService
  ) {}

  ngOnInit() {
    if (this.accountService.hasAnyAuthority(['ROLE_ADMIN'])) {
      this.carnetSoucheService.findTotalAdmin().subscribe(res => {
        this.carnetASouchesTotal = res;
      });
      this.carnetSoucheService.findTotalAdminUsed().subscribe(res => {
        this.carnetASouchesUsed = res;
      });
      this.carnetSoucheService.findTotalAdminAll().subscribe(res => {
        this.certificatImmatriculationsTotal = res;
        this.immatriculationService.findAllImmAdmin().subscribe(resp => {
          this.immatriculationList = resp;
          this.certificatImmatriculationsUsed = this.certificatImmatriculationsTotal - this.immatriculationList;
          this.certificatImmatriculationsUsed === undefined ? 0 : this.certificatImmatriculationsUsed;
        });
      });

      this.carteWService.query().subscribe((res: HttpResponse<ICarteW[]>) => {
        this.carteWS = res.body;
      });
    } else if (this.accountService.hasAnyAuthority(['CONCESSIONNAIRE'])) {
      this.carnetSoucheService.findTotal().subscribe(res => {
        this.carnetASouchesTotal = res;
      });
      this.carnetSoucheService.findUsed().subscribe(res => {
        this.carnetASouchesUsed = res;
      });

      this.collaborationService.queryByme().subscribe((res: HttpResponse<ICollaboration[]>) => {
        this.revendeurs = res.body.length;
      });
      this.carnetSoucheService.findCarnetCertif().subscribe(res => {
        this.certificatImmatriculationsTotal = res;
        this.immatriculationService.findAllImmOrganisation().subscribe(resp => {
          this.immatriculationList = resp;
          this.certificatImmatriculationsUsed = this.certificatImmatriculationsTotal - this.immatriculationList;
          this.certificatImmatriculationsUsed === undefined ? 0 : this.certificatImmatriculationsUsed;
        });
      });
    } else if (this.accountService.hasAnyAuthority(['STHGUICHET'])) {
      this.carnetSoucheService.findTotal().subscribe(res => {
        this.carnetASouchesTotal = res;
      });
      this.carnetSoucheService.findUsed().subscribe(res => {
        this.carnetASouchesUsed = res;
      });

      this.carnetSoucheService.findCarnetCertif().subscribe(res => {
        this.certificatImmatriculationsTotal = res;
        this.immatriculationService.findAllImmOrganisation().subscribe(resp => {
          this.immatriculationList = resp;
          this.certificatImmatriculationsUsed = this.certificatImmatriculationsTotal - this.immatriculationList;
          this.certificatImmatriculationsUsed === undefined ? 0 : this.certificatImmatriculationsUsed;
        });
      });
    }
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', () => {
      this.accountService.identity().subscribe(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.alive = false;
      this.eventManager.destroy(this.authSubscription);
    }
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
}
