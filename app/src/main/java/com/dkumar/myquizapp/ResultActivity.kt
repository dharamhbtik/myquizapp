package com.dkumar.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        init()
    }
    private fun init(){
        val userName = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = userName
        val correctAnswer = intent.getIntExtra(Constants.CORRECT_QUESTIONS,0).toString()
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0).toString()
        tv_Score.text ="You Score is $correctAnswer out of $totalQuestions"
        btnRestart.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
                intent.putExtra(Constants.USER_NAME,userName)
            startActivity(intent)
            finish()
        }
    }
}