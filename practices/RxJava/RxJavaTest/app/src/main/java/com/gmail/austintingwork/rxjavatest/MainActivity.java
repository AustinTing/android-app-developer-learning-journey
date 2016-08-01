package com.gmail.austintingwork.rxjavatest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        examplePart01();
//        examplePart02();
        examplePart03();


    }

    private void examplePart01() {
//        example 1-01
        Observable<String> myObservable1 = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );
        Subscriber<String> mySubscriber1 = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d("debug", "Subscriber: onNext: " + s);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        };
        myObservable1.subscribe(mySubscriber1);

//        example 1-02 原始
        Observable<String> myObservable2 =
                Observable.just("Hello, world!");
        Action1<String> onNextAction2 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("debug", "onNextAction2: onNext: " + s);
            }
        };
//         obervable結束後執行一個方法？
//        myObservable2.subscribe(onNextAction2);


//        exmaple 1-02 簡化
        Observable.just("Hello, world!")
                .subscribe(s -> System.out.println(s));

//        example 1-03 operator 原始
        Observable.just("Hello, world!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " -Austin";
                    }
                })
                .subscribe(s -> System.out.println(s));
//        example 1-03 簡化
        Observable.just("Hello, world!")
                .map(s -> s + " -Dan")
                .subscribe(System.out::println);
//        example 1-04
        Observable.just("Hello, world!")
                .map(s -> s + " -Austin")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.println(s));

    }

    private void examplePart02() {

//        example 01 from()
//        query("Hello, world!")
//                .subscribe(urls -> {
//                    Observable.from(urls)
//                            .subscribe(url -> logIt(url));
//                });

//        example 02 flatMap()
        query("Hello, world!")
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .subscribe(url -> logIt("ex 03: " + url));

//        example 03 flatMap() 簡化
        query("Hello, world")
                .flatMap(urls -> Observable.from(urls))
                .subscribe(url -> logIt("ex 03 簡化: " + url));

//        example 04 flatMap() + flatMap()
        query("Hellom world")
                .flatMap(urls -> Observable.from(urls))
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return getTitle(s);
                    }
                })
                .subscribe(title -> logIt("ex 04: " + title));
//        example 04 flatMap() + flatMap() 簡化
        query("Hellom world")
                .flatMap(urls -> Observable.from(urls))
                .flatMap(url -> getTitle(url))
                .subscribe(title -> logIt("ex 04 簡化: " + title));

//        example 05 filter()
        query("Hellom world")
                .flatMap(urls -> Observable.from(urls))
                .flatMap(url -> getTitle(url))
                .filter(title -> title != null)
                .take(1)
                .subscribe(title -> logIt("ex 05 " + title));

    }

    private void examplePart03() {
//        ex 01 Error Handling
        Observable.just("Hello")
                .map(s -> potentialException(s))
                .map(s -> anotherPotentialException(s))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) { logIt("onNext "+s); }

                    @Override
                    public void onCompleted() { logIt("Completed! "); }

                    @Override
                    public void onError(Throwable e) { logIt(" Ouch!"); }
                });
//        ex 03 Subscriptions
        Subscription subscription = Observable.just("Hello").subscribe(s -> logIt(s));
        logIt("Subscription = "+ subscription.isUnsubscribed());    //??
        subscription.unsubscribe();
        logIt("Subscription = "+ subscription.isUnsubscribed());

    }




    private void logIt(String s) {
        Log.d(TAG, s);
    }

    Observable<List<String>> query(String text) {
        List<String> urls = new ArrayList<>();
        urls.add("url1");
        urls.add("url2");
        urls.add("url3");

        Observable<List<String>> myObservable = Observable.just(urls);
        return myObservable;
    }
    Observable<String> getTitle(String url) {
        String tittle = url;
        int rd = Math.random() > 0.5 ? 0 : 1;
        if (rd == 1) {
            tittle = null;
        }
        Observable<String> observable = Observable.just(tittle);
        return observable;

    }

    String potentialException(String s) {
        String st = s;
        int rd = Math.random() > 0.9 ? 0 : 1;
        if (rd == 0) {
            logIt("potentialException");
            throw new ArithmeticException("potential Exception");
        }
        return s ;
    }

    String anotherPotentialException(String s) {
        String st = s;
        int rd = Math.random() > 0.8 ? 0 : 1;
        if (rd == 0) {
            logIt("anotherPotentialException");
            throw new ArithmeticException("potential Another Exception");
        }
        return s;
    }
}
