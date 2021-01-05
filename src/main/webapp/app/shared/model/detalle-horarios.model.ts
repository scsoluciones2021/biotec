export interface IDetalleHorarios {
    id?: number;
    idHorario?: number;
    horaDesde?: string;
    horaHasta?: string;
    lunes?: number;
    martes?: number;
    miercoles?: number;
    jueves?: number;
    viernes?: number;
    sabado?: number;
    domingo?: number;
    intervalo?: number;
    frecuencia?: number;
    cantidadPacientes?: number;
}

export class DetalleHorarios implements IDetalleHorarios {
    constructor(
        public id?: number,
        public idHorario?: number,
        public horaDesde?: string,
        public horaHasta?: string,
        public lunes?: number,
        public martes?: number,
        public miercoles?: number,
        public jueves?: number,
        public viernes?: number,
        public sabado?: number,
        public domingo?: number,
        public intervalo?: number,
        public frecuencia?: number,
        public cantidadPacientes?: number
    ) {}
}
