import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { DocIdentificationPMDetailComponent } from 'app/entities/doc-identification-pm/doc-identification-pm-detail.component';
import { DocIdentificationPM } from 'app/shared/model/doc-identification-pm.model';

describe('Component Tests', () => {
  describe('DocIdentificationPM Management Detail Component', () => {
    let comp: DocIdentificationPMDetailComponent;
    let fixture: ComponentFixture<DocIdentificationPMDetailComponent>;
    const route = ({ data: of({ docIdentificationPM: new DocIdentificationPM(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [DocIdentificationPMDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DocIdentificationPMDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocIdentificationPMDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load docIdentificationPM on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.docIdentificationPM).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
