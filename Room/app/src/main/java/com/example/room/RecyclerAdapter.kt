package com.example.room

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

    /*
    Room > RoomHelper를 쓸 수 있도록 수정
     */
    var helper : RoomHelper? = null

    var listData = mutableListOf<RoomMemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val RoomMemo = listData.get(position)
        holder.setRoomMemo(RoomMemo)
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        /*
        홀더는 한 화면에 그려지는 개수만큼 만든 후 재사용
        1번 메모가 있는 홀더를 스크롤해서 위로 올리면 아래에서 올라오는 새로운 메모가 1번 홀더를 재사용하는 구조
        따라서, 클릭하는 시점에 어떤 데이터가 있는지 알아야 하므로, Holder 클래스의 init위에 변수 하나 선언
        setRoomMemo() 메서드로 넘어온 RoomMemo를 임시 저장
         */
        var mRoomMemo : RoomMemo? = null
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

                /*
                Room > deleteMemo() -> RoomHelper의 메서드
                RoomHelper를 사용할 때는 여러 개의 Dao가 있을 수 있으므로
                '헬퍼.Dao().메서드()'형태로 가운데 어떤 Dao를 쓸지 명시
                 */
                helper?.roomMemoDao()?.delete(mRoomMemo!!)
                /*
                deleteRoomMemo()는 null을 허용하지 않는데, mRoomMemo는 null을 허용하도록 설정되었기 때문에
                !!를 사용해서 강제해야 함.
                 */
                notifyDataSetChanged()

            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun setRoomMemo(RoomMemo: RoomMemo){
            itemView.textNo.text = "${RoomMemo.no}"
            itemView.textContent.text = RoomMemo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            //날짜 포맷은 SimpleDateFormat으로 설정
            itemView.textDatetime.text = "${sdf.format(RoomMemo.datetime)}"

            this.mRoomMemo = RoomMemo
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
//    fun setRoomMemo(RoomMemo:RoomMemo){
//        itemView.textNo.text = "${RoomMemo.no}"
//        itemView.textContent.text = RoomMemo.content
//        val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
//            //날짜 포맷은 SimpleDateFormat으로 설정
//        itemView.textDatetime.text = "${sdf.format(RoomMemo.datetime)}"
//    }
//}