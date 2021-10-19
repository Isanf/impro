import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError, Event, NavigationStart, NavigationCancel } from '@angular/router';

import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { AccountService } from 'app/core/auth/account.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LoginModalService } from 'app/core/login/login-modal.service';

@Component({
  selector: 'jhi-main',
  styleUrls: ['main.component.scss'],
  templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
  isCollapsed = false;
  showLoadingIndicator = true;
  modalRef: NgbModalRef;
  constructor(
    private jhiLanguageHelper: JhiLanguageHelper,
    private accoutService: AccountService,
    private loginModalService: LoginModalService,
    private router: Router
  ) {
    this.router.events.subscribe((routeEvent: Event) => {
      if (routeEvent instanceof NavigationStart) {
        this.showLoadingIndicator = true;
      }

      if (routeEvent instanceof NavigationEnd || routeEvent instanceof NavigationError || routeEvent instanceof NavigationError) {
        this.showLoadingIndicator = false;
      }
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'improApp';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }
  isAuthenticated() {
    return this.accoutService.isAuthenticated();
  }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });
  }
  login() {
    this.modalRef = this.loginModalService.open();
  }
}
