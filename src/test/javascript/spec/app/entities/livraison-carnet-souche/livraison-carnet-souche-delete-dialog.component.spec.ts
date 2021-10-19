import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ImproTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { LivraisonCarnetSoucheDeleteDialogComponent } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche-delete-dialog.component';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';

describe('Component Tests', () => {
  describe('LivraisonCarnetSouche Management Delete Component', () => {
    let comp: LivraisonCarnetSoucheDeleteDialogComponent;
    let fixture: ComponentFixture<LivraisonCarnetSoucheDeleteDialogComponent>;
    let service: LivraisonCarnetSoucheService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonCarnetSoucheDeleteDialogComponent]
      })
        .overrideTemplate(LivraisonCarnetSoucheDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivraisonCarnetSoucheDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonCarnetSoucheService);
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