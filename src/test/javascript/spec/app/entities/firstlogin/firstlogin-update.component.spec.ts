import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { FirstloginUpdateComponent } from 'app/entities/firstlogin/firstlogin-update.component';
import { FirstloginService } from 'app/entities/firstlogin/firstlogin.service';
import { Firstlogin } from 'app/shared/model/firstlogin.model';

describe('Component Tests', () => {
  describe('Firstlogin Management Update Component', () => {
    let comp: FirstloginUpdateComponent;
    let fixture: ComponentFixture<FirstloginUpdateComponent>;
    let service: FirstloginService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [FirstloginUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FirstloginUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FirstloginUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FirstloginService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Firstlogin(123);
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
        const entity = new Firstlogin();
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
