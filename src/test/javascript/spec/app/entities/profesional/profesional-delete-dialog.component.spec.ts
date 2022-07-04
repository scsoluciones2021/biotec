/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { ProfesionalDeleteDialogComponent } from 'app/entities/profesional/profesional-delete-dialog.component';
import { ProfesionalService } from 'app/entities/profesional/profesional.service';

describe('Component Tests', () => {
    describe('Profesional Management Delete Component', () => {
        let comp: ProfesionalDeleteDialogComponent;
        let fixture: ComponentFixture<ProfesionalDeleteDialogComponent>;
        let service: ProfesionalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [ProfesionalDeleteDialogComponent]
            })
                .overrideTemplate(ProfesionalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProfesionalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfesionalService);
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
