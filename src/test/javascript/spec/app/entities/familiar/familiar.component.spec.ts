/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { FamiliarComponent } from 'app/entities/familiar/familiar.component';
import { FamiliarService } from 'app/entities/familiar/familiar.service';
import { Familiar } from 'app/shared/model/familiar.model';

describe('Component Tests', () => {
    describe('Familiar Management Component', () => {
        let comp: FamiliarComponent;
        let fixture: ComponentFixture<FamiliarComponent>;
        let service: FamiliarService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [FamiliarComponent],
                providers: []
            })
                .overrideTemplate(FamiliarComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FamiliarComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FamiliarService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Familiar(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.familiars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
