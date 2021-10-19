import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILivraisonCarnetSouche, LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';
import { LivraisonCarnetSoucheService } from './livraison-carnet-souche.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { ICommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { AccountService } from 'app/core/auth/account.service';
import { IInfoCommandeCarnetASouche } from 'app/shared/model/info-commande-carnet-a-souche.model';
import { ICarnetASouche } from 'app/shared/model/carnet-a-souche.model';
import { InfoCommandeCarnetASoucheService } from 'app/entities/info-commande-carnet-a-souche/info-commande-carnet-a-souche.service';
import { CarnetASoucheService } from 'app/entities/carnet-a-souche/carnet-a-souche.service';

type SelectableEntity = IOrganisation | ICommandeCarnetSouche;

@Component({
  selector: 'jhi-livraison-carnet-souche-update',
  templateUrl: './livraison-carnet-souche-update.component.html'
})
export class LivraisonCarnetSoucheUpdateComponent implements OnInit {
  isSaving = false;
  organisations: IOrganisation[] = [];
  commandecarnetsouches: ICommandeCarnetSouche[] = [];
  infoCommandeCarnetASouches: IInfoCommandeCarnetASouche[] = [];
  carnetASouches: ICarnetASouche[] = [];
  commandecarnetsouche: ICommandeCarnetSouche;
  livraisonCarnetSouches: ILivraisonCarnetSouche;

  editForm = this.fb.group({
    id: [],
    numeroLivraisonCS: [],
    dateLivraison: [],
    concessionnaireId: [],
    supernetId: [],
    commandeCarnetSoucheId: [],
    numeroCommandeCS: [],
    infoCommandeCarnetASouche: [],
    carnetASouches: []
  });

  constructor(
    protected livraisonCarnetSoucheService: LivraisonCarnetSoucheService,
    protected organisationService: OrganisationService,
    protected commandeCarnetSoucheService: CommandeCarnetSoucheService,
    protected activatedRoute: ActivatedRoute,
    private accoutService: AccountService,
    private infoservice: CarnetASoucheService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livraisonCarnetSouche }) => {
      if (!livraisonCarnetSouche.id) {
        const today = moment().startOf('day');
        livraisonCarnetSouche.dateLivraison = today;
      }

      this.updateForm(livraisonCarnetSouche);
      this.activatedRoute.paramMap.subscribe(params => {
        const id = parseInt(params.get('idd'), 0);
        this.livraisonCarnetSoucheService.findToLivrer(id).subscribe((resp: HttpResponse<ILivraisonCarnetSouche>) => {
          this.livraisonCarnetSouches = resp.body;
          this.infoCommandeCarnetASouches = this.livraisonCarnetSouches.infoCommandeCarnetASoucheDTO;
          this.commandecarnetsouche = this.livraisonCarnetSouches.commandeCarnetSoucheDTO;
        });
      });

      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.commandeCarnetSoucheService
        .queryAll()
        .subscribe((res: HttpResponse<ICommandeCarnetSouche[]>) => (this.commandecarnetsouches = res.body || []));
    });
  }

  updateForm(livraisonCarnetSouche: ILivraisonCarnetSouche): void {
    this.editForm.patchValue({
      id: livraisonCarnetSouche.id,
      numeroLivraisonCS: livraisonCarnetSouche.numeroLivraisonCS,
      dateLivraison: livraisonCarnetSouche.dateLivraison ? livraisonCarnetSouche.dateLivraison.format(DATE_TIME_FORMAT) : null,
      concessionnaireId: livraisonCarnetSouche.concessionnaireId,
      supernetId: livraisonCarnetSouche.supernetId,
      commandeCarnetSoucheId: livraisonCarnetSouche.commandeCarnetSoucheId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livraisonCarnetSouche = this.createFromForm();
    if (livraisonCarnetSouche.id !== undefined) {
      this.subscribeToSaveResponse(this.livraisonCarnetSoucheService.update(livraisonCarnetSouche));
    } else {
      this.subscribeToSaveResponse(this.livraisonCarnetSoucheService.create(livraisonCarnetSouche));
    }
  }

  private createFromForm(): ILivraisonCarnetSouche {
    return {
      ...new LivraisonCarnetSouche(),
      id: this.editForm.get(['id'])!.value,
      numeroLivraisonCS: this.editForm.get(['numeroLivraisonCS'])!.value,
      dateLivraison: this.editForm.get(['dateLivraison'])!.value
        ? moment(this.editForm.get(['dateLivraison'])!.value, DATE_TIME_FORMAT)
        : undefined,
      concessionnaireId: this.commandecarnetsouche.concessionnaireId,
      infosId: this.editForm.get(['infoCommandeCarnetASouche'])!.value,
      commandeCarnetSoucheId: this.commandecarnetsouche.id,
      carnetASoucheDTO: this.editForm.get(['carnetASouches'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivraisonCarnetSouche>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  next() {
    this.infoservice
      .findCarnet(this.editForm.get(['infoCommandeCarnetASouche'])!.value)
      .subscribe((res: HttpResponse<ICarnetASouche[]>) => {
        if (res.body !== null) {
          this.carnetASouches = res.body;
        }
      });
  }
}
