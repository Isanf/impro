import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

@Component({
  selector: 'jhi-doc-identification-pm-detail',
  templateUrl: './doc-identification-pm-detail.component.html'
})
export class DocIdentificationPMDetailComponent implements OnInit {
  docIdentificationPM: IDocIdentificationPM;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ docIdentificationPM }) => {
      this.docIdentificationPM = docIdentificationPM;
    });
  }

  previousState() {
    window.history.back();
  }
}
