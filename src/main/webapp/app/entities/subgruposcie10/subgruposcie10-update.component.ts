import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubgruposcie10, Subgruposcie10 } from 'app/shared/model/subgruposcie10.model';
import { Subgruposcie10Service } from './subgruposcie10.service';
import { IGruposcie10 } from 'app/shared/model/gruposcie10.model';
import { Gruposcie10Service } from 'app/entities/gruposcie10/gruposcie10.service';

@Component({
    selector: 'jhi-subgruposcie10-update',
    templateUrl: './subgruposcie10-update.component.html'
})
export class Subgruposcie10UpdateComponent implements OnInit {
    isSaving = false;
    gruposcie10s: IGruposcie10[] = [];

    editForm = this.fb.group({
        id: [],
        clave: [null, [Validators.required]],
        descripcion: [null, [Validators.required]],
        idGrupo: [null, [Validators.required]],
        rel_grupo_subgrupo_cie10: []
    });

    constructor(
        protected subgruposcie10Service: Subgruposcie10Service,
        protected gruposcie10Service: Gruposcie10Service,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ subgruposcie10 }) => {
            this.updateForm(subgruposcie10);

            this.gruposcie10Service.query().subscribe((res: HttpResponse<IGruposcie10[]>) => (this.gruposcie10s = res.body || []));
        });
    }

    updateForm(subgruposcie10: ISubgruposcie10): void {
        this.editForm.patchValue({
            id: subgruposcie10.id,
            clave: subgruposcie10.clave,
            descripcion: subgruposcie10.descripcion,
            idGrupo: subgruposcie10.idGrupo,
            rel_grupo_subgrupo_cie10: subgruposcie10.rel_grupo_subgrupo_cie10
        });
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        const subgruposcie10 = this.createFromForm();
        if (subgruposcie10.id !== undefined) {
            this.subscribeToSaveResponse(this.subgruposcie10Service.update(subgruposcie10));
        } else {
            this.subscribeToSaveResponse(this.subgruposcie10Service.create(subgruposcie10));
        }
    }

    private createFromForm(): ISubgruposcie10 {
        return {
            ...new Subgruposcie10(),
            id: this.editForm.get(['id'])!.value,
            clave: this.editForm.get(['clave'])!.value,
            descripcion: this.editForm.get(['descripcion'])!.value,
            idGrupo: this.editForm.get(['idGrupo'])!.value,
            rel_grupo_subgrupo_cie10: this.editForm.get(['rel_grupo_subgrupo_cie10'])!.value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubgruposcie10>>): void {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(): void {
        this.isSaving = false;
    }

    trackById(index: number, item: IGruposcie10): any {
        return item.id;
    }
}
