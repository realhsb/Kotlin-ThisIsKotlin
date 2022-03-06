package com.example.widgetsseekbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress"
                //텍스트뷰의 text 속성에는 문자열만 입력 가능. 텍스트뷰의 text에 Int타입의 숫자를 입력하면
                //리소스 아이디로 인식해서 오류발생. 따라서 Int타입의 숫자를 입력할 때는 쌍따옴표("")안에 $변수를 넣는 형태로 입력
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }
}