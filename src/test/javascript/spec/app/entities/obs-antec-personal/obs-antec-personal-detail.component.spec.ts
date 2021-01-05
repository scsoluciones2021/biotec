/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ObsAntecPersonalDetailComponent } from 'app/entities/obs-antec-personal/obs-antec-personal-detail.component';
import { ObsAntecPersonal } from 'app/shared/model/obs-antec-personal.model';

describe('Component Tests', () => {
    describe('ObsAntecPersonal Management Detail Component', () => {
        let comp: ObsAntecPersonalDetailComponent;
        let fixture: ComponentFixture<ObsAntecPersonalDetailComponent>;
        const route = ({ data: of({ obsAntecPersonal: new ObsAntecPersonal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ObsAntecPersonalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ObsAntecPersonalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObsAntecPersonalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.obsAntecPersonal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
