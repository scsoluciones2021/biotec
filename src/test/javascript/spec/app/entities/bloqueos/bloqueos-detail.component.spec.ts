/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { BloqueosDetailComponent } from 'app/entities/bloqueos/bloqueos-detail.component';
import { Bloqueos } from 'app/shared/model/bloqueos.model';

describe('Component Tests', () => {
    describe('Bloqueos Management Detail Component', () => {
        let comp: BloqueosDetailComponent;
        let fixture: ComponentFixture<BloqueosDetailComponent>;
        const route = ({ data: of({ bloqueos: new Bloqueos(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [BloqueosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BloqueosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BloqueosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bloqueos).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
