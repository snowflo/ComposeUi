package com.example.composeuisample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeuisample.data.Message
import com.example.composeuisample.ui.theme.ComposeUiSampleTheme

/**
 * note: 프로젝트 생성전 jdk 설치할 것, 그렇지 않을시 특정 자바 클래스를 찾지 못하는 오류가 발생할 수 있음.
 *
 * note: 안드로이드 스튜디오 자동 개행 변경 방법
 *  - Settings -> Editor -> Code Style -> Hard wrap at (Maximum line length) 필드 변경
 */

/**
 * 메인 액티비티
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
                // remember로 데이터를 가지고 있는 messageList 변수
                // 해당 messageList 변수는 MessageList 함수의 messages 매개변수로 사용
                val messageList: SnapshotStateList<Message> = remember { mutableStateListOf<Message>() }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()  // 전체 화면
                        .padding(0.dp, 32.dp),  // 가로 패딩 0dp, 세로 패딩 32dp
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        // Greeting 함수의 onClicked 이벤트가 발생하면
                        // clickCount 데이터 상태가 변경
                        // 변경된 데이터가 다시 들어와 UI에 다시 반영됨
                        Greeting(name = "snowflo", clickCount = clickCount.value, onClicked = {
                            Log.d(TAG, "클릭됨")
                            clickCount.value = clickCount.value + 1

                            // Greeting의 Button 클릭시 새로운 메시지 생성
                            val newMsg = Message(id = clickCount.value, content = "메시지 입니다. ${clickCount.value}")
                            messageList.add(element = newMsg)
                        })

                        MessageList(messages = messageList, onDeleteClicked = {
                            Log.d(TAG, "삭제됨: ${it.id}")

                            // MessageRow의 Button 클릭시 해당 메시지 삭제
                            messageList.remove(element = it)
                        })
                    }// Column
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
            Text(text = "Hello $name")
            Text(text = "Click count : $clickCount")
            Button(onClick = onClicked) {
                Text(text = "Click me")
            }
        }
    }

    /**
     * 메시지 컬렉션을 표시하는 컴포즈 함수
     * - 스크롤이 필요하지 않은 경우 (방향에 따라) 간단한 Column 또는 Row를 사용
     * - 목록을 반복하여 각 항목의 콘텐츠를 내보냄
     *
     * note: 많은 수의 항목이나 길이를 알 수 없는 목록을 표시해야 하는 경우
     *       구성요소의 표시 영역에 표시되는 항목만 구성하여 배치하는 구성요소 집합을 사용
     *       (LazyColumn, LazyRow 포함)
     *
     * MessageList 함수는 onCreate로 이벤트 전달
     * MessageList를 Surface 함수에서 사용하기 위해 전달
     *
     * @param messages
     * @param onDeleteClicked
     */
    @Composable
    fun MessageList(messages: List<Message>, onDeleteClicked: (Message) -> Unit) {
        /**
         * import androidx.compose.foundation.lazy.items
         */
        LazyColumn {
            items(items = messages) { message ->
                MessageRow(message, onDeleteClicked)
            }
        }
    }

    /**
     * 메시지 아이템
     * MessageRow 함수는 MessageList로 이벤트를 전달
     *
     * @param msg
     * @param onDeleteClicked
     */
    @Composable
    fun MessageRow(msg: Message, onDeleteClicked: (Message) -> Unit) {

        Surface(
            // 크기, 패딩 등 설정
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            // 모양 설정
            shape = RoundedCornerShape(8.dp),
            // 가장자리 선 설정
            border = BorderStroke(1.dp, Color.LightGray),
            // 공중에 뜬 효과
            shadowElevation = 10.dp
        ) {
            // Surface 내부 블록에 Column 설정
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "id: ${msg.id} / content: ${msg.content}")
                // msg를 매개변수로 받아 반환값이 없는 람다 전달
                // onClick은 () -> Unit 형태를 가지기 때문에 "{}"로 래핑해 전달
                Button(onClick = { onDeleteClicked(msg) }) {
                    Text("Delete me")
                }
            }//Column
        }
    }
}