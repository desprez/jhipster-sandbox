import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
    selector: 'jhi-panel',
  templateUrl: './panel.component.html',
})
export class PanelComponent implements OnInit, OnDestroy {
    alerts: any[];

    constructor() { }

    ngOnInit() {
    }

    ngOnDestroy() {
    }

}
