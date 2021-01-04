"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.SpyObject = void 0;
var SpyObject = /** @class */ (function () {
    function SpyObject(type) {
        var _this = this;
        if (type) {
            Object.keys(type.prototype).forEach(function (prop) {
                var m = null;
                try {
                    m = type.prototype[prop];
                }
                catch (e) {
                    // As we are creating spys for abstract classes,
                    // these classes might have getters that throw when they are accessed.
                    // As we are only auto creating spys for methods, this
                    // should not matter.
                }
                if (typeof m === 'function') {
                    _this.spy(prop);
                }
            });
        }
    }
    SpyObject.prototype.spy = function (name) {
        if (!this[name]) {
            this[name] = this.createGuinnessCompatibleSpy(name);
        }
        return this[name];
    };
    SpyObject.prototype.createGuinnessCompatibleSpy = function (name) {
        var newSpy = jasmine.createSpy(name);
        newSpy.andCallFake = newSpy.and.callFake;
        newSpy.andReturn = newSpy.and.returnValue;
        newSpy.reset = newSpy.calls.reset;
        // revisit return null here (previously needed for rtts_assert).
        newSpy.and.returnValue(null);
        return newSpy;
    };
    return SpyObject;
}());
exports.SpyObject = SpyObject;
