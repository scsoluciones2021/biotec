/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { ConstantesDetailComponent } from 'app/entities/constantes/constantes-detail.component';
import { Constantes } from 'app/shared/model/constantes.model';

describe('Component Tests', () => {
    describe('Constantes Management Detail Component', () => {
        let comp: ConstantesDetailComponent;
        let fixture: ComponentFixture<ConstantesDetailComponent>;
        const route = ({ data: of({ constantes: new Constantes(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ConstantesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConstantesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConstantesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.constantes).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
