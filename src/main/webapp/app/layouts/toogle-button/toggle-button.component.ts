import { Component, Output, EventEmitter, Input } from '@angular/core';

@Component({
    selector: 'toggle-button',
    template: `
  <input type="checkbox" id="toggle-button-checkbox"
  (change)="changed.emit($event.target.checked)">
  <label class="toggle-button-switch"  
    for="toggle-button-checkbox"></label>
    <div class="toggle-button-text">
      <div class="toggle-button-text-on">{{activo}}</div>
      <div class="toggle-button-text-off">{{inactivo}}</div>
    </div>
  `,
    styles: [
        `
    :host {
      display: block;
      position: relative;
      width: 50px;
      height: 50px;
    }
    
    input[type="checkbox"] {
      display: none; 
    }

    .toggle-button-switch {
      position: absolute;
      top: 2px;
      left: 2px;
      width: 20px;
      height: 20px;
      background-color: #fff;
      border-radius: 100%;
      cursor: pointer;
      z-index: 100;
      transition: left 0.3s;
    }

    .toggle-button-text {
      overflow: hidden;
      background-color: #fc3164;
      border-radius: 25px;
      box-shadow: 2px 2px 5px 0 rgba(50, 50, 50, 0.75);
      transition: background-color 0.3s;
    }

    .toggle-button-text-on,
    .toggle-button-text-off {
      float: left;
      width: 40%;
      height: 100%;
      /*line-height: 50px;
      font-family: Lato, sans-serif;
      font-weight: bold; */
      color: #fff;
      text-align: center;
    }

    input[type="checkbox"]:checked ~ .toggle-button-switch {
      /* left: 52px; */
      left: 27px; 
    }

    input[type="checkbox"]:checked ~ .toggle-button-text {
      background-color: #3dbf87;
    }

    input[type="checkbox"]:checked ~ * .toggle-button-text-off{
        display: none;
    }

    input[type="checkbox"]:not(:checked) ~ * .toggle-button-text-on{
      display: none;
    }

    input[type="checkbox"]:not(:checked) ~ * .toggle-button-text-off{
      float: right;
      width: 30px;
    }

  `
    ]
})
export class ToggleButtonComponent {
    @Output() changed = new EventEmitter<boolean>();
    @Input() activo: String;
    @Input() inactivo: String;
}
