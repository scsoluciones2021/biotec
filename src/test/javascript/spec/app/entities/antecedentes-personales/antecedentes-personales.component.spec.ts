/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesPersonalesComponent } from 'app/entities/antecedentes-personales/antecedentes-personales.component';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales/antecedentes-personales.service';
import { AntecedentesPersonales } from 'app/shared/model/antecedentes-personales.model';

describe('Component Tests', () => {
    describe('AntecedentesPersonales Management Component', () => {
        let comp: AntecedentesPersonalesComponent;
        let fixture: ComponentFixture<AntecedentesPersonalesComponent>;
        let service: AntecedentesPersonalesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesPersonalesComponent],
                providers: []
            })
                .overrideTemplate(AntecedentesPersonalesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AntecedentesPersonalesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesPersonalesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AntecedentesPersonales(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.antecedentesPersonales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
