import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ImproSharedModule } from 'app/shared/shared.module';
import { CollaborationComponent } from './collaboration.component';
import { CollaborationDetailComponent } from './collaboration-detail.component';
import { CollaborationUpdateComponent } from './collaboration-update.component';
import { CollaborationDeleteDialogComponent } from './collaboration-delete-dialog.component';
import { collaborationRoute } from './collaboration.route';
import {
  NbWindowService,
  NbDialogModule,
  NbCardModule,
  NbIconModule,
  NbInputModule,
  NbFormFieldModule,
  NbSelectModule,
  NbAutocompleteModule,
  NbButtonModule
} from '@nebular/theme';
import { CollaborationFilter } from 'app/entities/collaboration/collaborationFilter';

@NgModule({
  imports: [
    ImproSharedModule,
    RouterModule.forChild(collaborationRoute),
    NbDialogModule.forChild(),
    NbCardModule,
    NbIconModule,
    NbInputModule,
    NbFormFieldModule,
    NbSelectModule,
    NbAutocompleteModule,
    NbButtonModule
  ],
  declarations: [
    CollaborationComponent,
    CollaborationDetailComponent,
    CollaborationUpdateComponent,
    CollaborationDeleteDialogComponent,
    CollaborationFilter
  ],
  providers: [NbWindowService],
  entryComponents: [CollaborationDeleteDialogComponent]
})
export class ImproCollaborationModule {}
