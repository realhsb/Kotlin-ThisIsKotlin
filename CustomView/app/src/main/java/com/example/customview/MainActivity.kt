package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}

class CustomView(context : Context) : View(context){
    override fun onDraw(canvas : Canvas?){
        //canvas는 일종의 그리기 도구, 그림판과 함께 그림을 그리기 위해서 draw로 시작하는 메서드 제공
        super.onDraw(canvas)

        /*
        Canvas 의 drawText() : 텍스트를 출력
        Paint : 출력할 문자열, 가로세로 좌표 그리고 글자의 색과 두께 정보 소유
         */
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 100f
        canvas?.drawText("안녕하세요", 0f, 0f, paint) //drawText 메서드

    }
}