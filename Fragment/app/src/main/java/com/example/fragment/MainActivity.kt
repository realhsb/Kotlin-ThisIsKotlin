package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setFragment()
    }

    fun goDetail(){
        //next버튼이 ListFragment에 있지만 코드는 MainActicity.kt에 작성합니다.
        val detailFragment = DetailFragment()

        //생성된 detailFragment를 액티비티에 삽입하기 위해 setFragmetnㅇ 작성했던 코드 세 줄을 복사해서 붙여 넣기
        //그리고 listFragment에만 detailFragment로 다음처럼 수정
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, detailFragment)

        //transaction의 add()와 commit() 사이에 addToBackStack() 추가
        //스마트론의 뒤로가기 버튼을 사용할 수 있음
        //이를 사용하지 않은 채로 뒤로가기를 하면 액티비티가 종료됨
        /*
        addToBackStack을 사용하면 프래그먼트를 삽입하기 위해 사용되는 트랜잭션을 하나의 액티비티처럼 백스택에 담아둘 수 있다.
        따라서 스마트폰의 뒤로가기 버튼으로 트랜잭션 전체를 마치 액티비티처럼 제거 가능

        주의 : 개별 프래그먼트가 아닌 트랜잭션 전체가 담기기 때문에 add나 replace에 상관없이 해당 트랜잭션 전체가 제거
         */
        transaction.addToBackStack("detail")

        transaction.commit()
    }

    fun goBack(){
        onBackPressed()
    }

    /*
    트랜잭션이란?
        여러 개의 의존성이 있는 동작을 한 번에 실행할 때 중간에 하나라도 잘못되면 모든 동작을 복구하는
        하나의 작업 단위...
        은행에서 송금하는 과정을 예로 들자. 네트워크로 연결된 2개의 은행 시스템을 두고 전송하는 은행에서 100만 원을 전송했는데,
        수신받는 은행에서 100만 원을 수신하지 못했다면 전송하는 은행에서 취소해야 한다.
        이때 전송과 수신하는 은행을 하나의 트랜잭션으로 묶어서 어느 한쪽에 문제가 있으면 트랜잭션 내부에서 처리된 모든 작업을 취소
     */

    /*
    fun setFragment(){
        val listFragment : ListFragment = ListFragment()

        //액티비티가 가지고 있는 프래그먼트 매니저를 통해서 트랜잭션을 시작하고
        //시작한 트랜잭션을 변수에 저장
        val transaction = supportFragmentManager.beginTransaction()

        //트랜잭션의 add() 매서드로 frameLayout을 id로 가지고 있는 레이아웃에 앞에서
        transaction.add(R.id.frameLayout, listFragment)

        //commit()메서드로 모든 작업이 정상적으로 처리되었음을 트랜잭션에 알려주면 작업이 반영
        transaction.commit()



    }
     */
}