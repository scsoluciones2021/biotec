import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { User, UserService } from 'app/core';
import { ProfesionalService } from 'app/entities/profesional';
import { IProfesional } from 'app/shared/model/profesional.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    isSaving: boolean;
    profesionales: IProfesional[];
    idProfesional: any;
    idProfesionalActual: any;

    constructor(
        private userService: UserService,
        private alertService: JhiAlertService,
        private route: ActivatedRoute,
        private router: Router,
        private profesionalService: ProfesionalService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;
        });
        this.authorities = [];
        this.userService.authorities().subscribe(authorities => {
            this.authorities = authorities;
        });
        this.profesionalService.todos().subscribe(profesionales => {
            this.profesionales = profesionales.body;
        });
        if (this.user.authorities != null && this.user.authorities.indexOf('ROLE_MEDICO') > -1) {
            this.profesionalService.buscarPorUsuario(this.user.id).subscribe(profesional => {
                this.idProfesional = profesional.body.id;
                this.idProfesionalActual = this.idProfesional;
            });
        }
    }

    previousState() {
        this.router.navigate(['/admin/user-management']);
    }

    save() {
        this.isSaving = true;
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(
                response => {
                    this.user = response.body;
                    if (this.user.authorities.indexOf('ROLE_MEDICO') > -1) {
                        this.profesionalService.updateUserId(this.idProfesional, this.user.id).subscribe(
                            res => {
                                this.onSaveSuccess(response);
                            },
                            () => {
                                this.onSaveError();
                            }
                        );
                    } else {
                        this.onSaveSuccess(response);
                    }
                },
                () => {
                    this.onSaveError();
                }
            );
        } else {
            this.user.langKey = 'en';
            this.userService.create(this.user).subscribe(
                response => {
                    var roleProfesional = this.user.authorities.indexOf('ROLE_MEDICO');

                    this.user = response.body;

                    if (roleProfesional > -1) {
                        this.profesionalService.updateUserId(this.idProfesional, this.user.id).subscribe(
                            res => {
                                this.onSaveSuccess(res);
                            },
                            () => {
                                this.onSaveError();
                            }
                        );
                    } else {
                        this.onSaveSuccess(response);
                    }
                },
                () => this.onSaveError()
            );
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    public filtroProfesionales(str: string) {
        if (typeof str === 'string') {
            this.profesionales = this.profesionales.filter(
                a =>
                    a.apellidoProfesional.toLowerCase().startsWith(str.toLowerCase()) ||
                    a.nombreProfesional.toLowerCase().startsWith(str.toLowerCase())
            );
        }
    }

    public checkProfesionalWithUser(idProfesionalSeleccionado: any) {
        console.log('checkProfesionalWithUser: ');

        console.log(idProfesionalSeleccionado);
        if (idProfesionalSeleccionado !== this.idProfesionalActual) {
            this.profesionalService.searchProfesionalWithoutUser(idProfesionalSeleccionado).subscribe(res => {
                if (!res.body) {
                    this.idProfesional = this.idProfesionalActual;
                    this.alertService.error(
                        'GestWebApp.usuario.messages.profesional-ocupado',
                        'El profesional seleccionado ya está asignado a otro usuario.',
                        null
                    );
                }
            });
        }
    }
}
