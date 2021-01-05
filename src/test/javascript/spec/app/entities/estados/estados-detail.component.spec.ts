/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { EstadosDetailComponent } from 'app/entities/estados/estados-detail.component';
import { Estados } from 'app/shared/model/estados.model';

describe('Component Tests', () => {
    describe('Estados Management Detail Component', () => {
        let comp: EstadosDetailComponent;
        let fixture: ComponentFixture<EstadosDetailComponent>;
        const route = ({ data: of({ estados: new Estados(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EstadosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstadosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstadosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estados).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
