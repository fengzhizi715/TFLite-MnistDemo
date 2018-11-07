package com.tflite.demo.ui

import android.graphics.Path

/**
 *
 * @FileName:
 *          com.tflite.demo.ui.FingerPath
 * @author: Tony Shen
 * @date: 2018-11-07 16:29
 * @version: V1.0 <描述当前版本功能>
 */
data class FingerPath(var color: Int, var emboss: Boolean, var blur: Boolean, var strokeWidth: Int, var path: Path)