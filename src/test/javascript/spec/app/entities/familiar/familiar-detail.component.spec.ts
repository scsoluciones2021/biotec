/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CpsjTestModule } from '../../../test.module';
import { FamiliarDetailComponent } from 'app/entities/familiar/familiar-detail.component';
import { Familiar } from 'app/shared/model/familiar.model';

describe('Component Tests', () => {
    describe('Familiar Management Detail Component', () => {
        let comp: FamiliarDetailComponent;
        let fixture: ComponentFixture<FamiliarDetailComponent>;
        const route = ({ data: of({ familiar: new Familiar(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [FamiliarDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FamiliarDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FamiliarDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.familiar).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
