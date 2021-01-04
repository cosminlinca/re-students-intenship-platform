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
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.MockRouter = exports.MockActivatedRoute = void 0;
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var spyobject_1 = require("./spyobject");
var MockActivatedRoute = /** @class */ (function (_super) {
    __extends(MockActivatedRoute, _super);
    function MockActivatedRoute(parameters) {
        var _this = _super.call(this) || this;
        _this.queryParamsSubject = new rxjs_1.ReplaySubject();
        _this.paramSubject = new rxjs_1.ReplaySubject();
        _this.dataSubject = new rxjs_1.ReplaySubject();
        _this.queryParams = _this.queryParamsSubject.asObservable();
        _this.params = _this.paramSubject.asObservable();
        _this.data = _this.dataSubject.asObservable();
        _this.setParameters(parameters);
        return _this;
    }
    MockActivatedRoute.prototype.setParameters = function (parameters) {
        this.queryParamsSubject.next(parameters);
        this.paramSubject.next(parameters);
        this.dataSubject.next(__assign(__assign({}, parameters), { defaultSort: 'id,desc' }));
    };
    return MockActivatedRoute;
}(router_1.ActivatedRoute));
exports.MockActivatedRoute = MockActivatedRoute;
var MockRouter = /** @class */ (function (_super) {
    __extends(MockRouter, _super);
    function MockRouter() {
        var _this = _super.call(this, router_1.Router) || this;
        _this.events = null;
        _this.url = '';
        _this.navigateSpy = _this.spy('navigate');
        _this.navigateByUrlSpy = _this.spy('navigateByUrl');
        return _this;
    }
    MockRouter.prototype.setEvents = function (events) {
        this.events = events;
    };
    MockRouter.prototype.setRouterState = function (routerState) {
        this.routerState = routerState;
    };
    return MockRouter;
}(spyobject_1.SpyObject));
exports.MockRouter = MockRouter;
