package com.example.widgetsradio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioApple -> Log.d("RadioButton", "An apple is selected")
                R.id.radioBanana -> Log.d("RadioButton", "A banana is selected")
                R.id.radioOrange -> Log.d("RadioButton", "An orange is selected")
            }
        }
    }
}