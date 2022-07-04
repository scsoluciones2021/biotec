import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Subgruposcie10DetailComponent } from 'app/entities/subgruposcie10/subgruposcie10-detail.component';
import { Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';

describe('Component Tests', () => {
    describe('Subgruposcie10 Management Detail Component', () => {
        let comp: Subgruposcie10DetailComponent;
        let fixture: ComponentFixture<Subgruposcie10DetailComponent>;
        const route = ({ data: of({ subgruposcie10: new Subgruposcie10(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Subgruposcie10DetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Subgruposcie10DetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Subgruposcie10DetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should load subgruposcie10 on init', () => {
                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.subgruposcie10).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
