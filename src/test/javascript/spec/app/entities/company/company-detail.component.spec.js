"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var ng_jhipster_1 = require("ng-jhipster");
var test_module_1 = require("../../../test.module");
var company_detail_component_1 = require("app/entities/company/company-detail.component");
var company_model_1 = require("app/shared/model/company.model");
describe('Component Tests', function () {
    describe('Company Management Detail Component', function () {
        var comp;
        var fixture;
        var dataUtils;
        var route = { data: rxjs_1.of({ company: new company_model_1.Company(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.StudentsIntenshipPlatformAvraTestModule],
                declarations: [company_detail_component_1.CompanyDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(company_detail_component_1.CompanyDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(company_detail_component_1.CompanyDetailComponent);
            comp = fixture.componentInstance;
            dataUtils = fixture.debugElement.injector.get(ng_jhipster_1.JhiDataUtils);
        });
        describe('OnInit', function () {
            it('Should load company on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.company).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
        describe('byteSize', function () {
            it('Should call byteSize from JhiDataUtils', function () {
                // GIVEN
                spyOn(dataUtils, 'byteSize');
                var fakeBase64 = 'fake base64';
                // WHEN
                comp.byteSize(fakeBase64);
                // THEN
                expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
            });
        });
        describe('openFile', function () {
            it('Should call openFile from JhiDataUtils', function () {
                // GIVEN
                spyOn(dataUtils, 'openFile');
                var fakeContentType = 'fake content type';
                var fakeBase64 = 'fake base64';
                // WHEN
                comp.openFile(fakeContentType, fakeBase64);
                // THEN
                expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
            });
        });
    });
});
