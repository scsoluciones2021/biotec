/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CpsjTestModule } from '../../../test.module';
import { AntecedentesFamiliaresDeleteDialogComponent } from 'app/entities/antecedentes-familiares/antecedentes-familiares-delete-dialog.component';
import { AntecedentesFamiliaresService } from 'app/entities/antecedentes-familiares/antecedentes-familiares.service';

describe('Component Tests', () => {
    describe('AntecedentesFamiliares Management Delete Component', () => {
        let comp: AntecedentesFamiliaresDeleteDialogComponent;
        let fixture: ComponentFixture<AntecedentesFamiliaresDeleteDialogComponent>;
        let service: AntecedentesFamiliaresService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CpsjTestModule],
                declarations: [AntecedentesFamiliaresDeleteDialogComponent]
            })
                .overrideTemplate(AntecedentesFamiliaresDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AntecedentesFamiliaresDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AntecedentesFamiliaresService);
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
