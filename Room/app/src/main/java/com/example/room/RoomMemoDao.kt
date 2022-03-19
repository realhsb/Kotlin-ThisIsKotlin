package com.example.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/*
Data Access Object
데이터베이스에 접근해서 DML 쿼리 (SELECT, INSERT, UPDATE, DELETE)를 실행하는 메서드 모음
 */
@Dao
interface RoomMemoDao {
    /*
    다른 ORM툴과는 다르게 조회를 하는 select쿼리는 직접 작성하도록 설계
    대부분 ORM은 select도 메서드 형태로 제공
     */
    @Query("select * from orm_memo")
    fun getAll() : List<RoomMemo>

    @Insert(onConflict = REPLACE) //REPLACE : 동일한 값이 입력되었을 때 UPDATE쿼리로 실행
    fun insert(memo : RoomMemo)

    @Delete
    fun delete(memo : RoomMemo)
}