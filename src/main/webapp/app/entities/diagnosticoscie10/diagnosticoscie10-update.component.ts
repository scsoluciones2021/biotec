import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiagnosticoscie10, Diagnosticoscie10 } from 'app/shared/model/diagnosticoscie10.model';
import { Diagnosticoscie10Service } from './diagnosticoscie10.service';
import { ICategoriascie10 } from 'app/shared/model/categoriascie10.model';
import { Categoriascie10Service } from 'app/entities/categoriascie10/categoriascie10.service';

@Component({
    selector: 'jhi-diagnosticoscie10-update',
    templateUrl: './diagnosticoscie10-update.component.html'
})
export class Diagnosticoscie10UpdateComponent implements OnInit {
    isSaving = false;
    categoriascie10s: ICategoriascie10[] = [];

    editForm = this.fb.group({
        id: [],
        clave: [null, [Validators.required]],
        descripcion: [null, [Validators.required]],
        idcategoria: [null, [Validators.required]],
        rel_categorias_diagnosticos_cie10: []
    });

    constructor(
        protected diagnosticoscie10Service: Diagnosticoscie10Service,
        protected categoriascie10Service: Categoriascie10Service,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ diagnosticoscie10 }) => {
            this.updateForm(diagnosticoscie10);

            this.categoriascie10Service
                .query()
                .subscribe((res: HttpResponse<ICategoriascie10[]>) => (this.categoriascie10s = res.body || []));
        });
    }

    updateForm(diagnosticoscie10: IDiagnosticoscie10): void {
        this.editForm.patchValue({
            id: diagnosticoscie10.id,
            clave: diagnosticoscie10.clave,
            descripcion: diagnosticoscie10.descripcion,
            idcategoria: diagnosticoscie10.idcategoria,
            rel_categorias_diagnosticos_cie10: diagnosticoscie10.rel_categorias_diagnosticos_cie10
        });
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        const diagnosticoscie10 = this.createFromForm();
        if (diagnosticoscie10.id !== undefined) {
            this.subscribeToSaveResponse(this.diagnosticoscie10Service.update(diagnosticoscie10));
        } else {
            this.subscribeToSaveResponse(this.diagnosticoscie10Service.create(diagnosticoscie10));
        }
    }

    private createFromForm(): IDiagnosticoscie10 {
        return {
            ...new Diagnosticoscie10(),
            id: this.editForm.get(['id'])!.value,
            clave: this.editForm.get(['clave'])!.value,
            descripcion: this.editForm.get(['descripcion'])!.value,
            idcategoria: this.editForm.get(['idcategoria'])!.value,
            rel_categorias_diagnosticos_cie10: this.editForm.get(['rel_categorias_diagnosticos_cie10'])!.value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiagnosticoscie10>>): void {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(): void {
        this.isSaving = false;
    }

    trackById(index: number, item: ICategoriascie10): any {
        return item.id;
    }
}
