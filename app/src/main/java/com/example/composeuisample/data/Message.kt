package com.example.composeuisample.data

/**
 * note: 주석을 이 블록에 해야 다른 곳에서 사용할 때 내부 id, msg 등이 표시됨
 *
 * 메세지 데이터 클래스
 *
 * @property id 아이디
 * @property content 콘텐츠
 */
data class Message(
    val id: Int,
    var content: String,
)
