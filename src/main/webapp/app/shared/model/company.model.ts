export interface ICompany {
  id?: number;
  name?: string;
  description?: string;
  domainOfActivity?: string;
  technologies?: string;
  contact?: string;
  address?: string;
  observations?: string;
  presentationContentType?: string;
  presentation?: any;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public domainOfActivity?: string,
    public technologies?: string,
    public contact?: string,
    public address?: string,
    public observations?: string,
    public presentationContentType?: string,
    public presentation?: any
  ) {}
}
