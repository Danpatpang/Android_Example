package io.github.danpatpang.bottomnavigation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {
    private lateinit var pageAdapter: PageAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab_layout.addTab(tab_layout.newTab().setText("Home"));
        tab_layout.addTab(tab_layout.newTab().setText("Analysis"));
        tab_layout.addTab(tab_layout.newTab().setText("User"));
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL;

        pageAdapter = PageAdapter(supportFragmentManager, tab_layout.tabCount);

        viewpager.adapter = pageAdapter;
        viewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(tab_layout)
        );
        tab_layout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewpager.currentItem = tab.position;
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            }
        )
    }
}
