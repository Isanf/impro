import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { NbGlobalPhysicalPosition, NbToastrService } from '@nebular/theme';

@Component({
  selector: 'jhi-alert',
  template: `
    <!--<div class="alerts" role="alert">
      <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
        <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
          <pre [innerHTML]="alert.msg"></pre>
        </ngb-alert>
      </div>
    </div>-->
  `
})
export class JhiAlertComponent implements OnInit, OnDestroy {
  alerts: any[];
  index: any = 0;

  constructor(private alertService: JhiAlertService, private toastrService: NbToastrService) {}

  ngOnInit() {
    // this.index += 1;
    this.alerts = this.alertService.get();
    this.alerts.forEach(alert => {
      if (alert && alert.type && alert.msg) {
        this.toastrService.show(alert.msg, 'Success', {
          duration: 3000,
          position: NbGlobalPhysicalPosition.TOP_RIGHT,
          preventDuplicates: false,
          status: 'success'
        });
      }
    });
  }

  setClasses(alert) {
    return {
      'jhi-toast': alert.toast,
      [alert.position]: true
    };
  }

  ngOnDestroy() {
    this.alerts = [];
  }

  /*showToast(position, status) {
    this.index += 1;
    this.toastrService.show(status || 'Success', `Un élément enregistré`, { position, status });
  }*/
}
