import { NgModule } from '@angular/core';

import { GestWebSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [GestWebSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [GestWebSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class GestWebSharedCommonModule {}
