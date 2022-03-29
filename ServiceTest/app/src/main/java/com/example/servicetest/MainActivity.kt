package com.example.servicetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
    서비스 호출 코드
    안드로이드에 전달할 인텐트 생성, MyService에 미리 정의해둔 명령을 action에 담아 같이 전달
    새로운 메서드를 만들 때 파라미터로 (view : View)를 사용하면 클릭리스너 연결이 없어도 레이아웃 파일에서 메서드에 직접 접근 가능
     */
    fun serviceStart(view : View){
        val intent = Intent(this, MyService::class.java)
        intent.action = MyService.ACTION_START
        startService(intent)
    }

    fun serviceStop(view : View){
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    // 서비스 중지 상태를 확인하기 위해서 MyService에 서비스 종료 시 호출되는 onDestroy() override
    override fun onDestroy() {
        Log.d("Service", "서비스가 종료되었습니다.")
        super.onDestroy()
    }
}