# Basic GRPC java example

This repository contains basic gRPC examples in java for 
1. unary
2. streaming (server to client)

the examples are directly taken from [Hussein Nasser gRPC crash cource](https://www.youtube.com/watch?v=Yw4rkaTc0f8). 
His video shows nodejs examples, while this is the java version.

for server implementation refer 
```java
com.satvik.grpclearn.server
``` 
For client implementation refer 
```java
com.satvik.grpclearn.client
```

After starting the server, execute the client with appropriate args:
### For adding an item
```java
TodoClient.java add
```

### For getting all items
```java
TodoClient.java get
```

### For streaming all items
```java
TodoClient.java getStream
```

Used [BloomRPC](https://github.com/bloomrpc/bloomrpc) for testing the server

Used [Google official repository for reference](https://github.com/grpc/grpc-java/tree/master/examples/src/main/java/io/grpc/examples/helloworld)