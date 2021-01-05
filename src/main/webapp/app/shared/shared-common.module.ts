import { NgModule } from '@angular/core';

import { CpsjSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CpsjSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CpsjSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CpsjSharedCommonModule {}
