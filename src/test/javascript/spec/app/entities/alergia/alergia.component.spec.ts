/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { AlergiaComponent } from 'app/entities/alergia/alergia.component';
import { AlergiaService } from 'app/entities/alergia/alergia.service';
import { Alergia } from 'app/shared/model/alergia.model';

describe('Component Tests', () => {
    describe('Alergia Management Component', () => {
        let comp: AlergiaComponent;
        let fixture: ComponentFixture<AlergiaComponent>;
        let service: AlergiaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AlergiaComponent],
                providers: []
            })
                .overrideTemplate(AlergiaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlergiaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlergiaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Alergia(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.alergias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
