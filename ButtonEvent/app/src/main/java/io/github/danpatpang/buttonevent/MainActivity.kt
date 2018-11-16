package io.github.danpatpang.buttonevent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 어댑터에 추가될 아이템.
        val itemList = arrayListOf<CheckingItem>(
            CheckingItem("1", 0, "drinking"),
            CheckingItem("2", 0, "toilet"));

        // 리스트뷰 전용 어댑터 생성 및 연결.
        // ListView는 화면에 새로운 아이템을 표시하라 때마다 Adapter의 getView( )를 호출하게 된다.
        val adapter = ItemListAdapter(this, itemList);
        checkList.adapter = adapter;
    }
}
