package se.miun.dt176g.xxxxyyyy.reactive;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReactiveServer2 {

    private static ReplaySubject<Shape> shapeStream;
    private static Subject<String> stringStream;
    private static ArrayList<Shape> shapeList;

    //private static Drawings drawings;
    //Drawings drawings;

    public static void main(String[] args) throws Exception {

        ServerSocket ssock = new ServerSocket(12346);
        System.out.println("Creating client stream");
        shapeStream = ReplaySubject.create();
        shapeList = new ArrayList<>();

        stringStream = ReplaySubject.create();
        //drawings = new Drawings();


        Observable.<Socket>create(emitter -> {
            while (true) {
                System.out.println("Accepting connection on: " + thread());
                System.out.println("run");

                emitter.onNext(ssock.accept());
                System.out.println("run2");


            }
        })
                .map(Socket::getInputStream)
                .map(ObjectInputStream::new)
                .flatMap(objectInputStream -> Observable.create(s -> {
                    try {
                        while (true) {
                            //System.out.println("run3");
                            s.onNext(objectInputStream.readObject());
                            System.out.println(shapeList.size());
                            //System.out.println("runt4");
                        }
                    } catch (IOException ioException) {
                        System.out.println("Disconnected" + ioException);
                    }
                }).subscribeOn(Schedulers.io()))
                .map(shape -> (Shape) shape)
                        .subscribe(ReactiveServer2::addShape);

                //.map(s -> (String) s)

//                .subscribe(stringStream::onNext
//                        , err -> System.err.println(err.getMessage())
//                        , () -> System.out.println("Done"));

                //.subscribe(shape -> shape.tester());




        shapeStream.subscribeOn(Schedulers.io())
                .subscribe(Shape::tester);



        shapeStream.subscribe(s -> System.out.println(s));

        System.out.println("Sleeping");

        Thread.sleep(Long.MAX_VALUE);
        System.out.println("Done");
    }

//    private static PublishSubject<ArrayList<Shape>> createPublish(Shape shape) {
//        ArrayList<Shape> shapes = new ArrayList<>();
//        shapes.add(shape);
//        PublishSubject<ArrayList<Shape>> shapesSubject = PublishSubject.create();
//        shapesSubject.onNext(shapes);
//        return shapesSubject;
//
//
//    }
    private static void addShape(Shape s) {
        shapeList.add(s);
        for (Shape shape :
                shapeList) {
            shape.tester();
        }
    }




    public static String thread() {
        Thread current = Thread.currentThread();
        return "(Thread: " + current + " (id = " + current.getId() + "))";
    }

}
