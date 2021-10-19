import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPosePlaque } from 'app/shared/model/pose-plaque.model';

@Component({
  selector: 'jhi-pose-plaque-detail',
  templateUrl: './pose-plaque-detail.component.html'
})
export class PosePlaqueDetailComponent implements OnInit {
  posePlaque: IPosePlaque;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ posePlaque }) => {
      this.posePlaque = posePlaque;
    });
  }

  previousState() {
    window.history.back();
  }
}
