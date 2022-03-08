package com.example.containerrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun loadData() : MutableList<Memo>{
            val data:MutableList<Memo> = mutableListOf()
            for (no in 1..100){
                val title = "this is kotlin $no"
                val date = System.currentTimeMillis()
                var memo = Memo(no, title, date)
                data.add(memo)
            }
            return data
        }

        //사용할 데이터 생성
        val data:MutableList<Memo> = loadData()

        // 어댑터 생성, 어댑터의 listData 변수에 data 저장
        var adapter = CustomAdapter()

        //recyclerView 위젯의 adapter 속성에 생성할 어댑터 연결
        adapter.listData = data
        recyclerView.adapter = adapter

        //리사이클러뷰를 화면에 보여주는 형태를 결정하는 레이아웃 매니저 (Layout Manager) 연결
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}