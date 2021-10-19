import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonneMorale } from 'app/shared/model/personne-morale.model';

@Component({
  selector: 'jhi-personne-morale-detail',
  templateUrl: './personne-morale-detail.component.html'
})
export class PersonneMoraleDetailComponent implements OnInit {
  personneMorale: IPersonneMorale;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personneMorale }) => {
      this.personneMorale = personneMorale;
    });
  }

  previousState() {
    window.history.back();
  }
}
