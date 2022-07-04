/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { AgrupadorOSDetailComponent } from 'app/entities/agrupador-os/agrupador-os-detail.component';
import { AgrupadorOS } from 'app/shared/model/agrupador-os.model';

describe('Component Tests', () => {
    describe('AgrupadorOS Management Detail Component', () => {
        let comp: AgrupadorOSDetailComponent;
        let fixture: ComponentFixture<AgrupadorOSDetailComponent>;
        const route = ({ data: of({ agrupadorOS: new AgrupadorOS(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [AgrupadorOSDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AgrupadorOSDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AgrupadorOSDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.agrupadorOS).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
