import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';

import { IPlaqueImmatriculation, PlaqueImmatriculation } from 'app/shared/model/plaque-immatriculation.model';
import { PlaqueImmatriculationService } from './plaque-immatriculation.service';
import { CertificatImmatriculation, ICertificatImmatriculation } from 'app/shared/model/certificat-immatriculation.model';
import { CertificatImmatriculationService } from 'app/entities/certificat-immatriculation/certificat-immatriculation.service';
import { IVehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';
import { NbGlobalPhysicalPosition, NbToastrService } from '@nebular/theme';
import { AccountService } from 'app/core/auth/account.service';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';

type SelectableEntity = ICertificatImmatriculation | IVehicule;

@Component({
  selector: 'jhi-plaque-immatriculation-update',
  templateUrl: './plaque-immatriculation-update.component.html'
})
export class PlaqueImmatriculationUpdateComponent implements OnInit {
  isSaving = false;
  certificatimmatriculations: ICertificatImmatriculation[] = [];
  certificatImmatriculation: ICertificatImmatriculation | null = null;
  vehiculesR: IVehicule[] = [];
  vehiculesC: IVehicule[] = [];
  typeVehicules: ITypeVehicule[] = [];
  typeVehicule: ITypeVehicule;
  certiId: number;
  idd: any;
  nim: any;
  nser: any;
  index: any = 0;
  @ViewChild('numero', { static: false }) numb;
  @ViewChild('numeroG', { static: false }) numbg;
  @ViewChild('imma', { static: false }) imm;
  @ViewChild('immaG', { static: false }) immg;
  @ViewChild('certiId', { static: false }) cert;
  @ViewChild('certiIdG', { static: false }) certg;
  @ViewChild('id', { static: false }) id;
  @ViewChild('idG', { static: false }) idg;

  editForm = this.fb.group({
    id: [],
    numeroSerie: [],
    numeroImmatriculation: [],
    certificatImmatriculationId: [],
    vehiculeId: [],
    telephone: [],
    otp: []
  });
  searchText: string;
  checked = false;
  checked2 = false;
  idTyp: number;

  constructor(
    protected plaqueImmatriculationService: PlaqueImmatriculationService,
    protected certificatImmatriculationService: CertificatImmatriculationService,
    protected vehiculeService: VehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
    private accountService: AccountService,
    private toastrService: NbToastrService,
    private typeVehiculeService: TypeVehiculeService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plaqueImmatriculation }) => {
      this.updateForm(plaqueImmatriculation);

      this.certificatImmatriculationService
        .query()
        .subscribe((res: HttpResponse<ICertificatImmatriculation[]>) => (this.certificatimmatriculations = res.body || []));

      if (this.accountService.hasAnyAuthority('REVENDEUR')) {
        this.vehiculeService.queryVehiculeRevendeur().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehiculesC = res.body || []));
      } else {
        this.vehiculeService
          .queryVehiculeConcessionnaire()
          .subscribe((res: HttpResponse<IVehicule[]>) => (this.vehiculesC = res.body || []));
      }

      if (this.id.nativeElement.value === undefined) {
        this.cert.nativeElement.disabled = true;
      }
    });

    this.typeVehiculeService.query().subscribe((res: HttpResponse<ITypeVehicule[]>) => (this.typeVehicules = res.body || []));
  }

  updateForm(plaqueImmatriculation: IPlaqueImmatriculation): void {
    this.editForm.patchValue({
      id: plaqueImmatriculation.id,
      numeroSerie: plaqueImmatriculation.numeroSerie,
      numeroImmatriculation: plaqueImmatriculation.numeroImmatriculation,
      certificatImmatriculationId: plaqueImmatriculation.certificatImmatriculationId,
      vehiculeId: plaqueImmatriculation.vehiculeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plaqueImmatriculation = this.createFromForm();
    if (plaqueImmatriculation.id !== undefined) {
      this.subscribeToSaveResponse(this.plaqueImmatriculationService.update(plaqueImmatriculation));
    } else {
      this.subscribeToSaveResponse(this.plaqueImmatriculationService.create(plaqueImmatriculation));
    }
  }

  private createFromForm(): IPlaqueImmatriculation {
    return {
      ...new PlaqueImmatriculation(),
      id: this.idd,
      numeroSerie: this.nser,
      numeroImmatriculation: this.nim,
      certificatImmatriculationId: this.certiId,
      vehiculeId: this.editForm.get(['vehiculeId'])!.value
    };
  }

  private createFromFormGuichet(): IPlaqueImmatriculation {
    return {
      ...new PlaqueImmatriculation(),
      id: this.idd,
      numeroSerie: this.nser,
      numeroImmatriculation: this.nim,
      certificatImmatriculationId: this.certiId,
      vehiculeId: this.editForm.get(['vehiculeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaqueImmatriculation>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    //this.previousState();
    this.router.navigate(['immatriculation']);
    this.editForm.get(['numeroSerie']).reset();
    this.editForm.get(['numeroImmatriculation']).reset();
    this.editForm.get(['certificatImmatriculationId']).reset();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  onChange() {
    if (this.accountService.hasAnyAuthority(['CONCESSIONNAIRE', 'REVENDEUR'])) {
      this.plaqueImmatriculationService.checkqr(this.editForm.get(['numeroSerie'])!.value).subscribe(
        (rsp: HttpResponse<any>) => {
          if (rsp.body !== null) {
            this.plaqueImmatriculationService
              .queryQr(this.editForm.get(['numeroSerie'])!.value)
              .subscribe((res: HttpResponse<IPlaqueImmatriculation>) => {
                if (res.body) {
                  if (res.body.vehiculeId === null) {
                    this.numb.nativeElement.value = res.body.numeroSerie;
                    this.imm.nativeElement.value = res.body.numeroImmatriculation;
                    this.id.nativeElement.value = res.body.id;
                    this.idd = res.body.id;
                    this.nim = res.body.numeroImmatriculation;
                    this.nser = res.body.numeroSerie;
                  } else {
                    this.toastrService.show('La bande a déjà été utilisé', 'Attention', {
                      duplicatesBehaviour: undefined,
                      duration: 5000,
                      position: NbGlobalPhysicalPosition.TOP_RIGHT,
                      preventDuplicates: false,
                      status: 'danger'
                    });
                    this.editForm.get(['numeroSerie']).reset();
                    this.editForm.get(['numeroImmatriculation']).reset();
                    this.editForm.get(['certificatImmatriculationId']).reset();
                  }
                }
              });
          } else {
            this.toastrService.show("Cette bande n'est pas reconnue", 'Attention', {
              duration: 5000,
              limit: 0,
              position: NbGlobalPhysicalPosition.TOP_RIGHT,
              preventDuplicates: false,
              status: 'danger'
            });
            this.editForm.get(['numeroSerie']).reset();
            this.editForm.get(['numeroImmatriculation']).reset();
            this.editForm.get(['certificatImmatriculationId']).reset();
          }
        },
        error => {
          this.toastrService.show(error, 'Attention', {
            duration: 5000,
            position: NbGlobalPhysicalPosition.TOP_RIGHT,
            preventDuplicates: false,
            status: 'danger'
          });
        }
      );
    } else if (this.accountService.hasAnyAuthority(['STHGUICHET'])) {
      this.plaqueImmatriculationService.checkqr(this.editForm.get(['numeroSerie'])!.value).subscribe(
        (rsp: HttpResponse<any>) => {
          if (rsp.body !== null) {
            this.plaqueImmatriculationService
              .queryQr(this.editForm.get(['numeroSerie'])!.value)
              .subscribe((res: HttpResponse<IPlaqueImmatriculation>) => {
                if (res.body) {
                  if (res.body.vehiculeId === null) {
                    this.numbg.nativeElement.value = res.body.numeroSerie;
                    this.immg.nativeElement.value = res.body.numeroImmatriculation;
                    this.idg.nativeElement.value = res.body.id;
                    this.idd = res.body.id;
                    this.nim = res.body.numeroImmatriculation;
                    this.nser = res.body.numeroSerie;
                  } else {
                    this.toastrService.show('La bande a déjà été utilisé', 'Attention', {
                      duplicatesBehaviour: undefined,
                      duration: 5000,
                      position: NbGlobalPhysicalPosition.TOP_RIGHT,
                      preventDuplicates: false,
                      status: 'danger'
                    });
                    this.editForm.get(['numeroSerie']).reset();
                    this.editForm.get(['numeroImmatriculation']).reset();
                    this.editForm.get(['certificatImmatriculationId']).reset();
                  }
                }
              });
          } else {
            this.toastrService.show("Cette bande n'est pas reconnue", 'Attention', {
              duration: 5000,
              position: NbGlobalPhysicalPosition.TOP_RIGHT,
              preventDuplicates: false,
              status: 'danger'
            });
            this.editForm.get(['numeroSerie']).reset();
            this.editForm.get(['numeroImmatriculation']).reset();
            this.editForm.get(['certificatImmatriculationId']).reset();
          }
        },
        error => {
          this.toastrService.show(error, 'Attention', {
            duration: 5000,
            position: NbGlobalPhysicalPosition.TOP_RIGHT,
            preventDuplicates: false,
            status: 'danger'
          });
        }
      );
    }
  }

  onScanned() {
    if (this.accountService.hasAnyAuthority(['CONCESSIONNAIRE', 'REVENDEUR'])) {
      this.plaqueImmatriculationService.checkqr(this.editForm.get(['certificatImmatriculationId'])!.value).subscribe(
        (rsp: HttpResponse<any>) => {
          if (rsp.body !== null) {
            this.certificatImmatriculationService
              .findqr(this.editForm.get(['certificatImmatriculationId'])!.value)
              .subscribe((res: HttpResponse<CertificatImmatriculation>) => {
                if (res.body) {
                  if (res.body.numero !== 'null') {
                    this.cert.nativeElement.value = res.body.numero;
                    this.certiId = res.body.id;
                  } else {
                    this.toastrService.show('Ce certificat est déjà utilisé!', 'Attention', {
                      duration: 5000,
                      limit: 0,
                      position: NbGlobalPhysicalPosition.TOP_RIGHT,
                      preventDuplicates: false,
                      status: 'danger'
                    });
                    this.editForm.get(['certificatImmatriculationId']).reset();
                  }
                }
              });
          } else {
            this.toastrService.show("Cette bande n'est pas reconnue", 'Attention', {
              duration: 5000,
              limit: 0,
              position: NbGlobalPhysicalPosition.TOP_RIGHT,
              preventDuplicates: false,
              status: 'danger'
            });
            this.editForm.get(['certificatImmatriculationId']).reset();
          }
        },
        error => {
          this.toastrService.show('Certificat non reconnu!', 'Attention', {
            duration: 5000,
            limit: 0,
            position: NbGlobalPhysicalPosition.TOP_RIGHT,
            preventDuplicates: false,
            status: 'danger'
          });
          this.editForm.get(['certificatImmatriculationId']).reset();
        }
      );
    } else if (this.accountService.hasAnyAuthority(['STHGUICHET'])) {
      this.plaqueImmatriculationService.checkqr(this.editForm.get(['certificatImmatriculationId'])!.value).subscribe(
        (rsp: HttpResponse<any>) => {
          if (rsp.body !== null) {
            this.certificatImmatriculationService
              .findqr(this.editForm.get(['certificatImmatriculationId'])!.value)
              .subscribe((res: HttpResponse<CertificatImmatriculation>) => {
                if (res.body) {
                  if (res.body.numero !== 'null') {
                    this.certg.nativeElement.value = res.body.numero;
                    this.certiId = res.body.id;
                  } else {
                    this.toastrService.show('Ce certificat est déjà utilisé!', 'Attention', {
                      duration: 5000,
                      limit: 0,
                      position: NbGlobalPhysicalPosition.TOP_RIGHT,
                      preventDuplicates: false,
                      status: 'danger'
                    });
                    this.editForm.get(['certificatImmatriculationId']).reset();
                  }
                }
              });
          } else {
            this.toastrService.show("Cette bande n'est pas reconnue", 'Attention', {
              duration: 5000,
              limit: 0,
              position: NbGlobalPhysicalPosition.TOP_RIGHT,
              preventDuplicates: false,
              status: 'danger'
            });
            this.editForm.get(['certificatImmatriculationId']).reset();
          }
        },
        error => {
          this.toastrService.show(error, 'Attention', {
            duration: 5000,
            position: NbGlobalPhysicalPosition.TOP_RIGHT,
            preventDuplicates: false,
            status: 'danger'
          });
        }
      );
    }
  }

  toggle($event: boolean) {
    this.checked = $event;
    this.checked2 = false;
  }
  toggle2($event: boolean) {
    this.checked2 = $event;
    this.checked = false;
  }

  typeVehi() {
    for (const typV of this.typeVehicules) {
      if (typV.id === this.editForm.get(['vehiculeId']).value) {
        alert('Type Vehicule: ' + typV.libelle);
      }
    }
    /*alert("Hi");
    if (this.typeVehicules[0].libelle){
      alert(this.typeVehicules[0].libelle);
    }*/
    /*this.idTyp = this.editForm.get(['vehiculeId']).value;
    this.typeVehicule = this.typeVehicules.filter(typeVehicule => typeVehicule.id === this.idTyp);*/
    /*alert(this.typeVehicule[0].libelle);
    if (this.editForm.get(['vehiculeId']).value){
      for (let typV of this.typeVehicules){
        if (typV.id === this.editForm.get(['vehiculeId']).value){
          alert("Type Vehicule: "+typV.libelle);
        }
      }
    }*/
  }
}
