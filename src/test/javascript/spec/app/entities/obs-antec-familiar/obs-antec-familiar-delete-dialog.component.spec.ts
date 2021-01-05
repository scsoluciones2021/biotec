/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { ObsAntecFamiliarDeleteDialogComponent } from 'app/entities/obs-antec-familiar/obs-antec-familiar-delete-dialog.component';
import { ObsAntecFamiliarService } from 'app/entities/obs-antec-familiar/obs-antec-familiar.service';

describe('Component Tests', () => {
    describe('ObsAntecFamiliar Management Delete Component', () => {
        let comp: ObsAntecFamiliarDeleteDialogComponent;
        let fixture: ComponentFixture<ObsAntecFamiliarDeleteDialogComponent>;
        let service: ObsAntecFamiliarService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [ObsAntecFamiliarDeleteDialogComponent]
            })
                .overrideTemplate(ObsAntecFamiliarDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObsAntecFamiliarDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObsAntecFamiliarService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
