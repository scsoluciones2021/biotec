/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ObsAntecFamiliarDetailComponent } from 'app/entities/obs-antec-familiar/obs-antec-familiar-detail.component';
import { ObsAntecFamiliar } from 'app/shared/model/obs-antec-familiar.model';

describe('Component Tests', () => {
    describe('ObsAntecFamiliar Management Detail Component', () => {
        let comp: ObsAntecFamiliarDetailComponent;
        let fixture: ComponentFixture<ObsAntecFamiliarDetailComponent>;
        const route = ({ data: of({ obsAntecFamiliar: new ObsAntecFamiliar(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ObsAntecFamiliarDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ObsAntecFamiliarDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObsAntecFamiliarDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.obsAntecFamiliar).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
