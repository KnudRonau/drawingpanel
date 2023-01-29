//package se.miun.dt176g.xxxxyyyy.reactive;
//
//import io.reactivex.rxjava3.core.Completable;
//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjava3.disposables.Disposable;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//import io.reactivex.rxjava3.subjects.PublishSubject;
//import io.reactivex.rxjava3.subjects.ReplaySubject;
//import io.reactivex.rxjava3.subjects.Subject;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Reactive Server that relays all incoming messages to all connected clients.
// *
// * TODO prevent sending message back to its origin (the client that sent it).
// * For example, wrap Strings in ChatMessage object with id for each client
// * (and possibly a chosen nickname).
// *
// * @author Gustaf Holst (jan -22)
// */
//public class ReactiveServerCopy {
//
//    public class ConnectError extends Throwable {
//
//        private static final long serialVersionUID = 1L;
//        private final Socket socket;
//
//        public ConnectError(Socket socket) {
//            this.socket = socket;
//        }
//
//        public Socket getSocket() {
//            return socket;
//        }
//    }
//
//    private Map<Integer, Disposable> disposables;    //key: socket hash
//    private Map<Integer, Disposable> messageDisposables;
//
//    private Subject<Socket> connections;
//    private Subject<String> messageStream;   //all messages from all clients
//
//    private ServerSocket serverSocket;
//    private boolean acceptConnections = true;
//
//
//    public static void main(String[] args) {
//
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//            ReactiveServerCopy server = new ReactiveServerCopy();
//            Disposable disposable = Observable.just(server)
//                    .subscribeOn(Schedulers.single())
//                    .doOnNext(ReactiveServerCopy::run)
//                    .doOnDispose(server::shutdown)
//                    .doOnSubscribe(d -> System.out.println("Server is running...press <enter> to stop"))
//                    .subscribe();
//
//            br.readLine();
//
//            disposable.dispose();
//            Schedulers.shutdown();
//
//            System.out.println("-- Shutting down --");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void run() {
//        disposables = new HashMap<>();
//        messageDisposables = new HashMap<>();
//        connections = PublishSubject.create();
//        messageStream = ReplaySubject.createWithSize(3);  //replay 3 last messages to new clients
//
//        // listen for requests on separate thread
//        Completable.create(emitter -> {try {
//            listenForIncomingConnectionRequests();
//        } catch (Throwable e) {
//            emitter.onError(e);
//        }
//        })
//                .subscribeOn(Schedulers.single())
//                .subscribe();
//
//        connections
//                .doOnNext(s -> System.out.println("tcp connection accepted..."))
//                .subscribe(this::listenToSocket);
//    }
//
//    private void listenForIncomingConnectionRequests() throws IOException {
//        serverSocket = new ServerSocket(12345);
//
//        while (acceptConnections) {
//            Socket socket = serverSocket.accept();
//            Observable.<Socket>create(emitter -> emitter.onNext(socket))
//                    .observeOn(Schedulers.io())
//                    .subscribe(connections);
//        }
//
//    }
//
////	private void listenToSocket(Socket socket) {
////		System.out.println("listen run");
////		//inject all incoming messages from this socket to the message stream
////	    Observable.<String>create(emitter -> {
////	    	socketToBufferedReaderLogic(socket)
////		    .subscribe(br -> {
////		    	while(!emitter.isDisposed()) {
////		    		String in = br.readLine();
////					System.out.println("first");
////		    		if (in == null || socket.isClosed()) {
////		                emitter.onError(new ConnectError(socket));
////						System.out.println("second");
////		            }
////		            else {
////						System.out.println("third");
////		                emitter.onNext(in);
////		            }
////		    	}
////		    });
////	    })
////	    .subscribeOn(Schedulers.io())
////	    .doOnSubscribe(d -> disposables.put(socket.hashCode(), d))
////	    .doOnError(this::handleError)
////	    .onErrorComplete(err -> err instanceof ConnectError)
////	    .doOnNext(System.out::println)
////	    .subscribe(messageStream::onNext
////                , err -> System.err.println(err.getMessage())
////                , () -> System.out.println("Socket closed"));
////
////	    // subscribe each newly connected client to the messagestream
////	    messageStream
////	    .subscribeOn(Schedulers.io())
////	    .doOnSubscribe(d -> messageDisposables.put(socket.hashCode(), d))
////	    .withLatestFrom(socketToPrintWriterLogic(socket), (m, pw) -> {
////	    		pw.println(m);
////	    		pw.flush();
////	    		return true;
////	    })
////	    .subscribe();
////	}
//
//    private void listenToSocket(Socket socket) {
//        System.out.println("listen run");
//        //inject all incoming messages from this socket to the message stream
//        Observable.<String>create(emitter -> {
//            Observable.just(socket)
//                    .map(Socket::getInputStream)
//                    .map(InputStreamReader::new)
//                    .map(BufferedReader::new)
//                    .subscribe(br -> {
//                        while(!emitter.isDisposed()) {
//                            String in = br.readLine();
//                            System.out.println("first");
//                            if (in == null || socket.isClosed()) {
//                                emitter.onError(new ConnectError(socket));
//                                System.out.println("second");
//                            }
//                            else {
//                                System.out.println("third");
//                                emitter.onNext(in);
//                            }
//                        }
//                    });
//        })
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(d -> disposables.put(socket.hashCode(), d))
//                .doOnError(this::handleError)
//                .onErrorComplete(err -> err instanceof ConnectError)
//                .doOnNext(System.out::println)
//                .subscribe(messageStream::onNext
//                        , err -> System.err.println(err.getMessage())
//                        , () -> System.out.println("Socket closed"));
//
//        // subscribe each newly connected client to the messagestream
//        messageStream
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(d -> messageDisposables.put(socket.hashCode(), d))
//                .withLatestFrom(socketToPrintWriterLogic(socket), (m, pw) -> {
//                    pw.println(m);
//                    pw.flush();
//                    return true;
//                })
//                .subscribe();
//    }
//
//    Observable<BufferedReader> socketToBufferedReaderLogic(Socket socket) {
//        return Observable.just(socket)
//                .map(Socket::getInputStream)
//                .map(InputStreamReader::new)
//                .map(BufferedReader::new);
//    }
//    Observable<PrintWriter> socketToPrintWriterLogic(Socket socket) {
//        System.out.println(socket.toString());
//        return Observable.just(socket)
//                .map(Socket::getOutputStream)
//                .map(OutputStreamWriter::new)
//                .map(PrintWriter::new);
//    }
//
//
//    private void shutdown() {
//        acceptConnections = false;
//    }
//
//    private void handleError(Throwable error) {
//        if (error instanceof ConnectError) {
//            Socket socket = ((ConnectError) error).getSocket();
//            disposables.get(socket.hashCode()).dispose();
//            disposables.remove(socket.hashCode());
//            messageDisposables.get(socket.hashCode()).dispose();
//            messageDisposables.remove(socket.hashCode());
//        }
//    }
//
//}
