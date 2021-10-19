import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { LivraisonCarnetSoucheUpdateComponent } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche-update.component';
import { LivraisonCarnetSoucheService } from 'app/entities/livraison-carnet-souche/livraison-carnet-souche.service';
import { LivraisonCarnetSouche } from 'app/shared/model/livraison-carnet-souche.model';

describe('Component Tests', () => {
  describe('LivraisonCarnetSouche Management Update Component', () => {
    let comp: LivraisonCarnetSoucheUpdateComponent;
    let fixture: ComponentFixture<LivraisonCarnetSoucheUpdateComponent>;
    let service: LivraisonCarnetSoucheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [LivraisonCarnetSoucheUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LivraisonCarnetSoucheUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivraisonCarnetSoucheUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivraisonCarnetSoucheService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LivraisonCarnetSouche(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new LivraisonCarnetSouche();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
