import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogActivity } from 'app/shared/model/log-activity.model';

@Component({
  selector: 'jhi-log-activity-detail',
  templateUrl: './log-activity-detail.component.html'
})
export class LogActivityDetailComponent implements OnInit {
  logActivity: ILogActivity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logActivity }) => (this.logActivity = logActivity));
  }

  previousState(): void {
    window.history.back();
  }
}
