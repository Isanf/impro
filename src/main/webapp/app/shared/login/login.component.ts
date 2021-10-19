import { Component, AfterViewInit, Renderer2, ElementRef } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { AccountService } from 'app/core/auth/account.service';
import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { FirstloginService } from 'app/entities/firstlogin/firstlogin.service';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

@Component({
  selector: 'jhi-login-modal',
  templateUrl: './login.component.html'
})
export class JhiLoginModalComponent implements AfterViewInit {
  authenticationError: boolean;
  /**first: { (observer?: PartialObserver<boolean[]>): Subscription; (next: (null |
 undefined), error: (null | undefined), complete: () => void): Subscription;
  (next: (null | undefined), error: (error: any) => void, complete?: () => void):
  Subscription; (next: (value: boolean[]) => void, error: (null | undefined), complete: () =>
  void): Subscription; (next?: (value: boolean[]) => void, error?: (error: any) => void, complete?:
   () => void): Subscription };**/
  first: string;
  user: User;
  organisation: IOrganisation[];

  loginForm = this.fb.group({
    username: [''],
    password: [''],
    rememberMe: [false]
  });

  constructor(
    private eventManager: JhiEventManager,
    private loginService: LoginService,
    private accountService: AccountService,
    private stateStorageService: StateStorageService,
    private userService: UserService,
    private firstloginService: FirstloginService,
    protected organisationService: OrganisationService,
    protected jhiAlertService: JhiAlertService,
    private elementRef: ElementRef,
    private Renderer: Renderer2,
    private router: Router,
    private route: ActivatedRoute,
    private principal: AccountService,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  ngAfterViewInit() {
    setTimeout(() => this.Renderer.selectRootElement(this.elementRef.nativeElement.querySelector('#username')), 0);

    /* this.firstloginService.firstlogin().subscribe(val =>{
      this.first = val;
    });*/
  }

  cancel() {
    this.authenticationError = false;
    this.loginForm.patchValue({
      username: '',
      password: ''
    });
    this.activeModal.dismiss('cancel');
  }

  login() {
    this.loginService
      .login({
        username: this.loginForm.get('username').value,
        password: this.loginForm.get('password').value,
        rememberMe: this.loginForm.get('rememberMe').value
      })
      .subscribe(
        () => {
          this.authenticationError = false;
          this.activeModal.dismiss('login success');
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/')
          ) {
            this.router.navigate(['/']);
          }

          this.eventManager.broadcast({
            name: 'authenticationSuccess',
            content: 'Sending Authentication Success'
          });

          //const redirect = this.stateStorageService.getUrl();
          this.firstloginService.firstlogin().subscribe(val => {
            const redirect = this.stateStorageService.getUrl();
            const redirect1 = '/account/password';
            if (val == null) {
              this.stateStorageService.storeUrl(null);
              this.router.navigateByUrl(redirect);
            } else {
              this.stateStorageService.storeUrl(null);
              this.router.navigateByUrl(redirect1);
              //alert(val);
            }
          });
        },
        () => (this.authenticationError = true)
      );
  }

  register() {
    this.activeModal.dismiss('to state register');
    this.router.navigate(['/account/register']);
  }

  requestResetPassword() {
    this.activeModal.dismiss('to state requestReset');
    this.router.navigate(['/account/reset', 'request']);
  }
}
