import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { CpsjSharedLibsModule, CpsjSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, FindLanguageFromKeyPipe } from './';

@NgModule({
    imports: [CpsjSharedLibsModule, CpsjSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, FindLanguageFromKeyPipe],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [CpsjSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjSharedModule {}
