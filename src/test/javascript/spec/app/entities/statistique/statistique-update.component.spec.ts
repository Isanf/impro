import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { StatistiqueUpdateComponent } from 'app/entities/statistique/statistique-update.component';
import { StatistiqueService } from 'app/entities/statistique/statistique.service';
import { Statistique } from 'app/shared/model/statistique.model';

describe('Component Tests', () => {
  describe('Statistique Management Update Component', () => {
    let comp: StatistiqueUpdateComponent;
    let fixture: ComponentFixture<StatistiqueUpdateComponent>;
    let service: StatistiqueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [StatistiqueUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StatistiqueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatistiqueUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StatistiqueService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Statistique(123);
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
        const entity = new Statistique();
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
