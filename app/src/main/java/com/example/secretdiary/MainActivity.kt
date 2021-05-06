package com.example.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if(changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            getSharedPreferences(name: 이름, Context.MODE_PRIVATE -> 여기 앱에서만 사용!)
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
//                일치하면 패스워드 성공 (true)
                startActivity(Intent(this, DiaryActivity::class.java))
//                TODO 다이어리 페이지 작성 후에 넘겨주어야 함
            }else {
//                일치하지 않을 경우 실패 (false)
//    this -> 액티비티 자신 이기 때문에 this를 쓴다.
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changePasswordMode) {
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)



            }else{
//                changePasswordMode가 활성화 :: 비밀번호가 맞는지를 체크


                    if(passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                        changePasswordMode = true
                        Toast.makeText(this, "변경할 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        changePasswordButton.setBackgroundColor(Color.RED)


                    }else {
    //                일치하지 않을 경우 실패 (false)
    //    this -> 액티비티 자신 이기 때문에 this를 쓴다.
                        showErrorAlertDialog()
                    }
                }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Failed!!")
            .setMessage("비밀번호가 잘못 되었습니다.")
            .setPositiveButton("OK") { dialog, which ->
                //                        람다식을 받게 된다.
            }.create().show()
    }

}