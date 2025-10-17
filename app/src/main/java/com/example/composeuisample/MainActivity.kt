package com.example.composeuisample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.composeuisample.ui.theme.ComposeUiSampleTheme

/**
 * 메인 액티비티
 *
 * note: 프로젝트 생성전 jdk 설치할 것, 그렇지 않을시 특정 자바 클래스를 찾지 못하는 오류가 발생할 수 있음.
 *
 * note: 컴포즈 설명
 *  - 선언형 UI 도구 키트
 *      - 기존 뷰 계층 구조: UI 위젯의 트리
 *      - 컴포즈: 선언형 UI 프레임워크
 *
 * 참조 링크: https://developer.android.com/develop/ui/compose/mental-model?hl=ko
 *
 * note: 컴포즈 핵심개념
 *  - 데이터가 들어옴 -> UI에 반영함
 *  - UI에서 이벤트가 발생함 -> 이벤트를 데이터로 올림 (중요)
 *  - 데이터 상태가 변경됨
 *  - 변경된 데이터가 다시 들어옴 -> UI에 다시 반영함
 *
 * 참조 영상: https://youtu.be/y4Zuyr2GSO4?si=cqZJ9443cRDnebkr
 */
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // setContent 내부 블록부터 컴포즈
            ComposeUiSampleTheme {
                // remember로 데이터를 가지고 있는 clickCount 변수
                // 해당 clickCount 변수는 Greeting 함수의 clickCount 매개변수로 사용
                val clickCount: MutableState<Int> = remember { mutableIntStateOf(0) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting 함수의 onClicked 이벤트가 발생하면
                    // clickCount 데이터 상태가 변경
                    // 변경된 데이터가 다시 들어와 UI에 다시 반영됨
                    Greeting(name = "snowflo", clickCount = clickCount.value, onClicked = {
                        Log.d(TAG, "onCreate: 클릭됨")
                        clickCount.value = clickCount.value + 1
                    })
                }
            }
        }
    }

    /**
     * note: 컴포즈 함수
     * - 데이터를 받아서 UI 요소를 내보내는 구성 가능한 함수 집합을 정의하여 사용자 인터페이스를 빌드
     * - @Composable 어노테이션: 모든 컴포즈 함수에는 이 어노테이션이 필요하며, 이 함수가 데이터를 UI로 변환하기 위한 함수라는 것을 컴포즈 컴파일러에 알림
     *
     * @param name
     * @param clickCount
     * @param onClicked
     */
    @Composable
    fun Greeting(name: String, clickCount: Int, onClicked: () -> Unit) {
        // 세로 방향으로 요소를 나열하기 위한 컨테이너: 컬럼
        Column {
            Text("Hello $name")
            Text("Click count : $clickCount")
            Button(onClicked) {
                Text("Click me")
            }
        }
    }
}