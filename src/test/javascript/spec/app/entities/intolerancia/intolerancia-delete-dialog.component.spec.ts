/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { IntoleranciaDeleteDialogComponent } from 'app/entities/intolerancia/intolerancia-delete-dialog.component';
import { IntoleranciaService } from 'app/entities/intolerancia/intolerancia.service';

describe('Component Tests', () => {
    describe('Intolerancia Management Delete Component', () => {
        let comp: IntoleranciaDeleteDialogComponent;
        let fixture: ComponentFixture<IntoleranciaDeleteDialogComponent>;
        let service: IntoleranciaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [IntoleranciaDeleteDialogComponent]
            })
                .overrideTemplate(IntoleranciaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IntoleranciaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntoleranciaService);
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
