package com.example.sayhello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.sayhello.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btn = findViewById<Button>(R.id.btnSay)
        // 가장 기본적인 객체 선언방법
//        btn.setOnClickListener {
//            textSay.setText("Hello Kotlin!")
//            Log.d("btn","눌림")
//        }

        btnSay.setOnClickListener {  }
        // 코틀린 익스텐션을 이용한 야매 선언
        // 구글에서 비추함

        binding.btnSay.setOnClickListener {
            textSay.setText("Hello Kotlin!")
            Log.d("btn","눌림")
        }
        // 뷰바인딩을 이용한 객체 선언
    }
}