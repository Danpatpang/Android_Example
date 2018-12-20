package io.github.danpatpang.chart

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entries = arrayListOf<Entry>(Entry(1F,1F), Entry(2F,5F), Entry(3F,0F), Entry(4F,1F), Entry(5F,3F));
        val lineDataSet = LineDataSet(entries, "###");
        lineDataSet.lineWidth = 2F;
        lineDataSet.circleRadius = 6F;
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.color = Color.parseColor("#FFA1B4DC");
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        val lineData = LineData(lineDataSet);
        chart.data = lineData;
        chart.isDoubleTapToZoomEnabled = false;
        chart.animateY(2000);
    }
}
