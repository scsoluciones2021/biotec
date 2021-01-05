/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesFamiliaresDetailComponent } from 'app/entities/antecedentes-familiares/antecedentes-familiares-detail.component';
import { AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

describe('Component Tests', () => {
    describe('AntecedentesFamiliares Management Detail Component', () => {
        let comp: AntecedentesFamiliaresDetailComponent;
        let fixture: ComponentFixture<AntecedentesFamiliaresDetailComponent>;
        const route = ({ data: of({ antecedentesFamiliares: new AntecedentesFamiliares(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesFamiliaresDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AntecedentesFamiliaresDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AntecedentesFamiliaresDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.antecedentesFamiliares).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
