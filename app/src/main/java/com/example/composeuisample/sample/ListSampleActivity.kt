package com.example.composeuisample.sample

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.composeuisample.R
import com.example.composeuisample.model.Sample
import com.example.composeuisample.model.SampleProvider
import com.example.composeuisample.ui.theme.ComposeUiSampleTheme
import com.example.composeuisample.ui.theme.Pink40
import com.example.composeuisample.ui.theme.Purple40
import com.example.composeuisample.ui.theme.White

/**
 * 리스트 샘플 액티비티
 */
class ListSampleActivity : ComponentActivity() {

    companion object {
        const val TAG = "ListSampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeUiSampleTheme {
                ContentView()
            }
        }
    }
}


@Composable
fun ContentView() {
    Surface(
        modifier = Modifier.fillMaxSize(),              // match parent
        color = MaterialTheme.colorScheme.background,   // color
    ) {
        Scaffold(
            modifier = Modifier.background(color = White),
            topBar = { MyTopBar() }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                SampleListView(sampleList = SampleProvider.sampleList)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = { MyTopBarTitle() },                // title
        colors = TopAppBarDefaults.topAppBarColors( // colors
            containerColor = Purple40,
            titleContentColor = White
        )
    )
}

@Composable
fun MyTopBarTitle() {
    Text(
        text = stringResource(id = R.string.app_name),
        modifier = Modifier.padding(all = 8.dp),    // dp: fixed size
        fontSize = 18.sp,                           // sp: system size
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun SampleListView(sampleList: List<Sample>) {
    // 메모리 관리가 들어간 LazyColumn, lazyRow (리사이클러뷰)
    LazyColumn() {
        items(items = sampleList) { SampleView(it) }
    }
}

@Composable
fun SampleView(sample: Sample) {
    Card(   // 카드뷰
        modifier = Modifier
            .padding(all = 10.dp)   // 카드뷰 외곽 패딩
            .fillMaxWidth(),        // match parent
        elevation = CardDefaults.cardElevation(10.dp),  // 공중에 뜸 효과
        shape = RoundedCornerShape(size = 12.dp),   // 라운드
        colors = CardDefaults.cardColors(
            containerColor = getSampleBackground(), // 배경색
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,  // Row기 때문에 CenterVertically 가능
            horizontalArrangement = Arrangement.spacedBy(10.dp) // 아이템끼리 공간 주기
        ) {
            ProfileImage(imgUrl = sample.profileImg)
            Column() {
                Text(
                    text = sample.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = sample.description,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ProfileImage(imgUrl: String, modifier: Modifier = Modifier) {
    // 이미지 비트맵 저장
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val imageModifier = modifier
        .size(width = 50.dp, height = 50.dp)
        .clip(RoundedCornerShape(10.dp))

    // LocalContext: 현재 컨텍스트를 가져옴
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // 이미지를 비트맵으로 받아 준비 완료
                // Glide 라이브러리를 통해 다운받은 비트맵
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })

    // 비트맵이 있다면
    bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
        Image(
            bitmap = fetchedBitmap,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = imageModifier
        )   // 비트맵으로 이미지 그리기
    } ?: Image(
        painter = painterResource(id = R.drawable.ic_empty_user_img),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )   // 페인터로 이미지 그리기
}

fun getSampleBackground(): Color {
    return if (true) {   // TODO: 2025-10-27 색상 선택 조건문
        Pink40
    } else {
        Purple40
    }
}