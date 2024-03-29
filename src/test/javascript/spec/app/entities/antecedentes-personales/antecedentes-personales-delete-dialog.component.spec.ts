/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GestWebTestModule } from '../../../test.module';
import { AntecedentesPersonalesDeleteDialogComponent } from 'app/entities/antecedentes-personales/antecedentes-personales-delete-dialog.component';
import { AntecedentesPersonalesService } from 'app/entities/antecedentes-personales/antecedentes-personales.service';

describe('Component Tests', () => {
    describe('AntecedentesPersonales Management Delete Component', () => {
        let comp: AntecedentesPersonalesDeleteDialogComponent;
        let fixture: ComponentFixture<AntecedentesPersonalesDeleteDialogComponent>;
        let service: AntecedentesPersonalesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GestWebTestModule],
                declarations: [AntecedentesPersonalesDeleteDialogComponent]
            })
                .overrideTemplate(AntecedentesPersonalesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AntecedentesPersonalesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesPersonalesService);
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
