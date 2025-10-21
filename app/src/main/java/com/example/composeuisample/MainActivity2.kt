package com.example.composeuisample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeuisample.MainActivity2.Companion.TAG
import com.example.composeuisample.ui.theme.ComposeUiSampleTheme

/**
 * 컴포즈 복습을 위한 액티비티 생성
 * - 패키지 우클릭 -> New -> Compose -> Empty Activity
 * - 컴포저블 단위: flutter, swiftUI와 매우 유사
 * - 스택: Column, Row, 곂치기: Box
 *
 * TODO: 2025-10-19 참조 -> https://youtu.be/1apENzDbtCQ?si=foyPFUyU791KvShc
 */
class MainActivity2 : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // setContent 기존 xml과 코드 연결
            ComposeUiSampleTheme {
                // Scaffold: Material 디자인 제공
                Scaffold(
                    topBar = {
                        MyTopBar()
                    },
                    bottomBar = {
                        MyBottomBar()
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        MyFloatingActionButton()
                    }
                ) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        MyComposableView()
                    }
                }
            }
        }//setContent
    }
}

/**
 * 상단 앱바
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        // TopAppBarColors를 직접 생성하는 것은 복잡하고 오류 발생 가능성이 높아
        // TopAppBarDefaults.topAppBarColors 헬퍼 함수를 통해 필요한 색상만 지정
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text("MyTopBar")
        }
    )
}

/**
 * 하단 탭바
 */
@Composable
fun MyBottomBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        // TODO: 2025-10-21 컨텐츠 추가
    }
}

/**
 * 플로팅 액션 버튼
 */
@Composable
fun MyFloatingActionButton() {
    FloatingActionButton(
        onClick = {
            Log.d(TAG, "플로팅 액션 버튼 클릭")
        },
        containerColor = Color(0xFFBB86FC)
    ) {
        Text("플로팅 액션 버튼")
    }
}

/**
 * 뷰
 * 매개변수를 받아 뷰를 생성
 *
 * @param name
 * @param modifier
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * 커스텀뷰
 */
@Composable
fun MyComposableView() {
    Log.d(TAG, "MyComposableView")
    // horizontal linear
    Row(
        Modifier.padding(10.dp) // 패딩
    ) {
        Text("행1", Modifier.background(Color.Red))
        Spacer(modifier = Modifier.size(10.dp)) // 여백
        Text("행2", Modifier.background(Color.Green))
        Spacer(modifier = Modifier.size(10.dp))
        Text("행3", Modifier.background(Color.Blue))
    }
}

/**
 * 미리보기
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeUiSampleTheme {
        Greeting("Android")
    }
}