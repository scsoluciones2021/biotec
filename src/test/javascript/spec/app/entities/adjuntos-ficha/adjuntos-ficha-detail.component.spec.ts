/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AdjuntosFichaDetailComponent } from 'app/entities/adjuntos-ficha/adjuntos-ficha-detail.component';
import { AdjuntosFicha } from 'app/shared/model/adjuntos-ficha.model';

describe('Component Tests', () => {
    describe('AdjuntosFicha Management Detail Component', () => {
        let comp: AdjuntosFichaDetailComponent;
        let fixture: ComponentFixture<AdjuntosFichaDetailComponent>;
        const route = ({ data: of({ adjuntos_ficha: new AdjuntosFicha(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AdjuntosFichaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdjuntosFichaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdjuntosFichaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adjuntos_ficha).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
