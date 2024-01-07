package com.satvik.grpclearn.client;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TodoClient {
    private final static Random random = new Random();

    private static List<String> todosSample = List.of("Laundry",
            "Read 10 pages of that book you've been procrastinating for",
            "Run a 10k today",
            "Scroll some instagram",
            "Think about life",
            "Listen to your fav podcast",
            "meditate for 10 mins");

    public static void main(String[] args) throws InterruptedException {
        String target = "localhost:9090";
        System.out.println(Arrays.toString(args));

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        try {
            if("add".equals(args[0])){
                UnaryTodoClient client = new UnaryTodoClient(channel);
                client.create(getRandomTodo());
            } else if("get".equals(args[0])){
                UnaryTodoClient client = new UnaryTodoClient(channel);
                client.printTodosBlocking();
            } else if("getStreamFake".equals(args[0])){
                UnaryTodoClient client = new UnaryTodoClient(channel);
                client.printTodosStreamingButNotReally();
            } else if("getStream".equals(args[0])){
                StreamingTodoClient client = new StreamingTodoClient(channel);
                client.printTodosStreaming();
            }

        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    private static String getRandomTodo(){
        int index = random.nextInt(todosSample.size());
        return todosSample.get(index);
    }
}
