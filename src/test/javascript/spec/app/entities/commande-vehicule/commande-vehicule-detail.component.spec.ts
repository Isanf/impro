import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ImproTestModule } from '../../../test.module';
import { CommandeVehiculeDetailComponent } from 'app/entities/commande-vehicule/commande-vehicule-detail.component';
import { CommandeVehicule } from 'app/shared/model/commande-vehicule.model';

describe('Component Tests', () => {
  describe('CommandeVehicule Management Detail Component', () => {
    let comp: CommandeVehiculeDetailComponent;
    let fixture: ComponentFixture<CommandeVehiculeDetailComponent>;
    const route = ({ data: of({ commandeVehicule: new CommandeVehicule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ImproTestModule],
        declarations: [CommandeVehiculeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommandeVehiculeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandeVehiculeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandeVehicule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandeVehicule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
