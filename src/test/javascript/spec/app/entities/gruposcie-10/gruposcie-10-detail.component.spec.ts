import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Gruposcie10DetailComponent } from 'app/entities/gruposcie10/gruposcie10-detail.component';
import { Gruposcie10 } from 'app/shared/model/gruposcie10.model';

describe('Component Tests', () => {
    describe('Gruposcie10 Management Detail Component', () => {
        let comp: Gruposcie10DetailComponent;
        let fixture: ComponentFixture<Gruposcie10DetailComponent>;
        const route = ({ data: of({ gruposcie10: new Gruposcie10(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Gruposcie10DetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Gruposcie10DetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Gruposcie10DetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should load gruposcie10 on init', () => {
                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gruposcie10).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
