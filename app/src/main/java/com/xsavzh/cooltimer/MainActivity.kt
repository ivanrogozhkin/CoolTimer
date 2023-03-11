package com.xsavzh.cooltimer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import com.xsavzh.cooltimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seekBar()
        startButton()
    }

    private fun seekBar() = with(binding) {
        seekBar.max = 600
        seekBar.progress = 60
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val millisUntilFinished: Long = (progress * 1000).toLong()
                updateTimer(millisUntilFinished)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // you can probably leave this empty
            }
        })
    }

    private fun startButton() = with(binding) {
        startButton.setOnClickListener {
            val timer = object : CountDownTimer(seekBar.progress.toLong() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    updateTimer(millisUntilFinished)
                }

                override fun onFinish() {
                    val mediaPayer = MediaPlayer.create(this@MainActivity, R.raw.alarm)
                    mediaPayer.start()
                }
            }
            timer.start()
        }
    }

    private fun updateTimer(millisUntilFinished: Long) = with(binding) {
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) - (minutes * 60)

        var minutesString = ""
        var secondsString = ""

        if (minutes < 10) {
            minutesString = "0$minutes"
        } else {
            minutesString += minutes
        }

        if (seconds < 10) {
            secondsString = "0$seconds"
        } else {
            secondsString += seconds
        }

        timeTextView.text = "$minutesString:$secondsString"
    }
}