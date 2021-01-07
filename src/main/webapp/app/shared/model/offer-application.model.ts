export interface IOfferApplication {
  id?: number;
  idStudent?: number;
  idOffer?: number;
  attachedCVContentType?: string;
  attachedCV?: any;
}

export class OfferApplication implements IOfferApplication {
  constructor(
    public id?: number,
    public idStudent?: number,
    public idOffer?: number,
    public attachedCVContentType?: string,
    public attachedCV?: any
  ) {}
}
