package com.example.composeuisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

/**
 * 메인 액티비티
 *
 * note: 프로젝트 생성전 jdk 설치할 것, 그렇지 않을시 특정 java class를 찾지 못하는 오류가 발생할 수 있음.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello world")
        }
    }
}