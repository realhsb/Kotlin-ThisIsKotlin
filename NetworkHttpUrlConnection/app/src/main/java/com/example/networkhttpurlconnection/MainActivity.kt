package com.example.networkhttpurlconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRequest.setOnClickListener {
            thread(start=true){
                try {
                    var urlText = editUrl.text.toString()
                    if (!urlText.startsWith("https")) {
                        urlText = "https//${urlText}"
                    }
                    val url = URL(urlText)

                    /*
                    URL객체에서 openConnection()메서드를 사용해서 서버와의 연결 생성
                    HttpURLConnection으로 형변환. openConnection()에서 반환되는 값은 URLConnection이라는 추상(설계) 클래스
                    추상클래스를 사용하기 위해서는 실제 구현 클래스인 HttpURLConnection으로 변환하는 과정 필요
                     */
                    val urlConnection = url.openConnection() as HttpURLConnection

                    //연결된 커넥션에 요청 방식 설정
                    urlConnection.requestMethod = "GET"

                    //응답이 정상이면 응답 데이터 처리
                    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                        /*

                         */
                        val streamReader = InputStreamReader(urlConnection.inputStream)
                        val buffered = BufferedReader(streamReader)

                        val content = StringBuilder()

                        //반복문을 돌면서 한 줄씩 읽은 데이터를 content 변수에 저장
                        while (true) {
                            val line = buffered.readLine() ?: break
                            content.append(line)
                        }

                        //사용한 스트림과 커넥션을 모두 해제
                        buffered.close()
                        urlConnection.disconnect()

                        runOnUiThread {
                            textContent.text = content.toString()
                        }
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}