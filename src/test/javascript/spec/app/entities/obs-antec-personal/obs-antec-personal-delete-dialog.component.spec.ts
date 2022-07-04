/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { ObsAntecPersonalDeleteDialogComponent } from 'app/entities/obs-antec-personal/obs-antec-personal-delete-dialog.component';
import { ObsAntecPersonalService } from 'app/entities/obs-antec-personal/obs-antec-personal.service';

describe('Component Tests', () => {
    describe('ObsAntecPersonal Management Delete Component', () => {
        let comp: ObsAntecPersonalDeleteDialogComponent;
        let fixture: ComponentFixture<ObsAntecPersonalDeleteDialogComponent>;
        let service: ObsAntecPersonalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [ObsAntecPersonalDeleteDialogComponent]
            })
                .overrideTemplate(ObsAntecPersonalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ObsAntecPersonalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObsAntecPersonalService);
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
