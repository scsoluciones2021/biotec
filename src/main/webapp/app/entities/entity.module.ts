import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GestWebCodigoPostalModule } from './codigo-postal/codigo-postal.module';
import { GestWebProvinciaModule } from './provincia/provincia.module';
import { GestWebObraSocialModule } from './obra-social/obra-social.module';
import { GestWebTituloShortModule } from './titulo-short/titulo-short.module';
import { GestWebProfesionalModule } from './profesional/profesional.module';
import { GestWebEspecialidadModule } from './especialidad/especialidad.module';
import { GestWebPacienteModule } from './paciente/paciente.module';
import { GestWebEnfermedadModule } from './enfermedad/enfermedad.module';
import { GestWebAlergiaModule } from './alergia/alergia.module';
import { GestWebIntoleranciaModule } from './intolerancia/intolerancia.module';
import { GestWebRegimenModule } from './regimen/regimen.module';
import { GestWebBebidaModule } from './bebida/bebida.module';
import { GestWebEjercicioModule } from './ejercicio/ejercicio.module';
import { GestWebAntecedentesPersonalesModule } from './antecedentes-personales/antecedentes-personales.module';
import { GestWebAntecedentesFamiliaresModule } from './antecedentes-familiares/antecedentes-familiares.module';
import { GestWebConstantesModule } from './constantes/constantes.module';
import { GestWebConsultaModule } from './consulta/consulta.module';
import { GestWebFichaModule } from './ficha/ficha.module';
import { GestWebPersonalModule } from './personal/personal.module';
import { GestWebEmpresaModule } from './empresa/empresa.module';
import { GestWebObsAntecFamiliarModule } from './obs-antec-familiar/obs-antec-familiar.module';
import { GestWebObsAntecPersonalModule } from './obs-antec-personal/obs-antec-personal.module';
import { GestWebDiagnosticoModule } from './diagnostico/diagnostico.module';
import { GestWebFamiliarModule } from './familiar/familiar.module';
import { GestWebAdjuntosFichaModule } from './adjuntos-ficha/adjuntos-ficha.module';
import { GestWebEstadosModule } from './estados/estados.module';
import { GestWebHorariosProfesionalModule } from './horarios-profesional/horarios-profesional.module';
import { GestWebBloqueosModule } from './bloqueos/bloqueos.module';
import { GestWebTurnoModule } from './turno/turno.module';
import { GestWebCalendarioModule } from './calendario/calendario.module';
import { GestWebAgrupadorOSModule } from './agrupador-os/agrupador-os.module';
import { GestWebDetalleHorariosModule } from './detalle-horarios/detalle-horarios.module';
import { GestWebPagoConsultaModule } from './pago-consulta/pago-consulta.module';
import { GestWebDiagnosticoscie10Module } from './diagnosticoscie10/diagnosticoscie10.module';
import { GestWebGruposcie10Module } from './gruposcie10/gruposcie10.module';
import { GestWebSubgruposcie10Module } from './subgruposcie10/subgruposcie10.module';
import { GestWebCategoriascie10Module } from './categoriascie10/categoriascie10.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import dayjs = require('dayjs');
@NgModule({
    // prettier-ignore
    imports: [
        GestWebCodigoPostalModule,
        GestWebProvinciaModule,
        GestWebObraSocialModule,
        GestWebTituloShortModule,
        GestWebProfesionalModule,
        GestWebEspecialidadModule,
        GestWebPacienteModule,
        GestWebEnfermedadModule,
        GestWebAlergiaModule,
        GestWebIntoleranciaModule,
        GestWebRegimenModule,
        GestWebBebidaModule,
        GestWebEjercicioModule,
        GestWebAntecedentesPersonalesModule,
        GestWebAntecedentesFamiliaresModule,
        GestWebConstantesModule,
        GestWebConsultaModule,
        GestWebFichaModule,
        GestWebPersonalModule,
        GestWebEmpresaModule,
        GestWebObsAntecFamiliarModule,
        GestWebObsAntecPersonalModule,
        GestWebDiagnosticoModule,
        GestWebFamiliarModule,
        GestWebAdjuntosFichaModule,
        GestWebEstadosModule,
        GestWebHorariosProfesionalModule,
        GestWebBloqueosModule,
        GestWebTurnoModule,
        GestWebCalendarioModule,
        GestWebAgrupadorOSModule,
        GestWebDetalleHorariosModule,
        GestWebPagoConsultaModule,
        GestWebGruposcie10Module,
        GestWebSubgruposcie10Module,
        GestWebCategoriascie10Module,
        GestWebDiagnosticoscie10Module,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GestWebEntityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey) {
                this.languageService.changeLanguage(languageKey);
            }
        });
        dayjs.locale('es');
    }
}
