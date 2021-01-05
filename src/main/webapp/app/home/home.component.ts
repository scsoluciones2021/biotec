import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiLanguageService } from 'ng-jhipster';

import { LoginModalService, Principal, Account } from 'app/core';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private router: Router,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    // funciones para paciente

    solicitudTurno() {
        if (!this.isAuthenticated()) {
            this.login();
        } else {
            this.router.navigate(['/turno-online/new']);
        }
    }

    listadoTurnos() {
        if (!this.isAuthenticated()) {
            this.login();
        } else {
            this.router.navigate(['/turno-online-listado']);
        }
    }

    // Fin funciones para pacientes
    // Funciones para médico

    fichasPacientes() {
        if (!this.isAuthenticated()) {
            this.login();
        } else {
            this.router.navigate(['/ficha']);
        }
    }

    agendas() {
        if (!this.isAuthenticated()) {
            this.login();
        } else {
            this.router.navigate(['/turno-profesional']);
        }
    }

    // Fin funciones para médico
}
