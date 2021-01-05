/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { EnfermedadDeleteDialogComponent } from 'app/entities/enfermedad/enfermedad-delete-dialog.component';
import { EnfermedadService } from 'app/entities/enfermedad/enfermedad.service';

describe('Component Tests', () => {
    describe('Enfermedad Management Delete Component', () => {
        let comp: EnfermedadDeleteDialogComponent;
        let fixture: ComponentFixture<EnfermedadDeleteDialogComponent>;
        let service: EnfermedadService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [EnfermedadDeleteDialogComponent]
            })
                .overrideTemplate(EnfermedadDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnfermedadDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnfermedadService);
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
