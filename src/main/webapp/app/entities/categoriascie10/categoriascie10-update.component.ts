import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategoriascie10, Categoriascie10 } from 'app/shared/model/categoriascie10.model';
import { Categoriascie10Service } from './categoriascie10.service';
import { ISubgruposcie10 } from 'app/shared/model/subgruposcie10.model';
import { Subgruposcie10Service } from 'app/entities/subgruposcie10/subgruposcie10.service';

@Component({
    selector: 'jhi-categoriascie10-update',
    templateUrl: './categoriascie10-update.component.html'
})
export class Categoriascie10UpdateComponent implements OnInit {
    isSaving = false;
    subgruposcie10s: ISubgruposcie10[] = [];

    editForm = this.fb.group({
        id: [],
        clave: [null, [Validators.required]],
        descripcion: [null, [Validators.required]],
        idsubgrupo: [null, [Validators.required]],
        rel_subrupos_categorias_cie10: []
    });

    constructor(
        protected categoriascie10Service: Categoriascie10Service,
        protected subgruposcie10Service: Subgruposcie10Service,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ categoriascie10 }) => {
            this.updateForm(categoriascie10);

            this.subgruposcie10Service.query().subscribe((res: HttpResponse<ISubgruposcie10[]>) => (this.subgruposcie10s = res.body || []));
        });
    }

    updateForm(categoriascie10: ICategoriascie10): void {
        this.editForm.patchValue({
            id: categoriascie10.id,
            clave: categoriascie10.clave,
            descripcion: categoriascie10.descripcion,
            idsubgrupo: categoriascie10.idsubgrupo,
            rel_subrupos_categorias_cie10: categoriascie10.rel_subrupos_categorias_cie10
        });
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        const categoriascie10 = this.createFromForm();
        if (categoriascie10.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriascie10Service.update(categoriascie10));
        } else {
            this.subscribeToSaveResponse(this.categoriascie10Service.create(categoriascie10));
        }
    }

    private createFromForm(): ICategoriascie10 {
        return {
            ...new Categoriascie10(),
            id: this.editForm.get(['id'])!.value,
            clave: this.editForm.get(['clave'])!.value,
            descripcion: this.editForm.get(['descripcion'])!.value,
            idsubgrupo: this.editForm.get(['idsubgrupo'])!.value,
            rel_subrupos_categorias_cie10: this.editForm.get(['rel_subrupos_categorias_cie10'])!.value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriascie10>>): void {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(): void {
        this.isSaving = false;
    }

    trackById(index: number, item: ISubgruposcie10): any {
        return item.id;
    }
}
