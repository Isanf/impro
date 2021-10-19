import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonnePhysique } from 'app/shared/model/personne-physique.model';

@Component({
  selector: 'jhi-personne-physique-detail',
  templateUrl: './personne-physique-detail.component.html'
})
export class PersonnePhysiqueDetailComponent implements OnInit {
  personnePhysique: IPersonnePhysique;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personnePhysique }) => {
      this.personnePhysique = personnePhysique;
    });
  }

  previousState() {
    window.history.back();
  }
}
