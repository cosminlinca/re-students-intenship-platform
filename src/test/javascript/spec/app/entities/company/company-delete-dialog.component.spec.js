"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var testing_1 = require("@angular/core/testing");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var rxjs_1 = require("rxjs");
var ng_jhipster_1 = require("ng-jhipster");
var test_module_1 = require("../../../test.module");
var company_delete_dialog_component_1 = require("app/entities/company/company-delete-dialog.component");
var company_service_1 = require("app/entities/company/company.service");
describe('Component Tests', function () {
    describe('Company Management Delete Component', function () {
        var comp;
        var fixture;
        var service;
        var mockEventManager;
        var mockActiveModal;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.StudentsIntenshipPlatformAvraTestModule],
                declarations: [company_delete_dialog_component_1.CompanyDeleteDialogComponent],
            })
                .overrideTemplate(company_delete_dialog_component_1.CompanyDeleteDialogComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(company_delete_dialog_component_1.CompanyDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(company_service_1.CompanyService);
            mockEventManager = testing_1.TestBed.get(ng_jhipster_1.JhiEventManager);
            mockActiveModal = testing_1.TestBed.get(ng_bootstrap_1.NgbActiveModal);
        });
        describe('confirmDelete', function () {
            it('Should call delete service on confirmDelete', testing_1.inject([], testing_1.fakeAsync(function () {
                // GIVEN
                spyOn(service, 'delete').and.returnValue(rxjs_1.of({}));
                // WHEN
                comp.confirmDelete(123);
                testing_1.tick();
                // THEN
                expect(service.delete).toHaveBeenCalledWith(123);
                expect(mockActiveModal.closeSpy).toHaveBeenCalled();
                expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
            })));
            it('Should not call delete service on clear', function () {
                // GIVEN
                spyOn(service, 'delete');
                // WHEN
                comp.cancel();
                // THEN
                expect(service.delete).not.toHaveBeenCalled();
                expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
            });
        });
    });
});
