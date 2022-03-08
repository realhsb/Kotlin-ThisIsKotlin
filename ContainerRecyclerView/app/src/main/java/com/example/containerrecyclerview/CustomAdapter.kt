package com.example.containerrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.text.SimpleDateFormat

class CustomAdapter : RecyclerView.Adapter<Holder>() {

    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
        //한 화면에 여덟 줄이 보여지면 여덟 번 호출

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
        /*LayoutInflater를 사용하면 특정 XML파일을 개발자가 직접 클래스로 변환할 수 있음.
        화면 요소이므로 컨텍스트가 필요, inflate()메서드에 레이아웃을 지정해서 호출하면 클래스로 변환
        변환된 레이아웃 클래스는 바로 사용 불가, onCreateViewHolder()의 리턴 타입인 Holder 클래스에 담아 사용 (메모리에 저장 및 사용)
         */

        //return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false))
        /*
        inflate(resource, root, attachToRoot)
        resource : View로 생성할 레이아웃 파일명(id)
        root : attachToRoot가 true 또는 false에 따라 root의 역할 결정
        attachToRoot : true일 경우 attach해야 하는 대상으로 root를 지정하고 아래에 붙임,
                       false일 경우 View의 최상위 레이아웃의 속성을 기본으로 레이아웃 적용
         */
    }

    override fun getItemCount(): Int {
        //목록에 보여줄 아이템의 개수
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //생성된 아이템 레이아웃에 값 입력 후 목록에 출력

        val memo = listData.get(position)
        holder.setMemo(memo)

    }


}

class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
    //Holder 클래스가 생성되는 시점에 클릭리스너를 추가하려면 init을 추가
    init{
        itemView.setOnClickListener{
            //아이템뷰에 클릭리스너를 달고 리스너 블록 안에 토스트로 간단한 메시지를 보여주는 코드를 setMemo() 메서드 위에 작성
            Toast.makeText(itemView?.context, "클릭된 아이템 = ${itemView.textTitle.text}",
            Toast.LENGTH_LONG).show()
        }

    }
    fun setMemo(memo : Memo){
        itemView.textNo.text = "${memo.no}"
        itemView.textTitle.text = memo.title

        var sdf = SimpleDateFormat("yyyy/MM/dd")
        var formattedDate = sdf.format(memo.timestamp)
        itemView.textDate.text = formattedDate
    }
}