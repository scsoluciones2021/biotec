/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { RegimenComponent } from 'app/entities/regimen/regimen.component';
import { RegimenService } from 'app/entities/regimen/regimen.service';
import { Regimen } from 'app/shared/model/regimen.model';

describe('Component Tests', () => {
    describe('Regimen Management Component', () => {
        let comp: RegimenComponent;
        let fixture: ComponentFixture<RegimenComponent>;
        let service: RegimenService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [RegimenComponent],
                providers: []
            })
                .overrideTemplate(RegimenComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegimenComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimenService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Regimen(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.regimen[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
