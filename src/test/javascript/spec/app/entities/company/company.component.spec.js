"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var rxjs_1 = require("rxjs");
var http_1 = require("@angular/common/http");
var router_1 = require("@angular/router");
var test_module_1 = require("../../../test.module");
var company_component_1 = require("app/entities/company/company.component");
var company_service_1 = require("app/entities/company/company.service");
var company_model_1 = require("app/shared/model/company.model");
describe('Component Tests', function () {
    describe('Company Management Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.StudentsIntenshipPlatformAvraTestModule],
                declarations: [company_component_1.CompanyComponent],
                providers: [
                    {
                        provide: router_1.ActivatedRoute,
                        useValue: {
                            data: rxjs_1.of({
                                defaultSort: 'id,asc',
                            }),
                            queryParamMap: rxjs_1.of(router_1.convertToParamMap({
                                page: '1',
                                size: '1',
                                sort: 'id,desc',
                            })),
                        },
                    },
                ],
            })
                .overrideTemplate(company_component_1.CompanyComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(company_component_1.CompanyComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(company_service_1.CompanyService);
        });
        it('Should call load all on init', function () {
            // GIVEN
            var headers = new http_1.HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(rxjs_1.of(new http_1.HttpResponse({
                body: [new company_model_1.Company(123)],
                headers: headers,
            })));
            // WHEN
            comp.ngOnInit();
            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.companies && comp.companies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should load a page', function () {
            // GIVEN
            var headers = new http_1.HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(rxjs_1.of(new http_1.HttpResponse({
                body: [new company_model_1.Company(123)],
                headers: headers,
            })));
            // WHEN
            comp.loadPage(1);
            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.companies && comp.companies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should calculate the sort attribute for an id', function () {
            // WHEN
            comp.ngOnInit();
            var result = comp.sort();
            // THEN
            expect(result).toEqual(['id,desc']);
        });
        it('should calculate the sort attribute for a non-id attribute', function () {
            // INIT
            comp.ngOnInit();
            // GIVEN
            comp.predicate = 'name';
            // WHEN
            var result = comp.sort();
            // THEN
            expect(result).toEqual(['name,desc', 'id']);
        });
    });
});
