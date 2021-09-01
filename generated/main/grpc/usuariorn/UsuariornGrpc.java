package usuariorn;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: UsuarioRn.proto")
public final class UsuariornGrpc {

  private UsuariornGrpc() {}

  public static final String SERVICE_NAME = "usuariorn.Usuariorn";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<usuariorn.UsuarioRn.GetURLRequest,
      usuariorn.UsuarioRn.GetUrlResponse> getGetURLMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetURL",
      requestType = usuariorn.UsuarioRn.GetURLRequest.class,
      responseType = usuariorn.UsuarioRn.GetUrlResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<usuariorn.UsuarioRn.GetURLRequest,
      usuariorn.UsuarioRn.GetUrlResponse> getGetURLMethod() {
    io.grpc.MethodDescriptor<usuariorn.UsuarioRn.GetURLRequest, usuariorn.UsuarioRn.GetUrlResponse> getGetURLMethod;
    if ((getGetURLMethod = UsuariornGrpc.getGetURLMethod) == null) {
      synchronized (UsuariornGrpc.class) {
        if ((getGetURLMethod = UsuariornGrpc.getGetURLMethod) == null) {
          UsuariornGrpc.getGetURLMethod = getGetURLMethod =
              io.grpc.MethodDescriptor.<usuariorn.UsuarioRn.GetURLRequest, usuariorn.UsuarioRn.GetUrlResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetURL"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  usuariorn.UsuarioRn.GetURLRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  usuariorn.UsuarioRn.GetUrlResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UsuariornMethodDescriptorSupplier("GetURL"))
              .build();
        }
      }
    }
    return getGetURLMethod;
  }

  private static volatile io.grpc.MethodDescriptor<usuariorn.UsuarioRn.RegistrarURLRequest,
      usuariorn.UsuarioRn.RegistrarURLResponse> getRegistrarURLMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegistrarURL",
      requestType = usuariorn.UsuarioRn.RegistrarURLRequest.class,
      responseType = usuariorn.UsuarioRn.RegistrarURLResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<usuariorn.UsuarioRn.RegistrarURLRequest,
      usuariorn.UsuarioRn.RegistrarURLResponse> getRegistrarURLMethod() {
    io.grpc.MethodDescriptor<usuariorn.UsuarioRn.RegistrarURLRequest, usuariorn.UsuarioRn.RegistrarURLResponse> getRegistrarURLMethod;
    if ((getRegistrarURLMethod = UsuariornGrpc.getRegistrarURLMethod) == null) {
      synchronized (UsuariornGrpc.class) {
        if ((getRegistrarURLMethod = UsuariornGrpc.getRegistrarURLMethod) == null) {
          UsuariornGrpc.getRegistrarURLMethod = getRegistrarURLMethod =
              io.grpc.MethodDescriptor.<usuariorn.UsuarioRn.RegistrarURLRequest, usuariorn.UsuarioRn.RegistrarURLResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegistrarURL"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  usuariorn.UsuarioRn.RegistrarURLRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  usuariorn.UsuarioRn.RegistrarURLResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UsuariornMethodDescriptorSupplier("RegistrarURL"))
              .build();
        }
      }
    }
    return getRegistrarURLMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UsuariornStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsuariornStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsuariornStub>() {
        @java.lang.Override
        public UsuariornStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsuariornStub(channel, callOptions);
        }
      };
    return UsuariornStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UsuariornBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsuariornBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsuariornBlockingStub>() {
        @java.lang.Override
        public UsuariornBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsuariornBlockingStub(channel, callOptions);
        }
      };
    return UsuariornBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UsuariornFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UsuariornFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UsuariornFutureStub>() {
        @java.lang.Override
        public UsuariornFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UsuariornFutureStub(channel, callOptions);
        }
      };
    return UsuariornFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UsuariornImplBase implements io.grpc.BindableService {

    /**
     */
    public void getURL(usuariorn.UsuarioRn.GetURLRequest request,
        io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.GetUrlResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetURLMethod(), responseObserver);
    }

    /**
     */
    public void registrarURL(usuariorn.UsuarioRn.RegistrarURLRequest request,
        io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.RegistrarURLResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegistrarURLMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetURLMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                usuariorn.UsuarioRn.GetURLRequest,
                usuariorn.UsuarioRn.GetUrlResponse>(
                  this, METHODID_GET_URL)))
          .addMethod(
            getRegistrarURLMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                usuariorn.UsuarioRn.RegistrarURLRequest,
                usuariorn.UsuarioRn.RegistrarURLResponse>(
                  this, METHODID_REGISTRAR_URL)))
          .build();
    }
  }

  /**
   */
  public static final class UsuariornStub extends io.grpc.stub.AbstractAsyncStub<UsuariornStub> {
    private UsuariornStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsuariornStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsuariornStub(channel, callOptions);
    }

    /**
     */
    public void getURL(usuariorn.UsuarioRn.GetURLRequest request,
        io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.GetUrlResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetURLMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registrarURL(usuariorn.UsuarioRn.RegistrarURLRequest request,
        io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.RegistrarURLResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegistrarURLMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UsuariornBlockingStub extends io.grpc.stub.AbstractBlockingStub<UsuariornBlockingStub> {
    private UsuariornBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsuariornBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsuariornBlockingStub(channel, callOptions);
    }

    /**
     */
    public usuariorn.UsuarioRn.GetUrlResponse getURL(usuariorn.UsuarioRn.GetURLRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetURLMethod(), getCallOptions(), request);
    }

    /**
     */
    public usuariorn.UsuarioRn.RegistrarURLResponse registrarURL(usuariorn.UsuarioRn.RegistrarURLRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegistrarURLMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UsuariornFutureStub extends io.grpc.stub.AbstractFutureStub<UsuariornFutureStub> {
    private UsuariornFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UsuariornFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UsuariornFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<usuariorn.UsuarioRn.GetUrlResponse> getURL(
        usuariorn.UsuarioRn.GetURLRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetURLMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<usuariorn.UsuarioRn.RegistrarURLResponse> registrarURL(
        usuariorn.UsuarioRn.RegistrarURLRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegistrarURLMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_URL = 0;
  private static final int METHODID_REGISTRAR_URL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UsuariornImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UsuariornImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_URL:
          serviceImpl.getURL((usuariorn.UsuarioRn.GetURLRequest) request,
              (io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.GetUrlResponse>) responseObserver);
          break;
        case METHODID_REGISTRAR_URL:
          serviceImpl.registrarURL((usuariorn.UsuarioRn.RegistrarURLRequest) request,
              (io.grpc.stub.StreamObserver<usuariorn.UsuarioRn.RegistrarURLResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UsuariornBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UsuariornBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return usuariorn.UsuarioRn.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Usuariorn");
    }
  }

  private static final class UsuariornFileDescriptorSupplier
      extends UsuariornBaseDescriptorSupplier {
    UsuariornFileDescriptorSupplier() {}
  }

  private static final class UsuariornMethodDescriptorSupplier
      extends UsuariornBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UsuariornMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UsuariornGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UsuariornFileDescriptorSupplier())
              .addMethod(getGetURLMethod())
              .addMethod(getRegistrarURLMethod())
              .build();
        }
      }
    }
    return result;
  }
}
