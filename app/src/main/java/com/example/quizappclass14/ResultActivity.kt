package com.example.quizappclass14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizappclass14.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var skip = intent.getIntExtra("skip",0)
        var correct = intent.getIntExtra("right",0)
        var wrong = intent.getIntExtra("wrong",0)
        var totalQuestion = intent.getIntExtra("totalQuestion",0)

        binding.showResult.text = "Number Of Question : $totalQuestion\n"+
                "Skip : $skip\n"+
                "Correct : $correct\n"+
                "Wrong : $wrong\n"


    }
}