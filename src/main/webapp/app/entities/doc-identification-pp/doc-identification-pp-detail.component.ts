import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocIdentificationPP } from 'app/shared/model/doc-identification-pp.model';

@Component({
  selector: 'jhi-doc-identification-pp-detail',
  templateUrl: './doc-identification-pp-detail.component.html'
})
export class DocIdentificationPPDetailComponent implements OnInit {
  docIdentificationPP: IDocIdentificationPP | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ docIdentificationPP }) => (this.docIdentificationPP = docIdentificationPP));
  }

  previousState(): void {
    window.history.back();
  }
}
