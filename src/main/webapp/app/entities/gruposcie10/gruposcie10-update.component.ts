import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGruposcie10, Gruposcie10 } from 'app/shared/model/gruposcie10.model';
import { Gruposcie10Service } from './gruposcie10.service';

@Component({
    selector: 'jhi-gruposcie10-update',
    templateUrl: './gruposcie10-update.component.html'
})
export class Gruposcie10UpdateComponent implements OnInit {
    isSaving = false;

    editForm = this.fb.group({
        id: [],
        clave: [null, [Validators.required]],
        descripcion: [null, [Validators.required]]
    });

    constructor(protected gruposcie10Service: Gruposcie10Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

    ngOnInit(): void {
        this.activatedRoute.data.subscribe(({ gruposcie10 }) => {
            this.updateForm(gruposcie10);
        });
    }

    updateForm(gruposcie10: IGruposcie10): void {
        this.editForm.patchValue({
            id: gruposcie10.id,
            clave: gruposcie10.clave,
            descripcion: gruposcie10.descripcion
        });
    }

    previousState(): void {
        window.history.back();
    }

    save(): void {
        this.isSaving = true;
        const gruposcie10 = this.createFromForm();
        if (gruposcie10.id !== undefined) {
            this.subscribeToSaveResponse(this.gruposcie10Service.update(gruposcie10));
        } else {
            this.subscribeToSaveResponse(this.gruposcie10Service.create(gruposcie10));
        }
    }

    private createFromForm(): IGruposcie10 {
        return {
            ...new Gruposcie10(),
            id: this.editForm.get(['id'])!.value,
            clave: this.editForm.get(['clave'])!.value,
            descripcion: this.editForm.get(['descripcion'])!.value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGruposcie10>>): void {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess(): void {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(): void {
        this.isSaving = false;
    }
}
