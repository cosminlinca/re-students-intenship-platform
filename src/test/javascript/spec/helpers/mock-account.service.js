"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.MockAccountService = void 0;
var rxjs_1 = require("rxjs");
var spyobject_1 = require("./spyobject");
var account_service_1 = require("app/core/auth/account.service");
var MockAccountService = /** @class */ (function (_super) {
    __extends(MockAccountService, _super);
    function MockAccountService() {
        var _this = _super.call(this, account_service_1.AccountService) || this;
        _this.getSpy = _this.spy('get').andReturn(_this);
        _this.saveSpy = _this.spy('save').andReturn(_this);
        _this.authenticateSpy = _this.spy('authenticate').andReturn(_this);
        _this.identitySpy = _this.spy('identity').andReturn(rxjs_1.of(null));
        _this.getAuthenticationStateSpy = _this.spy('getAuthenticationState').andReturn(rxjs_1.of(null));
        _this.isAuthenticated = _this.spy('isAuthenticated').andReturn(true);
        return _this;
    }
    MockAccountService.prototype.setIdentityResponse = function (account) {
        this.identitySpy = this.spy('identity').andReturn(rxjs_1.of(account));
        this.getAuthenticationStateSpy = this.spy('getAuthenticationState').andReturn(rxjs_1.of(account));
    };
    return MockAccountService;
}(spyobject_1.SpyObject));
exports.MockAccountService = MockAccountService;
