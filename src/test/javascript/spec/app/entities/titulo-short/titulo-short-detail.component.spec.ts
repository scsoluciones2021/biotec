/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { TituloShortDetailComponent } from 'app/entities/titulo-short/titulo-short-detail.component';
import { TituloShort } from 'app/shared/model/titulo-short.model';

describe('Component Tests', () => {
    describe('TituloShort Management Detail Component', () => {
        let comp: TituloShortDetailComponent;
        let fixture: ComponentFixture<TituloShortDetailComponent>;
        const route = ({ data: of({ tituloShort: new TituloShort(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [TituloShortDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TituloShortDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TituloShortDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tituloShort).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
