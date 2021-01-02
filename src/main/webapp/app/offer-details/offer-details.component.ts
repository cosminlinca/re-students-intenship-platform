import { Component, OnInit } from '@angular/core';
import { IOffer } from 'app/shared/model/offer.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-offer-details',
  templateUrl: './offer-details.component.html',
  styleUrls: ['./offer-details.component.scss'],
})
export class OfferDetailsComponent implements OnInit {
  offer: IOffer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => (this.offer = offer));
  }

  previousState(): void {
    window.history.back();
  }
}
