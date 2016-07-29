package com.gmail.austintingwork.rxjavatest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Log.d("debug", "Subscriber: onNext: "+s);
            }

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }
        };
        myObservable1.subscribe(mySubscriber1);
//        example 1-02 原始
        Observable<String> myObservable2 =
                Observable.just("Hello, world!");
        Action1<String> onNextAction2 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("debug", "onNextAction2: onNext: "+s);
            }
        };
        myObservable2.subscribe(onNextAction2);


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
                .subscribe(s -> System.out.println(s));
//        example 1-04
        Observable.just("Hello, world!")
                .map(s -> s +" -Austin")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.println(s));


    }
}
