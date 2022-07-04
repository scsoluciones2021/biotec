import { Component, OnInit, HostListener } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';

import { Title } from '@angular/platform-browser';

import { Principal, LoginModalService } from 'app/core';

import { MainService } from './main.service';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    notRegistering: boolean;
    sliderImagenes: String[];
    images: any[];
    public innerWidth: any;
    modalRef: NgbModalRef;

    constructor(
        private mainService: MainService,
        private principal: Principal,
        private titleService: Title,
        private router: Router,
        private loginModalService: LoginModalService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper
    ) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'GestWebApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        this.notRegistering = false;
        if (title === 'Registro' || title === 'Password' || (title === 'Activación' && !this.isAuthenticated())) {
            this.notRegistering = true;
        }
        return title;
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
        this.innerWidth = window.innerWidth;
    }

    ngOnInit() {
        this.innerWidth = window.innerWidth;
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.titleService.setTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
        });

        this.images = [];

        // if (window.innerWidth < 768) {
        //     this.images.push({
        //         source: './content/images/carrusel/DSC_6318_k9l6zb_c_scale,w_772.jpg',
        //         alt: 'Consultorio Kinesiología',
        //         title: ''
        //     });
        //     this.images.push({
        //         source: './content/images/carrusel/DSC_6363_pa5tuk_c_scale,w_603.jpg',
        //         alt: 'Consultorio Kinesiología',
        //         title: ''
        //     });
        //     this.images.push({
        //         source: './content/images/carrusel/DSC_6313_godfzt_c_scale,w_649.jpg',
        //         alt: 'Consultorio Kinesiología',
        //         title: ''
        //     });
        //     this.images.push({
        //         source: './content/images/carrusel/DSC_6306_dikau0_c_scale,w_626.jpg',
        //         alt: 'Consultorio Kinesiología',
        //         title: ''
        //     });
        // } else {
        //     this.images.push({ source: './content/images/carrusel/DSC_6363.jpg', alt: 'Consultorio Kinesiología', title: '' });
        //     this.images.push({ source: './content/images/carrusel/DSC_6306.jpg', alt: 'Segundo Slide', title: '' });
        //     this.images.push({ source: './content/images/carrusel/DSC_6313.jpg', alt: 'Tercer Slide', title: '' });
        //     this.images.push({ source: './content/images/carrusel/DSC_6318.jpg', alt: 'Cuarto Slide', title: '' });
        // }
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
