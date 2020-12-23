export interface ITestEntity {
  id?: number;
  name?: string;
  year?: number;
}

export class TestEntity implements ITestEntity {
  constructor(public id?: number, public name?: string, public year?: number) {}
}
