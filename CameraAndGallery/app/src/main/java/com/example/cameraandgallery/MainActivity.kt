package com.example.cameraandgallery

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    // 1. 권한 정의
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE
        , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // 2. 권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        카메라에서 찍은 사진을 외부 저장소(포토갤러리)에 저장할 것. onCreate()에서 저장소 권한 체크
        권한 승인 시, 화면을 설정하는 setView() 호출
         */
        if(checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)){
            setViews()
        }
    }

    /*
    setViews()에 버튼 클릭 시 카메라를 호출하는 코드 추가
    버튼을 클릭하면 실제 카메라를 호출하는 코드가 있는 openCamera() 호출
     */
    fun setViews(){
        buttonCamera.setOnClickListener {
            openCamera()
        }
        buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    /*
    setViews()와 분리함으로써, 카메라 권한이 미승인 되었을 때, 먼저 권한 처리한 후에 카메라 요청 로직을 다시 호출할 수 있음
    Intent에 MediaStore.ACTION_IMAGE_CAPTURE를 담아 전달하면 카메라 앱이 호출
     */
    fun openCamera(){
        if(checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, FLAG_REQ_CAMERA)
        }
    }

    /*
    intent의 파라미터로 ACTION_PICK을 사용하면
    intent.type에서 설정한 종류의 데이터를 MediaStore에서 불러옴
    목록으로 나열
     */
    fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    /*
    카메라 정상 호출, 사용자 사진 촬영 완료 -> onActivityResult() 메서드로 결과값 전달
    촬영한 사진 정보는 세 번째 파라미터 data에 인텐트로 전달
    전달받은 data 파라미터에서 사진을 꺼낸 후, 이미지뷰에 세팅

    Intent로 전달되는 data 파라미터에서 사진 꺼내기
        data 파라미터를 통해 전달되는 사진은 data.extras.get("data")로 꺼낼 수 있음
        꺼낸 값이 먼저 null인지 확인 -> Bitmap으로 형변환
        data.extras.get으로 꺼낸 값이 Object이므로 변환한 후에 이미지뷰에 세팅
        data 또는 extras가 null일 경우 대비해서 ?(물음표) 추가하여 null 안정성 체크
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data") != null){
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val uri = saveImageFile(newFileName(), "image/jpg", bitmap)
                        imagePreview.setImageURI(uri)
                        /*
                        onActivityResult() 메서드로 전달된 비트맵을 saveImageFile() 함수에서 전처리
                        반환된 Uri를 이미지뷰에 세팅하도록 코드 수정
                         */
                    }
                }
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data
                    imagePreview.setImageURI(uri)
                    /*
                    갤러리 버튼을 통해 전달된 이미지 데이터를 imagePreview에 세팅
                    호출된 갤러리에서 이미지를 선택하면 data의 data속성으로 해당이미지의 Uri가 전달
                    전달된 Uri를 이미지뷰에 세팅 가능
                     */
                }
            }
        }
    }

    //촬영된 이미지를 외부 저장소에 저장하는 메서드
    fun saveImageFile(filename : String, mimeType : String, bitmap : Bitmap) : Uri? {
        //넘겨받은 값으로 MediaStore에 저장할 파일의 이름과 마임타입 결정
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        //안드 버전 Q이상 -> 다른 곳에서 내가 사용하는 데이터를 요청할 경우 무시하는 옵션 추가
        //IS_PENDING이 1일 때 동작 , 처리 완료시 0으로 돌려놓음
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        //MediaStore에 파일 등록, 등록된 Uri를 item 변수에 저장
        var uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {
            //파일디스크립터를 열어 descriptor 변수에 저장. 파일디스크립터로 파일 읽거나 쓰기 가능

            if (uri != null) {
                var descriptor = contentResolver.openFileDescriptor(uri, "w")

                //파일디스크립터 사용 가능시, FileOutputStream으로 비트맵 파일 저장
                //숫자 100은 압축률, 숫자가 작을수록 화질이 낮아지면서 파일 크기도 작아짐
                if (descriptor != null) {
                    val fos = FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()

                    //저장 완료시, IS_PEDING을 0으로, MediaStore 업데이트
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(uri, values, null, null)
                    }
                }
            }
        } catch (e:java.lang.Exception){
            Log.e("File", "error-${e.localizedMessage}")
        }
        return uri
    }

    @RequiresApi(Build.VERSION_CODES.N)
    /*
    사진의 중복되지 않는 파일명 -> 현재시각 이용해서 파일명 생성
     */
    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())

        return "$filename.jpg"
    }


    /*
    여기서부터 권한 처리 관련 메서드
     */

    // 3. 권한 체크 메서드
    fun checkPermission(permissions : Array<out String>, flag : Int) : Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(permission in permissions){
                if(ContextCompat.checkSelfPermission(this, permission) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, permissions, flag)
                    return false
                }
            }
        }
        return true
    }

    /*
    onCreate() 메서드에서 checkPermission이 실패 -> 권한 요청 팝업 실행
    onRequestPermissionResult로 결과값 전달, 따라서 FLAG_PERM_STORAGE의 for문 다음에서
    setViews() 함수를 한 번 호출
    승인이 안 되었으면 setViews() 함수가 실행되지 않아야 함. finish() 다음 줄에 return 추가

    openCamera()에서 checkPermission이 실패할 경우 onRequestPermissionResult의 FLAG_PERM_CAMERA에서
    실행되는 for문 다음에 openCamera()를 다시 호출
    미승인시 실행되지 않도록 Toast 다음 줄에 return
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            FLAG_PERM_STORAGE -> {  // 4. 저장소 권한 후처리
                for (grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                        finish()    // 5. 저장소 권한 미 승인 시 앱 종료
                        return
                    }
                }
                setViews()
            }
            FLAG_PERM_CAMERA -> {   // 6. 카메라 권한 후처리
                for (grant in grantResults) {
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                        return
                    }
                }
                openCamera()
            }
        }
    }
}