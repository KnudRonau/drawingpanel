package se.miun.dt176g.xxxxyyyy.reactive;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ReactiveServer {

    public class ConnectError extends Throwable {
        private static final long serialVersionUID = 1L;
        private final Socket socket;

        public ConnectError(Socket socket) {
            this.socket = socket;
        }

        public Socket getSocket() {
            return socket;
        }
    }

    private Map<Integer, Disposable> disposables;    //key: socket hash
    private Map<Integer, Disposable> shapeDisposables;

    private Subject<Socket> connections;
    private Subject<Shape> shapeStream;   //all messages from all clients
    private ServerSocket serverSocket;
    private boolean acceptConnections = true;


    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            ReactiveServer server = new ReactiveServer();
            Disposable disposable = Observable.just(server)
                    .subscribeOn(Schedulers.single())
                    .doOnNext(ReactiveServer::run)
                    .doOnDispose(server::shutdown)
                    .doOnSubscribe(d -> System.out.println("Server is running...press <enter> to stop"))
                    .subscribe();

            br.readLine();

            disposable.dispose();
            Schedulers.shutdown();

            System.out.println("-- Shutting down --");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        disposables = new HashMap<>();
        shapeDisposables = new HashMap<>();
        connections = PublishSubject.create();
        shapeStream = ReplaySubject.createWithSize(100);  //replay 3 last messages to new clients

        // listen for requests on separate thread
        Completable.create(emitter -> listenForIncomingConnectionRequests())
                .subscribeOn(Schedulers.single())
                .subscribe();

        connections
                .doOnNext(s -> System.out.println("tcp connection accepted..."))
                .subscribe(this::listenToSocket);
    }

    private void listenForIncomingConnectionRequests() throws IOException {
        serverSocket = new ServerSocket(12345);

        while (acceptConnections) {
            Socket socket = serverSocket.accept();
            Observable.<Socket>create(emitter -> emitter.onNext(socket))
                    .observeOn(Schedulers.io())
                    .subscribe(connections);
        }

    }

    private void listenToSocket(Socket socket) throws IOException{
        //System.out.println("listen run");
        //inject all incoming messages from this socket to the message stream

        Observable.<Shape>create(emitter -> {
            Observable.just(socket)
                    .map(Socket::getInputStream)
                    .map(ObjectInputStream::new)
                    .subscribe(objectInputStream -> {
                        while (!emitter.isDisposed()) {
                            Shape toShape = (Shape) objectInputStream.readObject();
                            if (toShape == null ||socket.isClosed()) {
                                emitter.onError(new ConnectError(socket));
                                //System.out.println("second");
                            } else {
                                    //System.out.println("third");
                                    emitter.onNext(toShape);
                            }
                        }
                    });
        })

                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> disposables.put(socket.hashCode(), d))
                .doOnError(this::handleError)
                .onErrorComplete(err -> err instanceof ConnectError)
                //.doOnNext(shape -> shape.tester())
                .subscribe(shapeStream::onNext,
                        err -> System.err.println(err.getMessage()),
                        () -> System.out.println("Socket closed"));

            shapeStream
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(d -> shapeDisposables.put(socket.hashCode(), d))
                    .withLatestFrom(socketToOutputStream(socket), (s, oos) -> {
                        oos.writeObject(s);
                        oos.flush();
                        return true;
                    })
                    .subscribe();

    }


    Observable<ObjectOutputStream> socketToOutputStream(Socket socket) {
        return Observable.just(socket)
                .map(Socket::getOutputStream)
                .map(ObjectOutputStream::new);
    }

    private void shutdown() {
        acceptConnections = false;
    }


    private void handleError(Throwable error) {
        if (error instanceof ConnectError) {
            Socket socket = ((ConnectError) error).getSocket();
            disposables.get(socket.hashCode()).dispose();
            disposables.remove(socket.hashCode());
            shapeDisposables.get(socket.hashCode()).dispose();
            shapeDisposables.remove(socket.hashCode());
        }
    }

}

