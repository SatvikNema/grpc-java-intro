package com.satvik.grpclearn.server;

import com.satvik.grpclearn.NoParams;
import com.satvik.grpclearn.TodoItem;
import com.satvik.grpclearn.TodoItems;
import com.satvik.grpclearn.TodoServiceGrpc;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TodoServer extends TodoServiceGrpc.TodoServiceImplBase {

    private final Random random;
    private final List<TodoItem> globalTodoItems;
    public TodoServer(){
        random = new Random();
        globalTodoItems = new ArrayList<>();
    }
    @Override
    public void createTodo(TodoItem request, StreamObserver<TodoItem> responseObserver) {
        TodoItem response = TodoItem.newBuilder()
                .setText(request.getText())
                .setId(random.nextInt(100000))
                .build();
        globalTodoItems.add(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTodosStream(NoParams request, StreamObserver<TodoItem> responseObserver) {
        globalTodoItems.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getTodos(NoParams request, StreamObserver<TodoItems> responseObserver) {
        TodoItems.Builder builder = TodoItems.newBuilder();
        builder.addAllItems(globalTodoItems);
        TodoItems response = builder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
