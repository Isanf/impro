import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserDeviceId } from 'app/shared/model/user-device-id.model';
import { UserDeviceIdService } from './user-device-id.service';
import { UserDeviceIdDeleteDialogComponent } from './user-device-id-delete-dialog.component';

@Component({
  selector: 'jhi-user-device-id',
  templateUrl: './user-device-id.component.html'
})
export class UserDeviceIdComponent implements OnInit, OnDestroy {
  userDeviceIds?: IUserDeviceId[];
  eventSubscriber?: Subscription;

  constructor(
    protected userDeviceIdService: UserDeviceIdService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userDeviceIdService.query().subscribe((res: HttpResponse<IUserDeviceId[]>) => (this.userDeviceIds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserDeviceIds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserDeviceId): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserDeviceIds(): void {
    this.eventSubscriber = this.eventManager.subscribe('userDeviceIdListModification', () => this.loadAll());
  }

  delete(userDeviceId: IUserDeviceId): void {
    const modalRef = this.modalService.open(UserDeviceIdDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userDeviceId = userDeviceId;
  }
}
