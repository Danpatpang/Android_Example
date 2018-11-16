package io.github.danpatpang.buttonevent

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*;
import kotlinx.android.synthetic.main.check_list_row.*;

class ItemListAdapter(var activity:Activity, var items: ArrayList<CheckingItem>): BaseAdapter() {

    // 각 데이터별 뷰
    private class ViewHolder(row : View?) {
        var item : ImageView ?= null;
        var count : TextView ?= null;
        var increaseCountButton : ImageButton? = null;
        var decreaseCountButton : ImageButton? = null;

        init {
            this.item = row?.findViewById(R.id.item);
            this.count = row?.findViewById(R.id.txtCount);
            this.increaseCountButton = row?.findViewById(R.id.increaseCountButton);
            this.decreaseCountButton = row?.findViewById(R.id.decreaseCountButton);
        }
    }

    // ListView의 재활용 View인 Convertview는 Adapter의 getView( )를 통해서 관리된다.
    // ViewHolder pattern
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?;
        val viewHolder: ViewHolder;
        // 뷰와 연결 작업
        if(convertView == null){
            // inflater 얻어오기 (xml의 view를 실제 객체로 만드는 과정)
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
            view = inflater.inflate(R.layout.check_list_row, null);
            viewHolder = ViewHolder(view);
            // 뷰에 태그를 달아준다.
            view?.tag = viewHolder;
        } else {
            view = convertView;
            viewHolder = view.tag as ViewHolder;
        }

        // 뷰 조작
        val item = items[position];
        viewHolder.count?.text = "${item.itemCount}";
        // 이미지 설정.
        val resName = "@drawable/${item.itemImage}";
        val resId = activity.resources.getIdentifier(resName, "drawable", activity.packageName);
        viewHolder.item?.setImageResource(resId);

        viewHolder.increaseCountButton?.setOnClickListener {
            item.increaseItemCount();
            viewHolder.count?.text = "${item.itemCount}";
        }
        viewHolder.decreaseCountButton?.setOnClickListener {
            item.decreaseItemCount();
            viewHolder.count?.text = "${item.itemCount}";
        }

        return view as View;
    }

    // 해당 position에 위치한 item
    override fun getItem(position: Int): CheckingItem {
        return items[position];
    }

    // item을 나타내는 고유 정보
    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    // 리스트의 갯수
    override fun getCount(): Int {
        return items.size;
    }

}