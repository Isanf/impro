import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-stock-update',
  templateUrl: './stock-update.component.html'
})
export class StockUpdateComponent implements OnInit {
  isSaving: boolean;

  organisations: IOrganisation[];

  editForm = this.fb.group({
    id: [],
    numeroStock: [],
    dateStock: [],
    concessionnaireId: [],
    file: []
  });
  fileToUpload: File = null;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockService: StockService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stock }) => {
      this.updateForm(stock);
    });
    this.organisationService
      .query()
      .subscribe(
        (res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(stock: IStock) {
    this.editForm.patchValue({
      id: stock.id,
      numeroStock: stock.numeroStock,
      dateStock: stock.dateStock != null ? stock.dateStock.format(DATE_TIME_FORMAT) : null,
      concessionnaireId: stock.concessionnaireId
    });
  }

  previousState() {
    window.history.back();
  }
  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  save() {
    this.isSaving = true;
    const stock = this.createFromForm();
    if (stock.id !== undefined) {
      this.subscribeToSaveResponse(this.stockService.update(stock));
    } else {
      this.subscribeToSaveResponse(this.stockService.createFile(this.fileToUpload));
    }
  }

  private createFromForm(): IStock {
    return {
      ...new Stock(),
      id: this.editForm.get(['id']).value,
      numeroStock: this.editForm.get(['numeroStock']).value,
      dateStock:
        this.editForm.get(['dateStock']).value != null ? moment(this.editForm.get(['dateStock']).value, DATE_TIME_FORMAT) : undefined,
      concessionnaireId: this.editForm.get(['concessionnaireId']).value,
      fichierStock: this.editForm.get(['file']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStock>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrganisationById(index: number, item: IOrganisation) {
    return item.id;
  }
}
