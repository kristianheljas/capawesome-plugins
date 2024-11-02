export declare class PromiseQueue {
    private queue;
    private working;
    enqueue<T = void>(key: string, promise: () => Promise<T>): Promise<T>;
    private dequeue;
}
