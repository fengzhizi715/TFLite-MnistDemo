package com.tflite.demo.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.Toast
import com.tflite.demo.DigitsDetector
import com.tflite.demo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @FileName:
 *          com.tflite.demo.activity.MainActivity
 * @author: Tony Shen
 * @date: 2018-11-07 16:20
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity : AppCompatActivity() {

    private val PIXEL_WIDTH = 28

    private lateinit var mnistClassifier: DigitsDetector

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mnistClassifier = DigitsDetector(this)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)

        button_detect.setOnClickListener{

            val digit = mnistClassifier.classify(Bitmap.createScaledBitmap(paintView.bitmap, PIXEL_WIDTH, PIXEL_WIDTH, false))

            if (digit >= 0) {

                Toast.makeText(this, "Detect the number is $digit",Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this,"Not detected",Toast.LENGTH_SHORT).show()
            }
        }

        button_clear.setOnClickListener{

            paintView.clear()
        }
    }
}
