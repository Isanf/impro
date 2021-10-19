import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFirstlogin, Firstlogin } from 'app/shared/model/firstlogin.model';
import { FirstloginService } from './firstlogin.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-firstlogin-update',
  templateUrl: './firstlogin-update.component.html'
})
export class FirstloginUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    passe: [],
    userId: []
  });

  constructor(
    protected firstloginService: FirstloginService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ firstlogin }) => {
      this.updateForm(firstlogin);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(firstlogin: IFirstlogin): void {
    this.editForm.patchValue({
      id: firstlogin.id,
      passe: firstlogin.passe,
      userId: firstlogin.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const firstlogin = this.createFromForm();
    if (firstlogin.id !== undefined) {
      this.subscribeToSaveResponse(this.firstloginService.update(firstlogin));
    } else {
      this.subscribeToSaveResponse(this.firstloginService.create(firstlogin));
    }
  }

  private createFromForm(): IFirstlogin {
    return {
      ...new Firstlogin(),
      id: this.editForm.get(['id'])!.value,
      passe: this.editForm.get(['passe'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFirstlogin>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
