import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatistique } from 'app/shared/model/statistique.model';

@Component({
  selector: 'jhi-statistique-detail',
  templateUrl: './statistique-detail.component.html'
})
export class StatistiqueDetailComponent implements OnInit {
  statistique: IStatistique | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statistique }) => (this.statistique = statistique));
  }

  previousState(): void {
    window.history.back();
  }
}
