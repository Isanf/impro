import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';
import { StockDeleteDialogComponent } from './stock-delete-dialog.component';
import { stockRoute } from './stock.route';
import { NbCardModule, NbInputModule, NbListModule } from '@nebular/theme';

@NgModule({
  imports: [ImproSharedModule, RouterModule.forChild(stockRoute), NbInputModule, NbCardModule, NbListModule],
  declarations: [StockComponent, StockDetailComponent, StockUpdateComponent, StockDeleteDialogComponent],
  entryComponents: [StockDeleteDialogComponent]
})
export class ImproStockModule {}
