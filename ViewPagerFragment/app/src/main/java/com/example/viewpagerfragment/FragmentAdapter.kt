package com.example.viewpagerfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    var fragmentList = listOf<Fragment>()

    override fun getCount(): Int {
        //getCount() : 어댑터가 화면에 보여줄 전체 프래그먼트의 개수를 반환
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        //getItem() : 현재 페이지의 position이 파라미터로 넘어옴. position에 해당하는 위치의 프래그먼트를 반환
        /*
        페이지가 요청될 때 getItem()으로 요청되는 페이지의 position 값이 넘어옴
        position 값을 이용해서 프래그먼트 목록에서 해당 position에 있는 프래그먼트 1개를 리턴
         */
        return fragmentList.get(position)
    }
}