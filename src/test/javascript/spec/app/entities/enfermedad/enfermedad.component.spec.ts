/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { EnfermedadComponent } from 'app/entities/enfermedad/enfermedad.component';
import { EnfermedadService } from 'app/entities/enfermedad/enfermedad.service';
import { Enfermedad } from 'app/shared/model/enfermedad.model';

describe('Component Tests', () => {
    describe('Enfermedad Management Component', () => {
        let comp: EnfermedadComponent;
        let fixture: ComponentFixture<EnfermedadComponent>;
        let service: EnfermedadService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EnfermedadComponent],
                providers: []
            })
                .overrideTemplate(EnfermedadComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnfermedadComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnfermedadService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Enfermedad(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.enfermedads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
