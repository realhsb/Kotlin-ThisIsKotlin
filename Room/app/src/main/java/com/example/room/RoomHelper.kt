package com.example.room

import androidx.room.Database
import androidx.room.RoomDatabase

/*
SQLiterOpenHelper를 상속받아 구현했던 것처럼 Room도 유사한 구조로 사용
Room은 RoomDatabase를 제공, 이것을 상속받아 클래스를 생성
대신 추상클래스로 생성해야 함.
 */


/*
entities : Room 라이브러리가 사용할 엔터티(테이블) 클래스 목록
version : 데이터베이스의 버전
exportSchema : true면 스키마 정보를 파일로 출력
 */
@Database(entities = arrayOf(RoomMemo::class), version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun roomMemoDao() : RoomMemoDao
    /*
    RoomHelper 클래스 안에 앞에서 정의한 Dao 인터페이스의 구현체를 사용할 수 있는 메서드명을 정의
     */
}