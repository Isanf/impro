import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFirstlogin } from 'app/shared/model/firstlogin.model';

@Component({
  selector: 'jhi-firstlogin-detail',
  templateUrl: './firstlogin-detail.component.html'
})
export class FirstloginDetailComponent implements OnInit {
  firstlogin: IFirstlogin | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ firstlogin }) => (this.firstlogin = firstlogin));
  }

  previousState(): void {
    window.history.back();
  }
}
