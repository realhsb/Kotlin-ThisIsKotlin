package com.example.sqlite

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    /*
    삭제 버튼을 사용하기 위해 MainActivity.kt에서 adpater.helper = helper 진행
    이때 어댑터에는 helper 변수가 없기 때문에 추가로 변수 작성
     */
    var helper : SqliteHelper? = null

    var listData = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData.get(position)
        holder.setMemo(memo)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        /*
        홀더는 한 화면에 그려지는 개수만큼 만든 후 재사용
        1번 메모가 있는 홀더를 스크롤해서 위로 올리면 아래에서 올라오는 새로운 메모가 1번 홀더를 재사용하는 구조
        따라서, 클릭하는 시점에 어떤 데이터가 있는지 알아야 하므로, Holder 클래스의 init위에 변수 하나 선언
        setMemo() 메서드로 넘어온 Memo를 임시 저장
         */
        var mMemo : Memo? = null
        init {
            //Holder 클래스에 init 블록을 만들고 buttonDelete에 클릭리스터를 달아줌
            itemView.buttonDelete.setOnClickListener {
                /*
                삭제 버튼을 클릭하면 어댑터의 helper와 listData에 접근
                기존의 Holder 클래스는 어댑터 밖에 있으므로 접근 불가
                밖에 있는 Holder 클래스 전체를 어댑터 클래스 안으로 옮기고 class 앞에 inner 키워드 추가
                밖에 있는 Holder 클래스는 주석처리해둠.
                 */

                //SQLiter 데이터를 먼저 삭제 후에, listData의 데이터도 삭제, 그리고 어댑터 갱신

                helper?.deleteMemo(mMemo!!)
                /*
                deleteMemo()는 null을 허용하지 않는데, mMemo는 null을 허용하도록 설정되었기 때문에
                !!를 사용해서 강제해야 함.
                 */
                notifyDataSetChanged()

            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun setMemo(memo:Memo){
            itemView.textNo.text = "${memo.no}"
            itemView.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            //날짜 포맷은 SimpleDateFormat으로 설정
            itemView.textDatetime.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }
    }
}

//class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
//    init {
//        //Holder 클래스에 init 블록을 만들고 buttonDelete에 클릭리스터를 달아줌
//        itemView.buttonDelete.setOnClickListener {
//            /*
//            삭제 버튼을 클릭하면 어댑터의 helper와 listData에 접근
//            지금은 어댑터 밖에 Holder클래스가 있으므로 접근 불가
//            밖에 있는 Holder 클래스 전체를 어댑터 클래스 안으로 옮기고 class 앞에 inner 키워드 추가
//            밖에 있는 홀더 클래스는 주석처리해둠.
//             */
//        }
//    }
//
//
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun setMemo(memo:Memo){
//        itemView.textNo.text = "${memo.no}"
//        itemView.textContent.text = memo.content
//        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
//            //날짜 포맷은 SimpleDateFormat으로 설정
//        itemView.textDatetime.text = "${sdf.format(memo.datetime)}"
//    }
//}