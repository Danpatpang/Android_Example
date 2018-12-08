package io.github.danpatpang.bottomnavigation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    private lateinit var pageAdapter: PageAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageAdapter = PageAdapter(supportFragmentManager, 3);
        viewpager.adapter = pageAdapter;
    }
}
