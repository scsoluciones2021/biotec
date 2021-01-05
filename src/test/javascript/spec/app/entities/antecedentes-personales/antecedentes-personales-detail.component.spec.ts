/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesPersonalesDetailComponent } from 'app/entities/antecedentes-personales/antecedentes-personales-detail.component';
import { AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

describe('Component Tests', () => {
    describe('AntecedentesPersonales Management Detail Component', () => {
        let comp: AntecedentesPersonalesDetailComponent;
        let fixture: ComponentFixture<AntecedentesPersonalesDetailComponent>;
        const route = ({ data: of({ antecedentesPersonales: new AntecedentesPersonales(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesPersonalesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AntecedentesPersonalesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AntecedentesPersonalesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.antecedentesPersonales).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
