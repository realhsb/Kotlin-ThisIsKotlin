package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Room 라이브러리는 @Entity 어노테이션이 적용된 클래스를 찾아 테이블로 변환
데이터베이스에서 테이블명을 클래스명과 다르게 하고 싶을 때는 @Entity(tableName = "테이블명")
여기서는 테이블명 : orm_memo
 */
@Entity(tableName = "orm_memo")
class RoomMemo {
    /*
    멤버 변수 위에 @ColumnInfo 어노테이션을 작성해서 테이블의 칼럼으로 사용된다는 것을 명시
    컬럼명을 변수명과 다르게 하고 싶을 때 @ColumnInfo(name = "컬럼명)
     */

    /*
    변수를 테이블의 컬럼으로 사용하고 싶지 않을 때
    @Ignore 어노테이션 적용
    @Ignore
    var temp : String = "임시로 사용되는 데이터입니다."
     */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no : Long? = null

    @ColumnInfo
    var content : String = ""

    @ColumnInfo(name = "date")
    var datetime : Long = 0

    constructor(content : String, datetime : Long){
        this.content = content
        this.datetime = datetime
    }
}