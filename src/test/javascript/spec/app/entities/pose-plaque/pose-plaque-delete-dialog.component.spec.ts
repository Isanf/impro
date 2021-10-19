import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ImproTestModule } from '../../../test.module';
import { PosePlaqueDeleteDialogComponent } from 'app/entities/pose-plaque/pose-plaque-delete-dialog.component';
import { PosePlaqueService } from 'app/entities/pose-plaque/pose-plaque.service';

describe('Component Tests', () => {
  describe('PosePlaque Management Delete Component', () => {
    let comp: PosePlaqueDeleteDialogComponent;
    let fixture: ComponentFixture<PosePlaqueDeleteDialogComponent>;
    let service: PosePlaqueService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [PosePlaqueDeleteDialogComponent]
      })
        .overrideTemplate(PosePlaqueDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PosePlaqueDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PosePlaqueService);
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
