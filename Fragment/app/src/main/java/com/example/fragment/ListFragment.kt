package com.example.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    var mainActivity : MainActivity? = null


    override fun onCreateView(
        /*
        inflater : 레이아웃 파일을 로드하기 위한 레이아웃 인플레이터를 기본으로 제공
        container : 프래그먼트 레이아웃이 배치되는 부모 레이아웃 (액티비티의 레이아웃)
        savedInstanceState : 상태값 저장을 위한 보조 도구, 액티비티의 onCreate의 파라미터와 동일하게 동작
        */
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*** 원본 코드 : inflater로 생성한 뷰를 바로 리턴하는 구조입니다
        return inflater.inflate(R.layout.fragment_list, container, false)
        ***/

        /*** 수정 코드 : inflater로 생성한 뷰(fragment_list)를 view 변수에 담아두고,
         * view 안에 있는 버튼에 리스너를 등록한 후에 리턴
         */
        val view = inflater!!.inflate(R.layout.fragment_list, container, false)
        view.btnNext.setOnClickListener { mainActivity?.goDetail()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }
}