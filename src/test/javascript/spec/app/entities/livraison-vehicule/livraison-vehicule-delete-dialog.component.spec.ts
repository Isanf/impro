import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ImproTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { LivraisonVehiculeDeleteDialogComponent } from 'app/entities/livraison-vehicule/livraison-vehicule-delete-dialog.component';
import { LivraisonVehiculeService } from 'app/entities/livraison-vehicule/livraison-vehicule.service';

describe('Component Tests', () => {
  describe('LivraisonVehicule Management Delete Component', () => {
    let comp: LivraisonVehiculeDeleteDialogComponent;
    let fixture: ComponentFixture<LivraisonVehiculeDeleteDialogComponent>;
    let service: LivraisonVehiculeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonVehiculeDeleteDialogComponent]
      })
        .overrideTemplate(LivraisonVehiculeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonVehiculeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonVehiculeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
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
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
