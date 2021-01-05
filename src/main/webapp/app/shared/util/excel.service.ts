import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import * as moment from 'moment';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';

@Injectable()
export class ExcelService {
    constructor() {}

    public exportAsExcelFile(json: any[], excelFileName: string): void {
        const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json, {
            // origin: "A1",
            // dateNF: 'YYYYMMDD HH:mm:ss'
            // header: ['Id', 'DNI', 'Apellido', 'Nombre', 'Teléfono', 'Email', 'Día', 'Hora', 'IdH', 'TP', 'TE', 'TE2', 'Prof', 'Prof2', 'To', 'To2'],
            // skipHeader: true
        });
        /* The first column header is stored in cell A1  
const cellId = worksheet['A1'];
const cellDni = worksheet['B1'];
const cellApe = worksheet['C1'];
const cellNom = worksheet['D1'];
const cellTel = worksheet['E1'];
const cellEmail= worksheet['F1'];
const cellDia = worksheet['G1'];
const cellHora = worksheet['H1'];
const cellEsp = worksheet['L1'];
const cellProf = worksheet['N1']; */
        /* To assign a new value, set the `v` key and clobber the old formatted text */
        // Headers
        /* Original
    delete worksheet['A1'].w; worksheet['A1'].v = "ID";
    delete worksheet['B1'].w; worksheet['B1'].v = "DNI";
    delete worksheet['C1'].w; worksheet['C1'].v = "Apellido";
    delete worksheet['D1'].w; worksheet['D1'].v = "Nombre";
    delete worksheet['E1'].w; worksheet['E1'].v = "Teléfono";
    delete worksheet['F1'].w; worksheet['F1'].v = "Email";
    delete worksheet['G1'].w; worksheet['G1'].v = "Día";
    delete worksheet['H1'].w; worksheet['H1'].v = "Hora";
    delete worksheet['I1'].w; worksheet['I1'].v = "Especialidad";
    delete worksheet['J1'].w; worksheet['J1'].v = "Profesional";

    */

        /* delete worksheet['A1'].w;
        worksheet['A1'].v = 'DNI';
        delete worksheet['B1'].w;
        worksheet['B1'].v = 'Apellido';
        delete worksheet['C1'].w;
        worksheet['C1'].v = 'Nombre';
        delete worksheet['D1'].w;
        worksheet['D1'].v = 'Teléfono';
        delete worksheet['E1'].w;
        worksheet['E1'].v = 'Día';
        delete worksheet['F1'].w;
        worksheet['F1'].v = 'Hora';
        delete worksheet['G1'].w;
        worksheet['G1'].v = 'Profesional';
        delete worksheet['H1'].w;
        worksheet['H1'].v = 'Obra Social';
        delete worksheet['I1'].w;
        worksheet['I1'].v = 'Monto $';
        delete worksheet['J1'].w;
        worksheet['J1'].v = 'Observaciones';
        delete worksheet['K1'].w;
        worksheet['K1'].v = 'Cupones Ob. Soc.';
*/

        delete worksheet['A1'].w;
        worksheet['A1'].v = 'Dia';
        delete worksheet['B1'].w;
        worksheet['B1'].v = 'Hora';
        delete worksheet['C1'].w;
        worksheet['C1'].v = 'Apellido';
        delete worksheet['D1'].w;
        worksheet['D1'].v = 'Nombre';
        delete worksheet['E1'].w;
        worksheet['E1'].v = 'DNI';
        delete worksheet['F1'].w;
        worksheet['F1'].v = 'Obra Social';
        delete worksheet['G1'].w;
        worksheet['G1'].v = 'Monto $';
        delete worksheet['H1'].w;
        worksheet['H1'].v = 'Observaciones';

        // Cambiamos el formato de la fecha
        var range = XLSX.utils.decode_range(worksheet['!ref']);

        for (var r = range.s.r; r <= range.e.r; r++) {
            // Recorremos las filas
            for (var c = range.s.c; c <= range.e.c; c++) {
                // Recorremos las columnas
                // Si es la columna del día
                //if (c == 4 && r != 0) {
                if (c == 0 && r != 0) {
                    var cellName = XLSX.utils.encode_cell({ c: c, r: r }); // Ej: G2, G3, G4...

                    worksheet[cellName].v = moment(worksheet[cellName].v).format('DD/MM/YYYY');
                }
                // Si es la columna de la hora
                //if (c == 5 && r != 0) {
                if (c == 1 && r != 0) {
                    var cellName = XLSX.utils.encode_cell({ c: c, r: r }); // Ej: H2, H3, H4...
                    worksheet[cellName].v = moment(worksheet[cellName].v).format('HH:mm');
                }
                // Si son las columnas de los ids de las relaciones
                /*if (c == 8 || c == 9 || c == 10 || c == 12 || c == 14) {
          var cellName = XLSX.utils.encode_cell({ c: c, r: r }); // Ej: I0, I1, I2, 
          worksheet[cellName] = { hidden: true}; 
          // delete worksheet[cellName];
        } */
            }
        }
        /* if(!worksheet['!cols']) worksheet['!cols'] = [];
    worksheet['!cols'][7] = { wch: 17 }
    */

        const workbook: XLSX.WorkBook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
        const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
        // const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'buffer' });
        this.saveAsExcelFile(excelBuffer, excelFileName);
    }

    private saveAsExcelFile(buffer: any, fileName: string): void {
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE
        });
        FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
}
