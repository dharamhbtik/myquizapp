package com.dkumar.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{
    private var mCurrentPosition:Int =1
    private var mQuestionsList:ArrayList<Question>? =null
    private var mSelectedOptionPosition:Int=0
    private var mUserName: String? =null
    private var mCorrectAnswers:Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        init()
    }
    private fun init(){
        tv__option_one.setOnClickListener(this)
        tv__option_two.setOnClickListener(this)
        tv__option_three.setOnClickListener(this)
        tv__option_four.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList =Constants.getQuestions()
        setQuestion()
        defaultOptionsView()

    }
    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
            defaultOptionsView()
            mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }
    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        pb_progressbar.progress = mCurrentPosition
        pb_progressbar.max = mQuestionsList!!.size
        tv_progress.text = "$mCurrentPosition / ${pb_progressbar.max}"
        iv_image.setImageResource(question.image)
        tvQuestion.text = question.question
        tv__option_one.text = question.OptionOne
        tv__option_two.text = question.OptionTwo
        tv__option_three.text = question.OptionThree
        tv__option_four.text = question.OptionsFour
        if(mCurrentPosition == mQuestionsList!!.size)
        {
            btnSubmit.text = "FINISH"
        } else{
            btnSubmit.text = "SUBMIT"
        }
    }
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,tv__option_one)
        options.add(1,tv__option_two)
        options.add(2,tv__option_three)
        options.add(3,tv__option_four)
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8080"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                    this,
                R.drawable.default_option_border_bg
                    )
        }
    }
    override fun onClick(v: View?) {
        when(v?.id){
           R.id.tv__option_one->{
               selectedOptionView(tv__option_one,1)
           }
            R.id.tv__option_two->{
                selectedOptionView(tv__option_two,2)
            }
            R.id.tv__option_three->{
            selectedOptionView(tv__option_three,3)
        }
            R.id.tv__option_four->{
                selectedOptionView(tv__option_four,4)
            }

            R.id.btnSubmit-> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++
                when {
                    mCurrentPosition <= mQuestionsList!!.size -> {
                        setQuestion()
                    }
                    else->{
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME,mUserName)
                        intent.putExtra(Constants.CORRECT_QUESTIONS,mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                        startActivity(intent)
                        finish()
                    }
                }
            } else{
                    val question = mQuestionsList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer!=mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    } else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    if(mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit.text="FINISH"
                    } else{
                        btnSubmit.text  ="GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }
    private fun answerView(answer:Int, drawableView:Int){
        when(answer){
            1->{
                tv__option_one.background = ContextCompat.getDrawable(this,drawableView)
            }
            2->{
                tv__option_two.background = ContextCompat.getDrawable(this,drawableView)
            }
            3->{
                tv__option_three.background = ContextCompat.getDrawable(this,drawableView)
            }
            4->{
                tv__option_four.background = ContextCompat.getDrawable(this,drawableView)
            }
        }
    }
}