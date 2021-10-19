import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INation } from 'app/shared/model/nation.model';

@Component({
  selector: 'jhi-nation-detail',
  templateUrl: './nation-detail.component.html'
})
export class NationDetailComponent implements OnInit {
  nation: INation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nation }) => (this.nation = nation));
  }

  previousState(): void {
    window.history.back();
  }
}
