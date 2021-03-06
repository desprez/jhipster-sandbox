/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSandBoxTestModule } from '../../../test.module';
import { PetDetailComponent } from 'app/entities/pet/pet-detail.component';
import { Pet } from 'app/shared/model/pet.model';

describe('Component Tests', () => {
    describe('Pet Management Detail Component', () => {
        let comp: PetDetailComponent;
        let fixture: ComponentFixture<PetDetailComponent>;
        const route = ({ data: of({ pet: new Pet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSandBoxTestModule],
                declarations: [PetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
