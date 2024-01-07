package com.satvik.grpclearn.client;

import com.satvik.grpclearn.TodoItem;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TodoItemStreamObserver implements StreamObserver<TodoItem> {
    private final CountDownLatch done;
    public TodoItemStreamObserver(CountDownLatch latch){
        this.done = latch;
    }

    @Override
    public void onNext(TodoItem value) {
        System.out.println("from server: " + value);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("server ended with error: " + t.getCause());
        done.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("server completed successfully!");
        done.countDown();
    }
}
