/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { CodigoPostalDetailComponent } from 'app/entities/codigo-postal/codigo-postal-detail.component';
import { CodigoPostal } from 'app/shared/model/codigo-postal.model';

describe('Component Tests', () => {
    describe('CodigoPostal Management Detail Component', () => {
        let comp: CodigoPostalDetailComponent;
        let fixture: ComponentFixture<CodigoPostalDetailComponent>;
        const route = ({ data: of({ codigoPostal: new CodigoPostal(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [CodigoPostalDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CodigoPostalDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CodigoPostalDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.codigoPostal).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
