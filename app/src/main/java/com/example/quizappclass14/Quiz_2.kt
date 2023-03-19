package com.example.quizappclass14

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.quizappclass14.databinding.ActivityMainBinding
import com.example.quizappclass14.databinding.ActivityQuiz2Binding
import java.util.concurrent.TimeUnit

class Quiz_2 : AppCompatActivity() {

    lateinit var binding: ActivityQuiz2Binding

    var quizList = listOf<Set_Quiz>(

        Set_Quiz("Who is the ODI caption of Bangladesh Team","Sakib","Tamim","Mushfiq","Mashrafi","Tamim"),
        Set_Quiz("Who is the T20 caption of Bangladesh Team","Sakib","Tamim","Mushfiq","Mashrafi","Sakib"),
        Set_Quiz("Who is the Test caption of Bangladesh Team","Mushfiq","Tamim","Sakib","Mashrafi","Sakib"),
        Set_Quiz("Victory day of bangladesh", "16 dec", "26 mar", "21 feb", "14 feb", "16 dec"),
        Set_Quiz("Independence day of bangladesh", "16 dec", "26 mar", "21 feb", "14 feb", "26 mar"),

    )

    var hasFinished = false

    var updateQuestionNo = 1

    var countDownTimer: CountDownTimer? = null

    var milisLeftSecond = 30000L

    var qIndex = 0

    var skip = -1
    var correct = 0
    var wrong = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuiz2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initQuestion()

        binding.countTV.text = "$updateQuestionNo/${quizList.size}"

        startCountDownTimer()

        binding.nextBTN.setOnClickListener {
            showNextQuestion()
        }

    }

    private fun initQuestion() {
        val quiz = quizList[qIndex]

        binding.apply {
            questionTV.text = quiz.Question
            option1.text = quiz.Option1
            option2.text = quiz.Option2
            option3.text = quiz.Option3
            option4.text = quiz.Option4
        }
    }

    private fun showNextQuestion(){
        checkAnswer()
        binding.apply {

            if (updateQuestionNo<quizList.size){
                updateQuestionNo++
                countTV.text = "$updateQuestionNo/${quizList.size}"
            }

            if (qIndex<=quizList.size - 1){
                initQuestion()
            }
            else{
                hasFinished = true
            }

            quizGroup.clearCheck()

        }


    }

    private fun checkAnswer() {
         binding
             .apply {
                 if (quizGroup.checkedRadioButtonId == -1){
                     skip++

                 }
                 else{
                     val checkButton = findViewById<RadioButton>(quizGroup.checkedRadioButtonId)

                     var checkAnswer = checkButton.text.toString()

                     if (checkAnswer == quizList[qIndex].RightAnswer){
                         correct++

                         scoreTV.text = "Score : $correct"

                         countDownTimer?.cancel()
                         showAlertDialog("Right Answer")

                     }else{
                         wrong++
                         countDownTimer?.cancel()
                         showAlertDialog("Wrong Answer")
                     }

                 }

                 if (qIndex<= quizList.size -1){
                     qIndex++
                 }
                 else{
                     showAlertDialog("Finished")
                 }






             }
    }

    fun startCountDownTimer(){
        countDownTimer = object :CountDownTimer(milisLeftSecond,1000){
            override fun onTick(millisUntilFinished: Long) {
                milisLeftSecond = millisUntilFinished

                val second = TimeUnit.MILLISECONDS.toSeconds(milisLeftSecond).toInt()

                binding.timerTextView.text = "Time Left : $second"

            }

            override fun onFinish() {

                showNextQuestion()

            }

        }.start()
    }

    fun showAlertDialog(message : String){

        val builder = AlertDialog.Builder(this)
        builder.setTitle(message)

        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                if (message=="Finished"){
                    countDownTimer?.cancel()

                    var intent = Intent(this@Quiz_2,ResultActivity::class.java)

                    intent.putExtra("skip",skip)
                    intent.putExtra("right",correct)
                    intent.putExtra("wrong",wrong)
                    intent.putExtra("totalQuestion",quizList.size)


                    startActivity(intent)
                }

                countDownTimer?.start()
            }

        })

        var alertDialog = builder.create()
        alertDialog.show()

    }


}