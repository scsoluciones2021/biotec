/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { PagoConsultaDeleteDialogComponent } from 'app/entities/pago-consulta/pago-consulta-delete-dialog.component';
import { PagoConsultaService } from 'app/entities/pago-consulta/pago-consulta.service';

describe('Component Tests', () => {
    describe('PagoConsulta Management Delete Component', () => {
        let comp: PagoConsultaDeleteDialogComponent;
        let fixture: ComponentFixture<PagoConsultaDeleteDialogComponent>;
        let service: PagoConsultaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [PagoConsultaDeleteDialogComponent]
            })
                .overrideTemplate(PagoConsultaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagoConsultaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagoConsultaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
