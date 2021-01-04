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
exports.MockLoginModalService = void 0;
var spyobject_1 = require("./spyobject");
var login_modal_service_1 = require("app/core/login/login-modal.service");
var MockLoginModalService = /** @class */ (function (_super) {
    __extends(MockLoginModalService, _super);
    function MockLoginModalService() {
        var _this = _super.call(this, login_modal_service_1.LoginModalService) || this;
        _this.open = _this.spy('open');
        return _this;
    }
    return MockLoginModalService;
}(spyobject_1.SpyObject));
exports.MockLoginModalService = MockLoginModalService;
