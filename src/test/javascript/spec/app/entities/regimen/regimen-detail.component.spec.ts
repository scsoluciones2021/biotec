/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { RegimenDetailComponent } from 'app/entities/regimen/regimen-detail.component';
import { Regimen } from 'app/shared/model/regimen.model';

describe('Component Tests', () => {
    describe('Regimen Management Detail Component', () => {
        let comp: RegimenDetailComponent;
        let fixture: ComponentFixture<RegimenDetailComponent>;
        const route = ({ data: of({ regimen: new Regimen(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [RegimenDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegimenDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimenDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.regimen).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
