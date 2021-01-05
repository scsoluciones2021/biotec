import { Component, OnInit } from '@angular/core';
import { animation } from '@angular/animations';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
    anio: String;

    constructor() {}

    ngOnInit() {
        const an = new Date();
        this.anio = an.getFullYear().toString();
    }
}
