/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { PersonalComponent } from 'app/entities/personal/personal.component';
import { PersonalService } from 'app/entities/personal/personal.service';
import { Personal } from 'app/shared/model/personal.model';

describe('Component Tests', () => {
    describe('Personal Management Component', () => {
        let comp: PersonalComponent;
        let fixture: ComponentFixture<PersonalComponent>;
        let service: PersonalService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [PersonalComponent],
                providers: []
            })
                .overrideTemplate(PersonalComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Personal(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
