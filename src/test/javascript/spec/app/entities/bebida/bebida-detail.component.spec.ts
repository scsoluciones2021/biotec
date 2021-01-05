/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { BebidaDetailComponent } from 'app/entities/bebida/bebida-detail.component';
import { Bebida } from 'app/shared/model/bebida.model';

describe('Component Tests', () => {
    describe('Bebida Management Detail Component', () => {
        let comp: BebidaDetailComponent;
        let fixture: ComponentFixture<BebidaDetailComponent>;
        const route = ({ data: of({ bebida: new Bebida(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BebidaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BebidaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BebidaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bebida).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
