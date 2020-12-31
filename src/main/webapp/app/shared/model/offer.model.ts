export interface IOffer {
  id?: number;
  positionName?: string;
  programDurationInWeeks?: number;
  requiredSkills?: string;
  technologies?: string;
  details?: string;
  paid?: boolean;
  observations?: string;
  domain?: string;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public positionName?: string,
    public programDurationInWeeks?: number,
    public requiredSkills?: string,
    public technologies?: string,
    public details?: string,
    public paid?: boolean,
    public observations?: string,
    public domain?: string
  ) {
    this.paid = this.paid || false;
  }
}
