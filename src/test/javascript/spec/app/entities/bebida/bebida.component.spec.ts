/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CpsjTestModule } from '../../../test.module';
import { BebidaComponent } from 'app/entities/bebida/bebida.component';
import { BebidaService } from 'app/entities/bebida/bebida.service';
import { Bebida } from 'app/shared/model/bebida.model';

describe('Component Tests', () => {
    describe('Bebida Management Component', () => {
        let comp: BebidaComponent;
        let fixture: ComponentFixture<BebidaComponent>;
        let service: BebidaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BebidaComponent],
                providers: []
            })
                .overrideTemplate(BebidaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BebidaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BebidaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bebida(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bebidas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
