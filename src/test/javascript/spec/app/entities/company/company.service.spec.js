"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var testing_2 = require("@angular/common/http/testing");
var company_service_1 = require("app/entities/company/company.service");
var company_model_1 = require("app/shared/model/company.model");
describe('Service Tests', function () {
    describe('Company Service', function () {
        var injector;
        var service;
        var httpMock;
        var elemDefault;
        var expectedResult;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [testing_2.HttpClientTestingModule],
            });
            expectedResult = null;
            injector = testing_1.getTestBed();
            service = injector.get(company_service_1.CompanyService);
            httpMock = injector.get(testing_2.HttpTestingController);
            elemDefault = new company_model_1.Company(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA');
        });
        describe('Service methods', function () {
            it('should find an element', function () {
                var returnedFromService = Object.assign({}, elemDefault);
                service.find(123).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });
            it('should create a Company', function () {
                var returnedFromService = Object.assign({
                    id: 0,
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.create(new company_model_1.Company()).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should update a Company', function () {
                var returnedFromService = Object.assign({
                    name: 'BBBBBB',
                    description: 'BBBBBB',
                    domainOfActivity: 'BBBBBB',
                    technologies: 'BBBBBB',
                    contact: 'BBBBBB',
                    address: 'BBBBBB',
                    observations: 'BBBBBB',
                    presentation: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.update(expected).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should return a list of Company', function () {
                var returnedFromService = Object.assign({
                    name: 'BBBBBB',
                    description: 'BBBBBB',
                    domainOfActivity: 'BBBBBB',
                    technologies: 'BBBBBB',
                    contact: 'BBBBBB',
                    address: 'BBBBBB',
                    observations: 'BBBBBB',
                    presentation: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.query().subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush([returnedFromService]);
                httpMock.verify();
                expect(expectedResult).toContainEqual(expected);
            });
            it('should delete a Company', function () {
                service.delete(123).subscribe(function (resp) { return (expectedResult = resp.ok); });
                var req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
                expect(expectedResult);
            });
        });
        afterEach(function () {
            httpMock.verify();
        });
    });
});
