package com.example.viewpagerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 프래그먼트 목록 생성
        val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())

        // adapter 생성, 앞에서 생성해 둔 프래그먼트 목록 저장
        // adapter의 첫 번쨰 파라미터는 항상 suuportFragmentManager 사용
        val adapter = FragmentAdapter(supportFragmentManager, 1)
        //미리 정의된 FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT가 너무 길어 해당값인 1 사용

        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter
    }
}
