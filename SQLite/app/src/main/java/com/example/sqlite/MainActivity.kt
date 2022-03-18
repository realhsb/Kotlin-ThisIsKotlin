package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val helper = SqliteHelper(this, "memo", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //onCreate() 안에 RecyclerAdapter 생성
        val adapter = RecyclerAdapter()

        /*
        메모를 삭제하기 위해 SQLite의 데이터와 어댑터에 있는 Memo 컬렉션의 데이터를 삭제
        SQLite의 데이터를 삭제하기 위해서 MainActivity에서 생성한 helper를 어댑터로 전달하는 코드를
        어댑터를 생성한 코드 바로 아래에 작성
        아직 어댑터에는 helper 변수가 없기 때문에 빨간색으로 나타남
        그래서 RecylcerAdapter.kt 클래스 가장 윗줄에 helper 변수를 추가로 작성
         */
        adapter.helper = helper

        //adapter의 listData에 데이터베이스에서 가져온 데이터 세팅
        adapter.listData.addAll(helper.selectMemo())

        //화면의 리사이클러뷰 위젯이 adapter를 연결하고 레이아웃 매니저를 설정
        recyclerMemo.adapter = adapter
        recyclerMemo.layoutManager = LinearLayoutManager(this)

        //저장 버튼에 클릭리스터 달아주기
        buttonSave.setOnClickListener {
            //메모를 입력하는 플레인텍스트를 검사해서 값이 있으면 해당 내용으로 Memo클래스 생성
            if(editMemo.text.toString().isNotEmpty()){
                val memo = Memo(null, editMemo.text.toString(), System.currentTimeMillis())

                //helper클래스의 insertMemo() 메서드에 앞에서 생성한 Memo를 전달해 데이터베이스에 저장
                helper.insertMemo(memo)

                //어댑터의 데이터를 모두 초기화
                adapter.listData.clear()

                //데이터베이스에서 새로운 목록을 읽어와 어댑터에 세팅하고 갱신
                //새로 생성되는 메모에는 자동으로 번호 입력. 번호를 갱신하기 위해 새로운 데이터 세팅
                adapter.listData.addAll(helper.selectMemo())
                adapter.notifyDataSetChanged()

                //메모 내용을 입력하는 위젯의 내용 초기화
                editMemo.setText("")
            }
        }
    }
}