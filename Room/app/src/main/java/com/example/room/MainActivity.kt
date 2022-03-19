package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //val helper = SqliteHelper(this, "memo", 1)
    var helper : RoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        helper 세팅 수정
        databaseBuilder() 메서드의 세 번째 파라미터가 실제 생성되는 DB 파일의 이름
        Room은 기본적으로 서브 스레드에서 동작하므로 allowMainThreadQueries() 옵션이 없으면 앱이 동작 멈춤
         */
        helper = Room.databaseBuilder(this, RoomHelper::class.java, "room_memo")
            .allowMainThreadQueries()
            .build()

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

        /*
        helper와 roomMemoDao()에 null이 허용되므로 helper 안의 코드를 사용하기 위해 ? 사용
        adapter의 listData에 null이 허용되지 않으므로 ?: (Elvis Operator)를 사용해서 앞의 2개가 null일 경우
        사용할 디폴트 값 설정
         */
        adapter.listData = helper?.roomMemoDao()?.getAll() ?: mutableListOf()

        //adapter의 listData에 데이터베이스에서 가져온 데이터 세팅
        adapter.listData.addAll(helper.selectMemo())



        //화면의 리사이클러뷰 위젯이 adapter를 연결하고 레이아웃 매니저를 설정
        recyclerMemo.adapter = adapter
        recyclerMemo.layoutManager = LinearLayoutManager(this)

        //저장 버튼에 클릭리스터 달아주기
        buttonSave.setOnClickListener {
            //메모를 입력하는 플레인텍스트를 검사해서 값이 있으면 해당 내용으로 Memo클래스 생성
            if(editMemo.text.toString().isNotEmpty()){
                val memo = RoomMemo(editMemo.text.toString(), System.currentTimeMillis())
                helper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()
                adapter.listData.addAll(helper?.roomMemoDao()?.getAll()) ?: mutableListOf())
                adapter.notifyDataSetChanged()

                editMemo.setText("")

//                val memo = Memo(null, editMemo.text.toString(), System.currentTimeMillis())
//                helper.insertMemo(memo)
//                adapter.listData.clear()
//                adapter.listData.addAll(helper.selectMemo())
//                adapter.notifyDataSetChanged()
//                editMemo.setText("")
//
            }
        }
    }
}