import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoordinates } from 'app/shared/model/coordinates.model';

@Component({
    selector: 'jhi-coordinates-detail',
    templateUrl: './coordinates-detail.component.html'
})
export class CoordinatesDetailComponent implements OnInit {
    coordinates: ICoordinates;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ coordinates }) => {
            this.coordinates = coordinates;
        });
    }

    previousState() {
        window.history.back();
    }
}
