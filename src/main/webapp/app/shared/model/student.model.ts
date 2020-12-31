import { IUser } from 'app/core/user/user.model';

export interface IStudent {
  id?: number;
  university?: string;
  faculty?: string;
  profile?: string;
  yearOfStudy?: number;
  cvPath?: string;
  observations?: string;
  cvDocumentContentType?: string;
  cvDocument?: any;
  user?: IUser;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public university?: string,
    public faculty?: string,
    public profile?: string,
    public yearOfStudy?: number,
    public cvPath?: string,
    public observations?: string,
    public cvDocumentContentType?: string,
    public cvDocument?: any,
    public user?: IUser
  ) {}
}
