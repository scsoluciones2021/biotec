import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Categoriascie10DetailComponent } from 'app/entities/categoriascie10/categoriascie10-detail.component';
import { Categoriascie10 } from 'app/shared/model/categoriascie10.model';

describe('Component Tests', () => {
    describe('Categoriascie10 Management Detail Component', () => {
        let comp: Categoriascie10DetailComponent;
        let fixture: ComponentFixture<Categoriascie10DetailComponent>;
        const route = ({ data: of({ categoriascie10: new Categoriascie10(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Categoriascie10DetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Categoriascie10DetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Categoriascie10DetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should load categoriascie10 on init', () => {
                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.categoriascie10).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
