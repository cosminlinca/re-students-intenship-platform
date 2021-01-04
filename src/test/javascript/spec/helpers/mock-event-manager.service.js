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
exports.MockEventManager = void 0;
var ng_jhipster_1 = require("ng-jhipster");
var spyobject_1 = require("./spyobject");
var MockEventManager = /** @class */ (function (_super) {
    __extends(MockEventManager, _super);
    function MockEventManager() {
        var _this = _super.call(this, ng_jhipster_1.JhiEventManager) || this;
        _this.broadcastSpy = _this.spy('broadcast').andReturn(_this);
        return _this;
    }
    return MockEventManager;
}(spyobject_1.SpyObject));
exports.MockEventManager = MockEventManager;
