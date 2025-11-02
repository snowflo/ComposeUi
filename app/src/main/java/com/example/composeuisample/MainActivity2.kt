package com.example.composeuisample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeuisample.MainActivity2.Companion.TAG
import com.example.composeuisample.sample.ListSampleActivity
import com.example.composeuisample.ui.theme.ComposeUiSampleTheme
import com.example.composeuisample.ui.theme.Purple40
import com.example.composeuisample.ui.theme.Purple80
import kotlin.random.Random

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
                    // innerPadding
                    // content: @Composable (PaddingValues) -> Units
                    // 메인 콘텐츠를 배치할 때 TopAppBar나 BottomAppBar에 가려지지 않도록 안전한 영역을 확보
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .background(Purple80)
                    ) {
                        Greeting(name = "Android")
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(Modifier.horizontalScroll(rememberScrollState())) {
                            ChangeActivityButton(name = "MainActivity", clickEvent = {
                                val intent = Intent(this@MainActivity2, MainActivity::class.java)
                                startActivity(intent)
                            })
                            ChangeActivityButton(name = ListSampleActivity.TAG, clickEvent = {
                                val intent = Intent(this@MainActivity2, ListSampleActivity::class.java)
                                startActivity(intent)
                            })
                        }
                        Spacer(modifier = Modifier.size(10.dp))
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
 * 액티비티 변경 버튼
 */
@Composable
fun ChangeActivityButton(name: String, clickEvent: () -> Unit) {
    Button(
        onClick = clickEvent,
        modifier = Modifier
            .background(Purple40)
            .padding(4.dp)
    ) {
        Log.d(TAG, "$name 액티비티 변경 버튼 클릭")
        Text(name)
    }
}

/**
 * 커스텀뷰
 *
 * 큰 흐름
 * Composable 안에 Composable 안에 Composable ... (중첩 가능)
 * 각 Composable은 Modifier를 통해 설정 가능
 * 데이터 핸들링: MutableState?
 */
@Composable
fun MyComposableView() {
    Log.d(TAG, "MyComposableView")
    // vertical linear
    Column(
        Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.verticalScroll(rememberScrollState())      // 스크롤
        ) {
            for (index in 0..30) {
                MyRowView()
            }
        }
    }
}

@Composable
fun MyRowView() {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    val randomColor = Color(red, green, blue)   // 랜덤색상

    // horizontal linear
    // arrangement: 요소를 어떻게 배열할지. Row, Column 같은 요소들이 들어가는
    // 컨테이너 성격의 컴포저블에서 요소들의 아이템을 정렬할 때 사용
    // 웹 개발 css에서 flex와 유사?
    // alignment

    // SpaceBetween: 공간 모두 차지
    // Start: 좌측
    // End: 우측
    // SpaceAround: 빈 공간을 남겨두기
    // Center: 요소들에 넣기
    // SpaceBetween: 사이에 공간을 밀어넣기
    // SpaceEvenly: 요소들 사이에 공간을 똑같이 하기
    Row(
        Modifier
            .padding(all = 10.dp)
            .background(Color.LightGray)                    // 패딩
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,     // 세로 중앙 정렬
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "빨강", Modifier
                .padding(all = 10.dp)
                .background(randomColor)
        )
        Spacer(modifier = Modifier.size(10.dp)) // 여백
        Text(
            "초록", Modifier
                .padding(all = 10.dp)
                .background(randomColor)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            "파랑", Modifier
                .padding(all = 10.dp)
                .background(randomColor)
        )
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