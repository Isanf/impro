import { Component } from '@angular/core';

import { MENU_ITEMS } from './pages-menu';
import { AccountService } from 'app/core/auth/account.service';
import { ADMIN_MENU_ITEMS } from 'app/layouts/sidebar/admin-menu';
import { CONS_MENU_ITEMS } from 'app/layouts/sidebar/concessionnaire-menu';
import { REVEN_MENU_ITEMS } from 'app/layouts/sidebar/revendeur-menu';
import { NbSidebarService } from '@nebular/theme';
import { Subject } from 'rxjs';
import { STHGUICHET_MENU_ITEMS } from 'app/layouts/sidebar/sthguichet-menu';
import { DGTTM_MENU_ITEMS } from 'app/layouts/sidebar/dgttm-menu';
import { DOUANE_MENU_ITEMS } from 'app/layouts/sidebar/douane-menu';
import { DG_DGTTM_MENU_ITEMS } from 'app/layouts/sidebar/dg_dgttm-menu';
import { DG_DOUANE_MENU_ITEMS } from 'app/layouts/sidebar/dg_douane-menu';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './pages.component.html'
})
export class PagesComponent {
  menu = MENU_ITEMS;
  admin_menu = ADMIN_MENU_ITEMS;
  cons_menu = CONS_MENU_ITEMS;
  reven_menu = REVEN_MENU_ITEMS;
  sthguichet_menu = STHGUICHET_MENU_ITEMS;
  dgttm_menu = DGTTM_MENU_ITEMS;
  dg_dgttm_menu = DG_DGTTM_MENU_ITEMS;
  douane_menu = DOUANE_MENU_ITEMS;
  dg_douane_menu = DG_DOUANE_MENU_ITEMS;
  protected layoutSize$ = new Subject();
  constructor(private accountService: AccountService, private sidebarService: NbSidebarService) {}

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  changeLayoutSize() {
    this.layoutSize$.next();
  }

  toggleSidebar() {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.changeLayoutSize();

    return false;
  }
}
