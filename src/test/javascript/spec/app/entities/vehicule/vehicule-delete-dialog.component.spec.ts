import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ImproTestModule } from '../../../test.module';
import { VehiculeDeleteDialogComponent } from 'app/entities/vehicule/vehicule-delete-dialog.component';
import { VehiculeService } from 'app/entities/vehicule/vehicule.service';

describe('Component Tests', () => {
  describe('Vehicule Management Delete Component', () => {
    let comp: VehiculeDeleteDialogComponent;
    let fixture: ComponentFixture<VehiculeDeleteDialogComponent>;
    let service: VehiculeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [VehiculeDeleteDialogComponent]
      })
        .overrideTemplate(VehiculeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VehiculeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VehiculeService);
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
