package com.example.customtext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomText : AppCompatTextView {
    constructor(context : Context)
            : super(context) {
    }

    fun process(delimeter : String){
        var one = text.substring(0,4)       //입력된 텍스트의 앞 4글자 자르기
        var two = text.substring(4,6)       //입력된 텍스트의 중간 2글자 자르기
        var three = text.substring(6)       //입력된 텍스트의 마지막 2글자 자르기

        setText("$one $delimeter $two $delimeter $three")   //자른 글자 사이에 delimeter를 넣어서 화면에 세팅
    }

    constructor(context : Context, attrs : AttributeSet)
            : super (context, attrs){
        val typed = context.obtainStyledAttributes(attrs, R.styleable.CustomText)   //res/values/attrs.xml에 정의된 어트리뷰트 가져옴
        val size = typed.indexCount

        for (i in 0 until size){
            when(typed.getIndex(i)){
                R.styleable.CustomText_delimeter -> {   //현재 속성을 확인하고 delimeter와 같으면
                    val delimeter = typed.getString(typed.getIndex(i)) ?: "-"  //xml에 입력된 delimeter 값을 꺼내고
                    process(delimeter)  //꺼낸 값을 process()메서드에 넘겨서 처리, 밑에서 메서드 구현
                }
            }
        }
    }
    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int)
            : super (context, attrs, defStyleAttr){

        }
}