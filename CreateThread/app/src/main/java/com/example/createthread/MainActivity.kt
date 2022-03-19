package com.example.createthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1.
        /*
        WorkerThread 객체를 생성해 별도의 스레드 생성
        start() 메서드를 호출하면 run() 메서드에 정의된 로직을 생성된 스레드가 처리
         */
        var workerThread =  WorkerThread()
        workerThread.start()

        /*
        Runnable 인터페이스로 구현한 객체는 Thread 클래스의 생성자로 전달하고
        Thread 클래스의 start() 메서드를 호출해야 스레드 생성
         */
        var workerRunnable = Thread(WorkerRunnable())
        workerRunnable.start()
    }

    //1. Thread 객체
    // Thread 클래스를 상속받아 스레드 생성
    class WorkerThread : Thread() {
        override fun run(){
            var i = 0
            while (i < 10){
                i += 1
                Log.i("WorkerThread", "$i")
            }
        }
    }

    //2. Runnable 인터페이스
    /*
    다중 상속을 허용하지 않는 코틀린의 언어의 특성상
    상속 관계에 있는 클래스도 구현할 수 있도록 지원하는 모델
     */
    class WorkerRunnable : Runnable {
        override fun run()  {
            var i = 0
            while(i < 10){
                i += 1
                Log.i("WorkerRunnable", "$i")
            }
        }
    }

    

}