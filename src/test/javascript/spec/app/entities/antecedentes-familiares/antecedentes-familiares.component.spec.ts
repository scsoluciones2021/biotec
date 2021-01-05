/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesFamiliaresComponent } from 'app/entities/antecedentes-familiares/antecedentes-familiares.component';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares/antecedentes-familiares.service';
import { AntecedentesFamiliares } from 'app/shared/model/antecedentes-familiares.model';

describe('Component Tests', () => {
    describe('AntecedentesFamiliares Management Component', () => {
        let comp: AntecedentesFamiliaresComponent;
        let fixture: ComponentFixture<AntecedentesFamiliaresComponent>;
        let service: AntecedentesFamiliaresService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesFamiliaresComponent],
                providers: []
            })
                .overrideTemplate(AntecedentesFamiliaresComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AntecedentesFamiliaresComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesFamiliaresService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AntecedentesFamiliares(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.antecedentesFamiliares[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
