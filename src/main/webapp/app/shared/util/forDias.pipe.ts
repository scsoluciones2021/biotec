import { Pipe, PipeTransform } from '@angular/core';

//nombre que vamos a poner en el filtro, ejemplo: "{{ true;false;false;true | forDias}}"
@Pipe({
  name: 'forDias'
})
export class ForDiasPipe implements PipeTransform {

  
  //valor es el string con los valores que pasamos
  transform(valor: any, args?: any): any {
    // Agregado para visualizar bien los días
    let  dias: string[] = ['L', 'M', 'X', 'J', 'V', 'S', 'D'];

    let ar: String = '';
    let arregloDias = valor.split(';');
    arregloDias.pop();
    arregloDias.forEach(function (element, indice) {
        if(element == 'true'){
            ar  += dias[indice] + ';';
        }
    });

    return ar;
  }

}