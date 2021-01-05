/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { AgrupadorOSDeleteDialogComponent } from 'app/entities/agrupador-os/agrupador-os-delete-dialog.component';
import { AgrupadorOSService } from 'app/entities/agrupador-os/agrupador-os.service';

describe('Component Tests', () => {
    describe('AgrupadorOS Management Delete Component', () => {
        let comp: AgrupadorOSDeleteDialogComponent;
        let fixture: ComponentFixture<AgrupadorOSDeleteDialogComponent>;
        let service: AgrupadorOSService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AgrupadorOSDeleteDialogComponent]
            })
                .overrideTemplate(AgrupadorOSDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AgrupadorOSDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgrupadorOSService);
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
