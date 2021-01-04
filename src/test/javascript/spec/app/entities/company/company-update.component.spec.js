"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var http_1 = require("@angular/common/http");
var forms_1 = require("@angular/forms");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var company_update_component_1 = require("app/entities/company/company-update.component");
var company_service_1 = require("app/entities/company/company.service");
var company_model_1 = require("app/shared/model/company.model");
describe('Component Tests', function () {
    describe('Company Management Update Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.StudentsIntenshipPlatformAvraTestModule],
                declarations: [company_update_component_1.CompanyUpdateComponent],
                providers: [forms_1.FormBuilder],
            })
                .overrideTemplate(company_update_component_1.CompanyUpdateComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(company_update_component_1.CompanyUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(company_service_1.CompanyService);
        });
        describe('save', function () {
            it('Should call update service on save for existing entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new company_model_1.Company(123);
                spyOn(service, 'update').and.returnValue(rxjs_1.of(new http_1.HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                testing_1.tick(); // simulate async
                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
            it('Should call create service on save for new entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new company_model_1.Company();
                spyOn(service, 'create').and.returnValue(rxjs_1.of(new http_1.HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                testing_1.tick(); // simulate async
                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
