import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { LoginService } from 'app/core/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { NbMenuService, NbSidebarService } from '@nebular/theme';
import { Observable, Subject } from 'rxjs';
import { delay, share } from 'rxjs/operators';
import { FirstloginService } from 'app/entities/firstlogin/firstlogin.service';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IUser } from 'app/core/user/user.model';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
  inProduction: boolean;
  isNavbarCollapsed: boolean;
  languages: any[];
  swaggerEnabled: boolean;
  modalRef: NgbModalRef;
  version: string;
  user: IUser;
  userorganisation: IOrganisation[];
  protected layoutSize$ = new Subject();

  constructor(
    private loginService: LoginService,
    protected firstloginService: FirstloginService,
    protected organisationService: OrganisationService,
    private languageService: JhiLanguageService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private sidebarService: NbSidebarService,
    private menuService: NbMenuService,
    private router: Router
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
    this.isNavbarCollapsed = true;
  }

  changeLayoutSize() {
    this.layoutSize$.next();
  }

  onChangeLayoutSize(): Observable<any> {
    return this.layoutSize$.pipe(
      share(),
      delay(1)
    );
  }

  ngOnInit() {
    this.languages = this.languageHelper.getAll();

    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });

    this.firstloginService.queryUser().subscribe((res: HttpResponse<IUser>) => (this.user = res.body));

    this.organisationService.queryMyorganisation().subscribe((res: HttpResponse<IOrganisation[]>) => (this.userorganisation = res.body));
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  collapseNavbar() {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  logout() {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  getImageUrl() {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
  }

  toggleSidebar() {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}
