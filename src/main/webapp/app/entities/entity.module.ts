import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CpsjCodigoPostalModule } from './codigo-postal/codigo-postal.module';
import { CpsjProvinciaModule } from './provincia/provincia.module';
import { CpsjObraSocialModule } from './obra-social/obra-social.module';
import { CpsjTituloShortModule } from './titulo-short/titulo-short.module';
import { CpsjProfesionalModule } from './profesional/profesional.module';
import { CpsjEspecialidadModule } from './especialidad/especialidad.module';
import { CpsjPacienteModule } from './paciente/paciente.module';
import { CpsjEnfermedadModule } from './enfermedad/enfermedad.module';
import { CpsjAlergiaModule } from './alergia/alergia.module';
import { CpsjIntoleranciaModule } from './intolerancia/intolerancia.module';
import { CpsjRegimenModule } from './regimen/regimen.module';
import { CpsjBebidaModule } from './bebida/bebida.module';
import { CpsjEjercicioModule } from './ejercicio/ejercicio.module';
import { CpsjAntecedentesPersonalesModule } from './antecedentes-personales/antecedentes-personales.module';
import { CpsjAntecedentesFamiliaresModule } from './antecedentes-familiares/antecedentes-familiares.module';
import { CpsjConstantesModule } from './constantes/constantes.module';
import { CpsjConsultaModule } from './consulta/consulta.module';
import { CpsjFichaModule } from './ficha/ficha.module';
import { CpsjPersonalModule } from './personal/personal.module';
import { CpsjEmpresaModule } from './empresa/empresa.module';
import { CpsjObsAntecFamiliarModule } from './obs-antec-familiar/obs-antec-familiar.module';
import { CpsjObsAntecPersonalModule } from './obs-antec-personal/obs-antec-personal.module';
import { CpsjDiagnosticoModule } from './diagnostico/diagnostico.module';
import { CpsjFamiliarModule } from './familiar/familiar.module';
import { CpsjAdjuntosFichaModule } from './adjuntos-ficha/adjuntos-ficha.module';
import { CpsjEstadosModule } from './estados/estados.module';
import { CpsjHorariosProfesionalModule } from './horarios-profesional/horarios-profesional.module';
import { CpsjBloqueosModule } from './bloqueos/bloqueos.module';
import { ConsultoriosPrivadosSanJustoTurnoModule } from './turno/turno.module';
import { CpsjCalendarioModule } from './calendario/calendario.module';
import { CpsjAgrupadorOSModule } from './agrupador-os/agrupador-os.module';
import { CpsjDetalleHorariosModule } from './detalle-horarios/detalle-horarios.module';
import { CpsjPagoConsultaModule } from './pago-consulta/pago-consulta.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CpsjCodigoPostalModule,
        CpsjProvinciaModule,
        CpsjObraSocialModule,
        CpsjTituloShortModule,
        CpsjProfesionalModule,
        CpsjEspecialidadModule,
        CpsjPacienteModule,
        CpsjEnfermedadModule,
        CpsjAlergiaModule,
        CpsjIntoleranciaModule,
        CpsjRegimenModule,
        CpsjBebidaModule,
        CpsjEjercicioModule,
        CpsjAntecedentesPersonalesModule,
        CpsjAntecedentesFamiliaresModule,
        CpsjConstantesModule,
        CpsjConsultaModule,
        CpsjFichaModule,
        CpsjPersonalModule,
        CpsjEmpresaModule,
        CpsjObsAntecFamiliarModule,
        CpsjObsAntecPersonalModule,
        CpsjDiagnosticoModule,
        CpsjFamiliarModule,
        CpsjAdjuntosFichaModule,
        CpsjEstadosModule,
        CpsjHorariosProfesionalModule,
        CpsjBloqueosModule,
        ConsultoriosPrivadosSanJustoTurnoModule,
        CpsjCalendarioModule,
        CpsjAgrupadorOSModule,
        CpsjDetalleHorariosModule,
        CpsjPagoConsultaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CpsjEntityModule {}
