/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AlergiaDetailComponent } from 'app/entities/alergia/alergia-detail.component';
import { Alergia } from 'app/shared/model/alergia.model';

describe('Component Tests', () => {
    describe('Alergia Management Detail Component', () => {
        let comp: AlergiaDetailComponent;
        let fixture: ComponentFixture<AlergiaDetailComponent>;
        const route = ({ data: of({ alergia: new Alergia(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AlergiaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AlergiaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlergiaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.alergia).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
