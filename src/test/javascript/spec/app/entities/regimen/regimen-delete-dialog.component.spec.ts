/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { RegimenDeleteDialogComponent } from 'app/entities/regimen/regimen-delete-dialog.component';
import { RegimenService } from 'app/entities/regimen/regimen.service';

describe('Component Tests', () => {
    describe('Regimen Management Delete Component', () => {
        let comp: RegimenDeleteDialogComponent;
        let fixture: ComponentFixture<RegimenDeleteDialogComponent>;
        let service: RegimenService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [RegimenDeleteDialogComponent]
            })
                .overrideTemplate(RegimenDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegimenDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimenService);
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
