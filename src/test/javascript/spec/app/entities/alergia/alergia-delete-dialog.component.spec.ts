/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { AlergiaDeleteDialogComponent } from 'app/entities/alergia/alergia-delete-dialog.component';
import { AlergiaService } from 'app/entities/alergia/alergia.service';

describe('Component Tests', () => {
    describe('Alergia Management Delete Component', () => {
        let comp: AlergiaDeleteDialogComponent;
        let fixture: ComponentFixture<AlergiaDeleteDialogComponent>;
        let service: AlergiaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AlergiaDeleteDialogComponent]
            })
                .overrideTemplate(AlergiaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlergiaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlergiaService);
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
