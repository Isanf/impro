import { Component, OnInit, ViewChild } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVente, Vente } from 'app/shared/model/vente.model';
import { VenteService } from './vente.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IPersonnePhysique, PersonnePhysique } from 'app/shared/model/personne-physique.model';
import { PersonnePhysiqueService } from 'app/entities/personne-physique/personne-physique.service';
import { IPersonneMorale, PersonneMorale } from 'app/shared/model/personne-morale.model';
import { PersonneMoraleService } from 'app/entities/personne-morale/personne-morale.service';
import { DocIdentificationPP, IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';
import { DocIdentificationPM, IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';
import { IVehicule, Vehicule } from 'app/shared/model/vehicule.model';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';
import { MarqueVehiculeService } from 'app/entities/marque-vehicule/marque-vehicule.service';
import { ICommandeVehicule } from 'app/shared/model/commande-vehicule.model';
import { AccountService } from 'app/core/auth/account.service';
import { ITypeVehicule } from 'app/shared/model/type-vehicule.model';
import { TypeVehiculeService } from 'app/entities/type-vehicule/type-vehicule.service';
import { DocIdentificationPMService } from 'app/entities/doc-identification-pm/doc-identification-pm.service';
import { DocIdentificationPPService } from 'app/entities/doc-identification-pp/doc-identification-pp.service';
import { IMarqueVehicule, MarqueVehicule } from 'app/shared/model/marque-vehicule.model';
import { map, startWith } from 'rxjs/operators';
import { NationService } from 'app/entities/nation/nation.service';
import { INation } from 'app/shared/model/nation.model';

type SelectableEntity = IVehicule | IMarqueVehicule;

@Component({
  selector: 'jhi-vente-update',
  templateUrl: './vente-update.component.html'
})
export class VenteUpdateComponent implements OnInit {
  isSaving: boolean;
  isSaving1: boolean;
  enregist: string;
  organisations: IOrganisation[];
  personnephysiques: IPersonnePhysique[];
  personnemorales: IPersonneMorale[];
  vehicules: IVehicule[] = [];
  vehicules1: IVehicule[] = [];
  marquevehicules: IMarqueVehicule[] = [];
  typevehicules: ITypeVehicule[] = [];
  typevehicules1: ITypeVehicule[] = [];
  marquevehicules1: IMarqueVehicule[] = [];
  vehicule: IVehicule[] = [];
  marquevehicule: IMarqueVehicule;
  marquevehiculeHPP: IMarqueVehicule;
  marquevehiculeHPM: IMarqueVehicule;
  vehicule1: IVehicule[] = [];
  docIdentificationPP: IDocIdentificationPP;
  listnation: INation[];
  carrosserie: string[] = [
    'CONDUITE INTERIEUR',
    'BREAK',
    'CABRIOLET',
    'MOTO SOLO',
    'TRICYCLE',
    'COMMERCIALE',
    'HANDICAPE',
    'DIVERS (non spécifié)'
  ];
  genre: string[] = [
    'MOTOCYCLETTE',
    'TRICYCLE A MOTEUR',
    'QUATRICYCLE A MOTEUR',
    'VOITURE PARTICULIERE',
    'TRACTEUR ROUTIER',
    'CAMIONNETTE',
    'DIVERS (non spécifié)'
  ];
  energies: string[] = ['Essence', 'Gazoile', 'Electricité', 'Autre', 'Rien'];

  marqvehicul: MarqueVehicule;
  mark: string;
  markid: string;

  @ViewChild('autoInput', { static: true }) input;

  filteredOptions$: Observable<IMarqueVehicule[]>;

  editForm = this.fb.group({
    id: [],
    montant: [],
    nom: [],
    prenom: [],
    dateNaissance: [],
    lieuNaissance: [],
    telephone: [],
    residence: [],
    numeroDoc: [],
    dateEtablissement: [],
    lieuEtablissement: [],
    autoriteEmettrice: [],
    typeDocIdentification: [],
    vehiculeStock: [],
    marqueVehiculeId: [],
    nip: [],
    enregi: [],
    typeVehiculeId: [],
    numeroChassis: [],
    genre: [],
    model: [],
    energie: [],
    poids: [],
    chargeUtile: [],
    ptac: [],
    place: [],
    carrosserie: [],
    nation: []
  });

  editForm1 = this.fb1.group({
    id: [],
    montant: [],
    denomination: [],
    dateCreate: [],
    numeroDoc1: [],
    numeroIFU: [],
    numeroRCCM: [],
    vehiculeStock: [],
    marqueVehiculeId: [],
    typeVehiculeId: [],
    numeroChassis: [],
    genre: [],
    model: [],
    energie: [],
    poids: [],
    chargeUtile: [],
    ptac: [],
    place: [],
    carrosserie: [],
    nation: []
  });

  outdoor: 'indoor' | 'outdoor';
  indoor: 'indoor' | 'outdoor';
  exist: boolean = null;
  exist1: boolean = null;
  docexist: HttpResponse<IDocIdentificationPP>;
  docexist1: HttpResponse<IDocIdentificationPM>;
  vexist: boolean = null;
  vehiexist: HttpResponse<IVehicule>;
  vexist1: boolean = null;
  vehiexist1: HttpResponse<IVehicule>;
  checkedStock = false;
  checkedHStock = false;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected venteService: VenteService,
    protected organisationService: OrganisationService,
    protected personnePhysiqueService: PersonnePhysiqueService,
    protected personneMoraleService: PersonneMoraleService,
    protected vehiculeService: VehiculeService,
    protected marqueVehiculeService: MarqueVehiculeService,
    protected typtVehiculeService: TypeVehiculeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private fb1: FormBuilder,
    private accountService: AccountService,
    private docIdentificationPPService: DocIdentificationPPService,
    private docIdentificationPMService: DocIdentificationPMService,
    protected router: Router,
    protected nationService: NationService
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vente }) => {
      this.updateForm(vente);
    });
    this.isSaving1 = false;
    this.activatedRoute.data.subscribe(({ vente }) => {
      //this.updateForm1(vente);
    });
    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.typtVehiculeService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.typevehicules = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personnePhysiqueService
      .query()
      .subscribe(
        (res: HttpResponse<IPersonnePhysique[]>) => (this.personnephysiques = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.personneMoraleService
      .query()
      .subscribe(
        (res: HttpResponse<IPersonneMorale[]>) => (this.personnemorales = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.nationService
      .querylist()
      .subscribe((res: HttpResponse<INation[]>) => (this.listnation = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    if (this.accountService.hasAnyAuthority('CONCESSIONNAIRE')) {
      this.vehiculeService.queryNDelivNSell().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules = res.body || []));
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body || []));
      this.vehiculeService.queryNDelivNSell().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules1 = res.body || []));
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules1 = res.body || []));
    } else {
      this.vehiculeService.queryDelivNSell().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules = res.body || []));
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules = res.body || []));
      this.vehiculeService.queryDelivNSell().subscribe((res: HttpResponse<IVehicule[]>) => (this.vehicules1 = res.body || []));
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => (this.marquevehicules1 = res.body || []));
    }
  }

  updateForm(vente: IVente) {
    this.editForm.patchValue({
      id: vente.id
    });
  }

  updateForm1(vente: IVente) {
    this.editForm1.patchValue({
      id: vente.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vente = this.createFromForm();
    if (vente.id !== undefined) {
      this.subscribeToSaveResponse(this.venteService.update(vente));
    } else {
      this.subscribeToSaveResponse(this.venteService.create(vente));
    }
  }
  save1() {
    this.isSaving1 = true;
    const ventePM = this.createFromForm1();
    this.subscribeToSaveResponse(this.venteService.create(ventePM));
    /*if (ventePM.id !== undefined  ) {
      this.subscribeToSaveResponse(this.venteService.update(ventePM));
    } else {
      this.subscribeToSaveResponse(this.venteService.create(ventePM));
    }*/
  }

  private createFromForm(): IVente {
    return {
      ...new Vente(),
      id: this.editForm.get(['id']).value,
      quantiteVendue: this.editForm.get(['montant']).value,
      personnePhysiqueDTO: new PersonnePhysique(
        null,
        this.editForm.get(['nom']).value,
        this.editForm.get(['prenom']).value,
        this.editForm.get(['dateNaissance']).value,
        this.editForm.get(['lieuNaissance']).value,
        this.editForm.get(['residence']).value,
        this.editForm.get(['telephone']).value
      ),
      docIdentificationPPDTO: new DocIdentificationPP(
        null,
        this.editForm.get(['numeroDoc']).value,
        this.editForm.get(['nip']).value,
        this.editForm.get(['dateEtablissement']).value,
        this.editForm.get(['lieuEtablissement']).value,
        this.editForm.get(['autoriteEmettrice']).value,
        this.editForm.get(['typeDocIdentification']).value
      ),
      vehiculeDTOStock: this.editForm.get(['vehiculeStock']).value,
      vehiculeDTO: new Vehicule(
        null,
        this.editForm.get(['numeroChassis']).value,
        this.editForm.get(['genre']).value,
        this.editForm.get(['model']).value,
        this.editForm.get(['energie']).value,
        null,
        null,
        null,
        this.editForm.get(['poids']).value,
        this.editForm.get(['chargeUtile']).value,
        this.editForm.get(['ptac']).value,
        null,
        this.editForm.get(['place']).value,
        null,
        null,
        this.editForm.get(['carrosserie']).value,
        null,
        null,
        null,
        null,
        null,
        this.editForm.get(['typeVehiculeId']).value,
        this.marquevehiculeHPP.id,
        null,
        null
      ),
      nationDTO: this.editForm.get(['nation']).value
    };
  }

  private createFromForm1(): IVente {
    return {
      ...new Vente(),
      id: this.editForm1.get(['id']).value,
      quantiteVendue: this.editForm1.get(['montant']).value,
      personneMoraleDTO: new PersonneMorale(
        null,
        this.editForm1.get(['numeroIFU']).value,
        this.editForm1.get(['denomination']).value,
        this.editForm1.get(['dateCreate']).value
      ),
      docIdentificationPMDTO: new DocIdentificationPM(
        null,
        this.editForm1.get(['numeroDoc1']).value,
        this.editForm1.get(['numeroIFU']).value,
        this.editForm1.get(['numeroRCCM']).value
      ),
      vehiculeDTOStock: this.editForm1.get(['vehiculeStock']).value,
      vehiculeDTO: new Vehicule(
        null,
        this.editForm1.get(['numeroChassis']).value,
        this.editForm1.get(['genre']).value,
        this.editForm1.get(['model']).value,
        this.editForm1.get(['energie']).value,
        null,
        null,
        null,
        this.editForm1.get(['poids']).value,
        this.editForm1.get(['chargeUtile']).value,
        this.editForm1.get(['ptac']).value,
        null,
        this.editForm1.get(['place']).value,
        null,
        null,
        this.editForm1.get(['carrosserie']).value,
        null,
        null,
        null,
        null,
        null,
        this.editForm1.get(['typeVehiculeId']).value,
        this.marquevehiculeHPM.id,
        null,
        null
      ),
      nationDTO: this.editForm1.get(['nation']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVente>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.isSaving1 = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
    this.isSaving1 = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
  //////////////////////////////////// BIGIN FOR FILTERING MARKS /////////////////////////////////////////////////
  private filter(value: string): IMarqueVehicule[] {
    const filterValue = value;
    return this.marquevehicules.filter(optionValue => optionValue.libelle.toLowerCase().includes(filterValue));
  }
  viewHandle(value: string) {
    return value;
  }
  getFilteredOptions(value: string): Observable<IMarqueVehicule[]> {
    return of(value).pipe(map(filterString => this.filter(filterString)));
  }
  onChange() {
    this.filteredOptions$ = this.getFilteredOptions(this.input.nativeElement.value);
  }
  onSelectionChange($event) {
    this.filteredOptions$ = this.getFilteredOptions($event);
    this.marquevehiculeHPP = this.marquevehicules.find(mv => mv.libelle.includes($event));
  }
  onSelectionChange1($event) {
    this.filteredOptions$ = this.getFilteredOptions($event);
    this.marquevehiculeHPM = this.marquevehicules.find(mv => mv.libelle.includes($event));
  }
  clic() {
    this.activatedRoute.data.subscribe(() => {
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => {
        this.marquevehicules = res.body || [];
        this.filteredOptions$ = of(res.body);
        this.filteredOptions$ = this.editForm.get(['marqueVehiculeId'])!.valueChanges.pipe(
          startWith(''),
          map(filterString => this.filter(filterString.toString().toLowerCase()))
        );
      });
    });
  }

  clic1() {
    this.activatedRoute.data.subscribe(() => {
      this.marqueVehiculeService.query().subscribe((res: HttpResponse<IMarqueVehicule[]>) => {
        this.marquevehicules = res.body || [];
        this.filteredOptions$ = of(res.body);
        this.filteredOptions$ = this.editForm1.get(['marqueVehiculeId'])!.valueChanges.pipe(
          startWith(''),
          map(filterString => this.filter(filterString.toString().toLowerCase()))
        );
      });
    });
  }

  //////////////////////////////////// END FOR FILTERING MARKS ////////////////////////////////////////////////////

  getmarque() {
    for (const v of this.vehicules) {
      if (v.marqueVehiculeId === this.marquevehiculeHPP.id) {
        this.vehicule.push(v);
      }
    }
  }

  getmarque1() {
    for (const v of this.vehicules1) {
      if (v.marqueVehiculeId === this.marquevehiculeHPM.id) {
        this.vehicule1.push(v);
      }
    }
  }

  vidmarq() {
    for (let i = 0; i < this.vehicule.length; i++) {
      this.vehicule.splice(i);
    }
  }

  vidmarq1() {
    for (let i = 0; i < this.vehicule1.length; i++) {
      this.vehicule1.splice(i);
    }
  }

  changeHandler() {
    this.enregist = this.editForm.get(['enregi']).value;
  }

  videNip() {
    this.editForm.get(['nip']).reset(null);
    this.docexist = null;
    this.exist = null;
  }

  verifNip() {
    this.docIdentificationPPService.findByNip(this.editForm.get(['nip']).value).subscribe(res => {
      this.docexist = res;
      if (this.docexist.body.nip != null) {
        this.exist = true;
      }
    });
  }

  videIFU() {
    this.editForm1.get(['numeroIFU']).reset(null);
    this.docexist1 = null;
    this.exist1 = null;
  }

  verifIFU() {
    this.docIdentificationPMService.findByIFU(this.editForm1.get(['numeroIFU']).value).subscribe(res => {
      this.docexist1 = res;
      if (this.docexist1.body.numeroIFU != null) {
        this.exist1 = true;
      }
    });
  }

  verifChassis() {
    this.vehiculeService.findByChassis(this.editForm.get(['numeroChassis']).value).subscribe(res => {
      this.vehiexist = res;
      if (this.vehiexist.body.numeroChassis != null) {
        this.vexist = true;
      }
    });
  }

  vidChassis() {
    this.editForm.get(['numeroChassis']).reset(null);
    this.vehiexist = null;
    this.vexist = null;
  }

  verifChassis1() {
    this.vehiculeService.findByChassis(this.editForm1.get(['numeroChassis']).value).subscribe(res => {
      this.vehiexist1 = res;
      if (this.vehiexist1.body.numeroChassis != null) {
        this.vexist1 = true;
      }
    });
  }

  vidChassis1() {
    this.editForm1.get(['numeroChassis']).reset(null);
    this.vehiexist1 = null;
    this.vexist1 = null;
  }

  toggle(checkedStock: boolean) {
    this.checkedHStock = false;
    this.checkedStock = checkedStock;
  }

  toggle2(checkedHStock: boolean) {
    this.checkedStock = false;
    this.checkedHStock = checkedHStock;
  }

  newMarks() {
    this.router.navigate(['marque-vehicule/new']);
  }
}
