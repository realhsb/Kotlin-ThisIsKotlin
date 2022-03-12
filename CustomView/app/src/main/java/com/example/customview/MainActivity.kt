package com.example.customview

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = CustomView("Hi Kotlin", this)
        frameLayout.addView(customView)
    }
}

class CustomView(text : String, context : Context) : View(context){
    val text : String
    init {
        this.text = text
    }

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
        canvas?.drawText(text, 0f, 100f, paint) //drawText 메서드

        //View에 그림 그리기

        //drawCircle : 원 그리기
        //drawCircle(원의 x축 중심, 원의 y축 중심, 반지름, 페인트)
        val blue = Paint()
        blue.style = Paint.Style.FILL
        blue.color = Color.BLUE
        canvas?.drawCircle(150f, 300f, 100f, blue)

        //drawArc() : 원호 그리기
        //STROKE 스타일을 사용하면 도형의 외곽선을 그릴 수 있다
        val red = Paint()
        red.style = Paint.Style.STROKE
        red.color = Color.RED
        canvas?.drawCircle(400f, 300f, 100f, red)

        //drawRect() : 사각형 그리기
        //사각형을 그리기 전에 Rect 클래스에 사각형의 left, top, right, bottom 좌표를 입력해서 생성
        val green = Paint()
        green.style = Paint.Style.STROKE
        green.strokeWidth = 20f
        val rect = Rect(50, 450, 250, 650)
        canvas?.drawRect(rect, green)

        //drawRoundRect() : 라운드 사각형 그리기
        //RectF 클래스 사용, 좌표값을 Float로 입력하므로 소수점 이하 좌표 표현 가능
        //메서드의  번쨰(rx)와 세 번째(ry) 파라미터가 라운드의 크기를 결정, 동일한 값을 입력해야 일반적인 형태 나옴
        val cyan = Paint()
        cyan.style = Paint.Style.FILL
        cyan.color = Color.CYAN
        val rectF = RectF(300f, 450f, 650f, 650f)
        canvas?.drawRoundRect(rectF, 50f, 50f, cyan)
    }
}