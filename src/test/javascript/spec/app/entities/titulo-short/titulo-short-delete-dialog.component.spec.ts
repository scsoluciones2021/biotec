/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { TituloShortDeleteDialogComponent } from 'app/entities/titulo-short/titulo-short-delete-dialog.component';
import { TituloShortService } from 'app/entities/titulo-short/titulo-short.service';

describe('Component Tests', () => {
    describe('TituloShort Management Delete Component', () => {
        let comp: TituloShortDeleteDialogComponent;
        let fixture: ComponentFixture<TituloShortDeleteDialogComponent>;
        let service: TituloShortService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [TituloShortDeleteDialogComponent]
            })
                .overrideTemplate(TituloShortDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TituloShortDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TituloShortService);
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
