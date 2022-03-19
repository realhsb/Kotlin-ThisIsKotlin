package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var total = 0
    var started = false

    var handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total/60)
            val second = String.format("%02d", total%60)
            textTimer.text = "$minute:$second"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        buttonStart에 클릭리스너 달기
        버튼이 클릭되면 started 를 true로 변경, 새로운 스레스 실행
        스레드는 while문의 started가 true인 동안 while문을 반복하면서 1초에 한 번씩 total의 값을 1씩 증가 & 핸들러에 메시지 전송
        핸들러를 호출하는 곳이 하나밖에 없으므로 메시지에 0을 담아 호출
         */
        buttonStart.setOnClickListener {
            started = true
            thread(start=true){
                while (started){
                    Thread.sleep(1000)
                    if(started){
                        total = total + 1
                        handler?.sendEmptyMessage(0)
                    }
                }
            }
        }

        buttonStop.setOnClickListener {
            if(started) {
                started = false
                total = 0
                textTimer.text = "00:00"
            }
        }
    }
}