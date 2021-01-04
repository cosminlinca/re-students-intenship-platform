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
exports.MockAlertService = void 0;
var ng_jhipster_1 = require("ng-jhipster");
var spyobject_1 = require("./spyobject");
var MockAlertService = /** @class */ (function (_super) {
    __extends(MockAlertService, _super);
    function MockAlertService() {
        return _super.call(this, ng_jhipster_1.JhiAlertService) || this;
    }
    MockAlertService.prototype.addAlert = function (alertOptions) {
        return alertOptions;
    };
    return MockAlertService;
}(spyobject_1.SpyObject));
exports.MockAlertService = MockAlertService;
