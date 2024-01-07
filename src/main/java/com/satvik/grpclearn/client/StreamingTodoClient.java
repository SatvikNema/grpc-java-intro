package com.satvik.grpclearn.client;

import com.satvik.grpclearn.NoParams;
import com.satvik.grpclearn.TodoServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.CountDownLatch;

public class StreamingTodoClient {
    private TodoServiceGrpc.TodoServiceStub todoServiceStub;
    public StreamingTodoClient(Channel channel){
        todoServiceStub = TodoServiceGrpc.newStub(channel);
    }

    public void printTodosStreaming() {
        // we need to maintain this latch to hold this thread active until the stream is finished
        CountDownLatch done = new CountDownLatch(1);
        TodoItemStreamObserver todoItemStreamObserver = new TodoItemStreamObserver(done);
        try {
            todoServiceStub.getTodosStream(NoParams.newBuilder().build(), todoItemStreamObserver);
            done.await(); // wait until the stream is finished
        } catch(StatusRuntimeException ex){
            System.out.println("gRPC failed "+ex.getMessage());
        } catch (InterruptedException e) {
            System.out.println("latch interrupted while waiting");
            throw new RuntimeException(e);
        }
    }
}
