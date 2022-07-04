/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { DetalleHorariosDeleteDialogComponent } from 'app/entities/detalle-horarios/detalle-horarios-delete-dialog.component';
import { DetalleHorariosService } from 'app/entities/detalle-horarios/detalle-horarios.service';

describe('Component Tests', () => {
    describe('DetalleHorarios Management Delete Component', () => {
        let comp: DetalleHorariosDeleteDialogComponent;
        let fixture: ComponentFixture<DetalleHorariosDeleteDialogComponent>;
        let service: DetalleHorariosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [DetalleHorariosDeleteDialogComponent]
            })
                .overrideTemplate(DetalleHorariosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetalleHorariosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleHorariosService);
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
