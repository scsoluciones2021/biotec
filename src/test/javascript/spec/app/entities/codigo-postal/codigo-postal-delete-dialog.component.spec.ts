/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { CodigoPostalDeleteDialogComponent } from 'app/entities/codigo-postal/codigo-postal-delete-dialog.component';
import { CodigoPostalService } from 'app/entities/codigo-postal/codigo-postal.service';

describe('Component Tests', () => {
    describe('CodigoPostal Management Delete Component', () => {
        let comp: CodigoPostalDeleteDialogComponent;
        let fixture: ComponentFixture<CodigoPostalDeleteDialogComponent>;
        let service: CodigoPostalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [CodigoPostalDeleteDialogComponent]
            })
                .overrideTemplate(CodigoPostalDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CodigoPostalDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CodigoPostalService);
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
