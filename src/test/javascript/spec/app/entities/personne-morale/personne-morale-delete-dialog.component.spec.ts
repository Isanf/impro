import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ImproTestModule } from '../../../test.module';
import { PersonneMoraleDeleteDialogComponent } from 'app/entities/personne-morale/personne-morale-delete-dialog.component';
import { PersonneMoraleService } from 'app/entities/personne-morale/personne-morale.service';

describe('Component Tests', () => {
  describe('PersonneMorale Management Delete Component', () => {
    let comp: PersonneMoraleDeleteDialogComponent;
    let fixture: ComponentFixture<PersonneMoraleDeleteDialogComponent>;
    let service: PersonneMoraleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PersonneMoraleDeleteDialogComponent]
      })
        .overrideTemplate(PersonneMoraleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonneMoraleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonneMoraleService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
