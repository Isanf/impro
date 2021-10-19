import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CommandeCarnetSoucheDetailComponent } from 'app/entities/commande-carnet-souche/commande-carnet-souche-detail.component';
import { CommandeCarnetSouche } from 'app/shared/model/commande-carnet-souche.model';

describe('Component Tests', () => {
  describe('CommandeCarnetSouche Management Detail Component', () => {
    let comp: CommandeCarnetSoucheDetailComponent;
    let fixture: ComponentFixture<CommandeCarnetSoucheDetailComponent>;
    const route = ({ data: of({ commandeCarnetSouche: new CommandeCarnetSouche(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CommandeCarnetSoucheDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeCarnetSoucheDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeCarnetSoucheDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeCarnetSouche on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeCarnetSouche).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
