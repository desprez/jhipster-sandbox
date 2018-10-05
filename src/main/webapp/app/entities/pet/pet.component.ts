import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPet } from 'app/shared/model/pet.model';
import { Principal } from 'app/core';
import { PetService } from './pet.service';

@Component({
    selector: 'jhi-pet',
    templateUrl: './pet.component.html'
})
export class PetComponent implements OnInit, OnDestroy {
    pets: IPet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private petService: PetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.petService.query().subscribe(
            (res: HttpResponse<IPet[]>) => {
                this.pets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPet) {
        return item.id;
    }

    registerChangeInPets() {
        this.eventSubscriber = this.eventManager.subscribe('petListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
