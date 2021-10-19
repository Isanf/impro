import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CollaborationDetailComponent } from 'app/entities/collaboration/collaboration-detail.component';
import { Collaboration } from 'app/shared/model/collaboration.model';

describe('Component Tests', () => {
  describe('Collaboration Management Detail Component', () => {
    let comp: CollaborationDetailComponent;
    let fixture: ComponentFixture<CollaborationDetailComponent>;
    const route = ({ data: of({ collaboration: new Collaboration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CollaborationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollaborationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollaborationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load collaboration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collaboration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
