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
exports.MockActiveModal = void 0;
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var spyobject_1 = require("./spyobject");
var MockActiveModal = /** @class */ (function (_super) {
    __extends(MockActiveModal, _super);
    function MockActiveModal() {
        var _this = _super.call(this, ng_bootstrap_1.NgbActiveModal) || this;
        _this.dismissSpy = _this.spy('dismiss').andReturn(_this);
        _this.closeSpy = _this.spy('close').andReturn(_this);
        return _this;
    }
    return MockActiveModal;
}(spyobject_1.SpyObject));
exports.MockActiveModal = MockActiveModal;
