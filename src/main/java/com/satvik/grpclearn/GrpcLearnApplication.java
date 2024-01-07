package com.satvik.grpclearn;


import com.satvik.grpclearn.server.TodoServer;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcLearnApplication {
	private Server server;

	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("peepee");
		GrpcLearnApplication grpcLearnApplication = new GrpcLearnApplication();
		grpcLearnApplication.start();

		//Await termination on the main thread since the grpc library uses daemon threads.
		grpcLearnApplication.blockUntilShutdown();
	}

	private void start() throws IOException {
		/* The port on which the server should run */
		int port = 9090;
		server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
				.addService(new TodoServer())
				.build()
				.start();
		System.out.println("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                GrpcLearnApplication.this.stopServer();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
	}

	private void stopServer() throws InterruptedException {
		if (server != null) {
			server.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		}
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

}
