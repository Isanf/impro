import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ImproTestModule } from '../../../test.module';
import { CommandeCarnetSoucheComponent } from 'app/entities/commande-carnet-souche/commande-carnet-souche.component';
import { CommandeCarnetSoucheService } from 'app/entities/commande-carnet-souche/commande-carnet-souche.service';
import { CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

describe('Component Tests', () => {
  describe('CommandeCarnetSouche Management Component', () => {
    let comp: CommandeCarnetSoucheComponent;
    let fixture: ComponentFixture<CommandeCarnetSoucheComponent>;
    let service: CommandeCarnetSoucheService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CommandeCarnetSoucheComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(CommandeCarnetSoucheComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandeCarnetSoucheComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandeCarnetSoucheService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeCarnetSouche(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeCarnetSouches && comp.commandeCarnetSouches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommandeCarnetSouche(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commandeCarnetSouches && comp.commandeCarnetSouches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
