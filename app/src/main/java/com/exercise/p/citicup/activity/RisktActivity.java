package com.exercise.p.citicup.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.exercise.p.citicup.CheckableLinearLayout;
import com.exercise.p.citicup.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RisktActivity extends AppCompatActivity {

    ViewPager pager;
    String[] topic;
    ArrayList<ArrayList<String>> data;
    ArrayList<ArrayList<Integer>> choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riskt);
        getData();
        initToolBar();
        initView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.riskt_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RisktActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.viewpager);
        final ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < topic.length; i++) {
            View view1;
            if (i == topic.length - 1) {
                view1 = View.inflate(RisktActivity.this, R.layout.test3_end, null);
                Button button1 = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
            }
            else if (i == 0) {
                view1 = View.inflate(RisktActivity.this, R.layout.test3_fir, null);
                Button button = (Button) view1.findViewById(R.id.next);
                final int temp = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp + 1, true);
                    }
                });
            }
            else {
                view1 = View.inflate(RisktActivity.this, R.layout.test3_mid, null);
                Button button1 = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
                Button button2 = (Button) view1.findViewById(R.id.next);
                final int temp2 = i;
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp2 + 1, true);
                    }
                });
            }
            Button submit = (Button) view1.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Test", "submit choose" + choose);
                }
            });
            final ListView listView = (ListView) view1.findViewById(R.id.items);
            if (i != 8 && i != 11) {
                setAdapter(listView, data.get(i), i, true);
            } else {
                setAdapter(listView, data.get(i), i, false);
            }
            TextView textView = (TextView) view1.findViewById(R.id.topic);
            textView.setText(i + 1 + "、" + topic[i]);
            views.add(view1);
        }
        pager.setAdapter(new MyViewAdapter(views));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Test", "2position" + position);
                View temp = views.get(position);
                ListView listView = (ListView) temp.findViewById(R.id.items);
                Log.i("Test", "temp :" + choose.get(position));
                for (int i = 0; i < choose.get(position).size(); i++) {
                    Log.i("Test", "i:" + i);
                    Log.i("Test", "c:" + choose.get(position).get(i));
                    listView.setItemChecked(choose.get(position).get(i), true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getData() {
        data = new ArrayList<>();
        topic = getResources().getStringArray(R.array.topic);
        for (int i = 1; i < topic.length + 1; i++) {
            ArrayList<String> temp = new ArrayList<>();
            int a = getResources().getIdentifier("a" + i, "array", getApplicationContext().getPackageName());
            String[] ss = getResources().getStringArray(a);
            Collections.addAll(temp, ss);
            data.add(temp);
        }
        choose = new ArrayList<>();
        for (int i = 0; i < topic.length; i++) {
            choose.add(new ArrayList<Integer>());
        }
        Log.i("Test", "Data: " + data);
    }

    private void setAdapter(final ListView listView, final ArrayList<String> ss, final int pos, final boolean isSingle) {
        if (isSingle)
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        else
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ss.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = (getLayoutInflater()).inflate(R.layout.test_list_item, null);
                }
                final CheckableLinearLayout layout = (CheckableLinearLayout) convertView;
                TextView textView = (TextView) layout.findViewById(R.id.node_title);
                final CheckBox box = (CheckBox) layout.findViewById(R.id.node_icon);
                layout.setBox(box);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (isSingle) {
                                choose.get(pos).clear();
                                choose.get(pos).add(position);
                                listView.setItemChecked(position, true);
                            } else {
                                if (layout.isChecked()) {
                                    choose.get(pos).remove(position);
                                } else {
                                    choose.get(pos).add(position);
                                }
                                listView.setItemChecked(position, !layout.isChecked());
                            }
                            Log.i("Test", "choose" + choose.get(pos));
                            Log.i("Test", "choose" + choose);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                textView.setText(ss.get(position));
                return layout;
            }
        });
    }

    private class MyViewAdapter extends PagerAdapter {
        private List<View> viewLists;

        public MyViewAdapter(List<View> lists) {
            viewLists = lists;
        }

        //获得size
        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //销毁Item
        @Override
        public void destroyItem(View view, int position, Object object) {
            Log.i("Test", "Destroy   :" + position);
            ((ViewPager) view).removeView(viewLists.get(position));
        }

        //实例化Item
        @Override
        public Object instantiateItem(View view, int position) {
            Log.i("Test", "Instantiate   :" + position);
            ((ViewPager) view).addView(viewLists.get(position), 0);
            View view1 = viewLists.get(position);
            return view1;
        }
    }
}
