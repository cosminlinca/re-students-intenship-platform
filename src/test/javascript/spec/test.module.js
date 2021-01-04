"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.StudentsIntenshipPlatformAvraTestModule = void 0;
var common_1 = require("@angular/common");
var router_1 = require("@angular/router");
var core_1 = require("@angular/core");
var testing_1 = require("@angular/common/http/testing");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var ngx_webstorage_1 = require("ngx-webstorage");
var ng_jhipster_1 = require("ng-jhipster");
var mock_language_service_1 = require("./helpers/mock-language.service");
var account_service_1 = require("app/core/auth/account.service");
var login_modal_service_1 = require("app/core/login/login-modal.service");
var mock_login_modal_service_1 = require("./helpers/mock-login-modal.service");
var mock_account_service_1 = require("./helpers/mock-account.service");
var mock_route_service_1 = require("./helpers/mock-route.service");
var mock_active_modal_service_1 = require("./helpers/mock-active-modal.service");
var mock_alert_service_1 = require("./helpers/mock-alert.service");
var mock_event_manager_service_1 = require("./helpers/mock-event-manager.service");
var StudentsIntenshipPlatformAvraTestModule = /** @class */ (function () {
    function StudentsIntenshipPlatformAvraTestModule() {
    }
    StudentsIntenshipPlatformAvraTestModule = __decorate([
        core_1.NgModule({
            providers: [
                common_1.DatePipe,
                ng_jhipster_1.JhiDataUtils,
                ng_jhipster_1.JhiDateUtils,
                ng_jhipster_1.JhiParseLinks,
                {
                    provide: ng_jhipster_1.JhiLanguageService,
                    useClass: mock_language_service_1.MockLanguageService,
                },
                {
                    provide: ng_jhipster_1.JhiEventManager,
                    useClass: mock_event_manager_service_1.MockEventManager,
                },
                {
                    provide: ng_bootstrap_1.NgbActiveModal,
                    useClass: mock_active_modal_service_1.MockActiveModal,
                },
                {
                    provide: router_1.ActivatedRoute,
                    useValue: new mock_route_service_1.MockActivatedRoute({ id: 123 }),
                },
                {
                    provide: router_1.Router,
                    useClass: mock_route_service_1.MockRouter,
                },
                {
                    provide: account_service_1.AccountService,
                    useClass: mock_account_service_1.MockAccountService,
                },
                {
                    provide: login_modal_service_1.LoginModalService,
                    useClass: mock_login_modal_service_1.MockLoginModalService,
                },
                {
                    provide: ng_jhipster_1.JhiAlertService,
                    useClass: mock_alert_service_1.MockAlertService,
                },
                {
                    provide: ng_bootstrap_1.NgbModal,
                    useValue: null,
                },
                {
                    provide: ngx_webstorage_1.SessionStorageService,
                    useValue: null,
                },
                {
                    provide: ngx_webstorage_1.LocalStorageService,
                    useValue: null,
                },
            ],
            imports: [testing_1.HttpClientTestingModule],
        })
    ], StudentsIntenshipPlatformAvraTestModule);
    return StudentsIntenshipPlatformAvraTestModule;
}());
exports.StudentsIntenshipPlatformAvraTestModule = StudentsIntenshipPlatformAvraTestModule;
