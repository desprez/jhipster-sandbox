import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

import { IPet } from 'app/shared/model/pet.model';
import { IOwner } from 'app/shared/model/owner.model';
import { PetService } from '../pet/pet.service';

@Component({
    selector: 'jhi-owner-detail',
    templateUrl: './owner-detail.component.html'
})
export class OwnerDetailComponent implements OnInit {
    owner: IOwner;
    pets: IPet[];

    constructor(private activatedRoute: ActivatedRoute, private petService: PetService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ owner }) => {
            this.owner = owner;
            this.loadPets(owner.id);
        });
    }

    loadPets(id) {
        this.petService.queryByOwner(id).subscribe(
            (res: HttpResponse<IPet[]>) => {
                this.pets = res.body;
            }
            // ,
            // (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    //    private onError(errorMessage: string) {
    //        this.jhiAlertService.error(errorMessage, null, null);
    //    }

    previousState() {
        window.history.back();
    }
}
