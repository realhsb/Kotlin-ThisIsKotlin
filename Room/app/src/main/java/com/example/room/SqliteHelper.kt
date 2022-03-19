package com.example.room

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context : Context, name : String, version : Int) :
SQLiteOpenHelper(context, name, null, version){
    /*
    Context, 데이터베이스명, 팩토리(사용하지 않아도 됨), 버전 정보를 필요
    나머지 세 가지 정보를 내가 만든 클래스의 생성자에 파라미터로 정의한 후, 상속받은 SQLiteOpenHelper에 전달
     */

    override fun onCreate(db: SQLiteDatabase?) {
        /*SqliterHelper 첫 실행시, 데이터베이스가 아직 생성X 그러므로 Oncreate() 메서드에 테이블 생성
        이 메서드 안에 테이블 생성 쿼리 작성 및 실행. 데이터베이스가 생성되어있으면 더 이상 실행X
        onCreate() 메서드 안에 앞에서 만든 테이블 생성 쿼리를 문자열로 입력한 후, db의 execSQL() 메서드에 전달 및 실행
         */
        val create = "create table memo (" +
                "no integer primary key, " +
                "content text, " +
                "datetime integer" +
                ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //SqliteHelper 클래스에 데이터 삽입 메서드(INSERT)를 구현
    fun insertMemo(memo : Memo){    //삽입 메서드
        val values = ContentValues()
        //Map 클래스처럼 키, 값 형태로 사용되는 ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        /*
        상속받은 SQLiteOpenHelper에 이미 구현된 writableDatabase에 테이블명과 함께 앞에서
        작성한 값을 전달해서 insert()하고, 사용한 후에는 close()를 호출해서 꼭 닫아줌
         */
        val wd = writableDatabase
        wd.insert("memo", null, values)
        wd.close()
    }

    //SqliteHelper 클래스에 데이터 조회 메서드(SELECT)를 정의
    @SuppressLint("Range")
    fun selectMemo() : MutableList<Memo>{   //조회 메서드
        /*
        조회 메서드는 반환값이 있으므로 메서드의 가장 윗줄에 반환할 값을 변수로 선언, (가장 아랫줄에서 반환하는 코드 작성) 그 사이에 코드 구현
         */
        val list = mutableListOf<Memo>()

        //메모의 전체 데이터를 조회하는 쿼리 작성
        val select = "select * from memo"

        //읽기 전용 데이터베이스를 변수에 담기
        val rd = readableDatabase

        //데이터베이스의 rawQuery() 메서드에 앞에서 작성해둔 쿼리를 담아서 실행하면 커서(cursor) 형태로 값 반환
        val cursor = rd.rawQuery(select, null)

        /*
        커서의 moveToNext() 메서드가 실행되면 다음 줄에 사용할 수 있는 레코드가 있는지 여부 반환
        해당 커서를 다음 위치로 이동. 레코드가 없으면 반복문 탈출. 모든 레코드를 읽을 때까지 반복
         */
        while (cursor.moveToNext()){
            //반복문을 돌면서 테이블에 정의된 3개의 컬럼에서 값을 꺼낸 후 각각 변수에 담음
            /*
            val 컬럼 위치 = cursor.getColumnIndex("컬럼명")
            cursor.getLong(컬럼 위치)
             */
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            //위 변수들에 저장해뒀던 값들로 Memo 클래스 생성 및 반환할 목록에 더함
            list.add(Memo(no, content, datetime))
        }

        /*
        메모리 누수 : 데이터베이스를 사용하면 스마트폰의 시스템 자원(CPU, 메모리 등)이 점유되어서 시스템 자원이 낭비됨
        한 번 점유된 자원은 close()를 호출할 때까지 반환되지 않음, 사용이 끝나면 반드시 close() 호출
         */
        cursor.close()
        rd.close()

        return list
    }

    fun updateMemo(memo : Memo){    //수정 메서드
        //ContentValues()를 사용해서 수정할 값 저장
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        /*
        writableDatabase의 update() 메서드를 사용해서 수정한 다음 close() 호출
        update() 메서드의 파라미터는 총 4개인데 (테이블명, 수정할 값, 수정할 조건)
        수정할 조건은 PRIMARY KEY로 지정된 컬럼을 사용하며 여기서는 PRIMARY KEY인 컬럼이 no이기 때문에
        "no = 숫자". 네 번째 값은 'null'입력
         */
        val wd = writableDatabase
        wd.update("memo", values, "no = &{memo.no}", null)
        wd.close()

    }

    //DELETE FROM 테이블명 WHERE 조건식
    fun deleteMemo(memo : Memo){
        /*
        SqliteHelper 클래스에 데이터 삭제 메서드 정의
        조건식은 "컬럼명 = 값"의 형태. 삭제 쿼리를 작성하고 변수에 저장
         */
        val delete = "delete from memo where no = ${memo.no}"

        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

data class Memo(var no : Long?, var content : String, var datetime: Long)
/*
no와 datetime의 타입을 데이터베이스에서는 INTEGER로 정의했지만 여기서는 LONG -> 숫자 범위가 다르기 때문
특별한 이유가 없다면 SQLite에서 INTEGER로 선언한 것은 소스 코드에서는 LONG 사용
no만 null을 허용한 것은 PRIMARY KEY 옵션으로 값이 자동 증가되므로 데이터 삽입(INSERT) 시에는 필요하지 않음
 */