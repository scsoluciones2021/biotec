/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { IntoleranciaDetailComponent } from 'app/entities/intolerancia/intolerancia-detail.component';
import { Intolerancia } from 'app/shared/model/intolerancia.model';

describe('Component Tests', () => {
    describe('Intolerancia Management Detail Component', () => {
        let comp: IntoleranciaDetailComponent;
        let fixture: ComponentFixture<IntoleranciaDetailComponent>;
        const route = ({ data: of({ intolerancia: new Intolerancia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [IntoleranciaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IntoleranciaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IntoleranciaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.intolerancia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
