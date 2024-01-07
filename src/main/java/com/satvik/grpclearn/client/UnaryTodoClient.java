package com.satvik.grpclearn.client;

import com.satvik.grpclearn.NoParams;
import com.satvik.grpclearn.TodoItem;
import com.satvik.grpclearn.TodoItems;
import com.satvik.grpclearn.TodoServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;

public class UnaryTodoClient {

    private TodoServiceGrpc.TodoServiceBlockingStub todoServiceBlockingStub;

    public UnaryTodoClient(Channel channel){
        todoServiceBlockingStub = TodoServiceGrpc.newBlockingStub(channel);

    }

    public void create(String text) {
        TodoItem request = TodoItem.newBuilder().setText(text).build();
        TodoItem response;
        try {
            response = todoServiceBlockingStub.createTodo(request);
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed:" + e.getStatus());
            return;
        }
        System.out.println("From server: " + response);
    }

    public void printTodosBlocking() {
        TodoItems response = TodoItems.newBuilder().build();
        try {
            response = todoServiceBlockingStub.getTodos(NoParams.newBuilder().build());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed:" + e.getStatus());
        }
        System.out.println("From server:");
        response.getItemsList().forEach(System.out::println);
    }

    // this does not actually stream the items. It just dumps everything on the client at once.
    // Not sure what is the difference between unary call and streaming call in this case
    public void printTodosStreamingButNotReally() {
        Iterator<TodoItem> response;
        try {
            response = todoServiceBlockingStub.getTodosStream(NoParams.newBuilder().build());
            while(response.hasNext()){
                System.out.println(response.next());
            }
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed:" + e.getStatus());
        }
    }
}
