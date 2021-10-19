import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { StockUpdateComponent } from 'app/entities/stock/stock-update.component';
import { StockService } from 'app/entities/stock/stock.service';
import { Stock } from 'app/shared/model/stock.model';

describe('Component Tests', () => {
  describe('Stock Management Update Component', () => {
    let comp: StockUpdateComponent;
    let fixture: ComponentFixture<StockUpdateComponent>;
    let service: StockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [StockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Stock(123);
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
        const entity = new Stock();
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
