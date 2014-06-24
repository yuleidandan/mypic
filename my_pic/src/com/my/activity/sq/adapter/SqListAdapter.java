package com.my.activity.sq.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amima.pic.R;
import com.my.activity.sq.bean.SqInfoBean;

public class SqListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<SqInfoBean> list = new ArrayList<SqInfoBean>();
	protected ListView mListView;

	public ArrayList<SqInfoBean> getList() {
		return list;
	}

	public void setList(ArrayList<SqInfoBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public ListView getmListView() {
		return mListView;
	}

	public void setmListView(ListView mListView) {
		this.mListView = mListView;
	}

	public SqListAdapter(Context context, ArrayList<SqInfoBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list != null)
			return list.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		SqInfoBean hh = list.get(position);
		H h = null;
		if (view == null) {
			h = new H();
			view = LayoutInflater.from(context).inflate(R.layout.sq_list_item,
					parent, false);

			h.mSq_name = (TextView) view
					.findViewById(R.id.textview_behind_title);

			view.setTag(h);
		} else {
			h = (H) view.getTag();
		}

		h.mSq_name.setText(hh.getName());

		return view;
	}

	class H {

		private TextView mSq_name;
	}
}
