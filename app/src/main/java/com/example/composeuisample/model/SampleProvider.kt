package com.example.composeuisample.model

data class Sample(
    val name: String = "Sherri Obrien",
    val description: String = "오늘 하루도 힘내자오.",
    // random user .me
    // link = https://randomuser.me/
    // https://randomuser.me/api/portraits/women/46.jpg
    val profileImg: String = "https://randomuser.me/api/portraits/women/46.jpg"
)

object SampleProvider {
    val sampleList = List<Sample>(size = 200) {
        Sample()
    }
}
