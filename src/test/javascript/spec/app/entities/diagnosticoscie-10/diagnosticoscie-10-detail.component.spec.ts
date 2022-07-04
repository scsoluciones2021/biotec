import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GestWebTestModule } from '../../../test.module';
import { Diagnosticoscie10DetailComponent } from 'app/entities/diagnosticoscie10/diagnosticoscie10-detail.component';
import { Diagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';

describe('Component Tests', () => {
    describe('Diagnosticoscie10 Management Detail Component', () => {
        let comp: Diagnosticoscie10DetailComponent;
        let fixture: ComponentFixture<Diagnosticoscie10DetailComponent>;
        const route = ({ data: of({ diagnosticoscie10: new Diagnosticoscie10(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [Diagnosticoscie10DetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Diagnosticoscie10DetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Diagnosticoscie10DetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should load diagnosticoscie10 on init', () => {
                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.diagnosticoscie10).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
