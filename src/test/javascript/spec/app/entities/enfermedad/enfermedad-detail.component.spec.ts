/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { EnfermedadDetailComponent } from 'app/entities/enfermedad/enfermedad-detail.component';
import { Enfermedad } from 'app/shared/model/enfermedad.model';

describe('Component Tests', () => {
    describe('Enfermedad Management Detail Component', () => {
        let comp: EnfermedadDetailComponent;
        let fixture: ComponentFixture<EnfermedadDetailComponent>;
        const route = ({ data: of({ enfermedad: new Enfermedad(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EnfermedadDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnfermedadDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnfermedadDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.enfermedad).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
