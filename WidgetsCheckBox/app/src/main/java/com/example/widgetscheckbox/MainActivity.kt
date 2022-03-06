package com.example.widgetscheckbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var listener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            when(buttonView.id){
                R.id.checkApple -> Log.d("CheckBox", "An apple was selected")
                R.id.checkBanana -> Log.d("CheckBox", "A banana was selected")
                R.id.checkOrange -> Log.d("CheckBox", "An orange was selected")
            }
        } else {
            when(buttonView.id){
                R.id.checkApple -> Log.d("CheckBox", "An apple was delected")
                R.id.checkBanana -> Log.d("CheckBox", "A banana was delected")
                R.id.checkOrange -> Log.d("CheckBox", "An orange was delected")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkApple.setOnCheckedChangeListener(listener)
        checkBanana.setOnCheckedChangeListener(listener)
        checkOrange.setOnCheckedChangeListener(listener)

//        checkApple.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked) Log.d("CheckBox", "An apple was selected")
//            else Log.d("CheckBox", "An apple was deselected")
//        }
    }
}