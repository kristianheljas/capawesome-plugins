export class PromiseQueue {
    constructor() {
        this.queue = new Map();
        this.working = new Map();
    }
    enqueue(key, promise) {
        return new Promise((resolve, reject) => {
            var _a;
            const item = { promise, resolve, reject };
            if (this.queue.has(key)) {
                (_a = this.queue.get(key)) === null || _a === void 0 ? void 0 : _a.push(item);
            }
            else {
                this.queue.set(key, [item]);
            }
            this.dequeue(key);
        });
    }
    dequeue(key) {
        var _a;
        if (this.working.get(key)) {
            return;
        }
        const item = (_a = this.queue.get(key)) === null || _a === void 0 ? void 0 : _a.shift();
        if (!item) {
            return;
        }
        try {
            this.working.set(key, true);
            item
                .promise()
                .then(value => item.resolve(value))
                .catch(err => item.reject(err))
                .finally(() => {
                this.working.set(key, false);
                this.dequeue(key);
            });
        }
        catch (error) {
            item.reject(error);
            this.working.set(key, false);
            this.dequeue(key);
        }
    }
}
//# sourceMappingURL=queue.js.map