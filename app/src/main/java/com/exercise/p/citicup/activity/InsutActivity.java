package com.exercise.p.citicup.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.CheckableLinearLayout;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.ViewPagerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class InsutActivity extends AppCompatActivity {

    int[] bg = {R.drawable.insut_1, R.drawable.insut_2, R.drawable.insut_3, R.drawable.insut_4, R.drawable.insut_5, R.drawable.insut_6, R.drawable.insut_7};
    String[] topic;
    ArrayList<ArrayList<String>> data;
    ArrayList<ArrayList<Integer>> choose;
    LinearLayout page1;
    LinearLayout page2;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insut);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.insut_toolbar);
        //设置toolbar属性
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsutActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        page1 = (LinearLayout) findViewById(R.id.page1);
        page2 = (LinearLayout) findViewById(R.id.page2);
        pager = (ViewPager) findViewById(R.id.viewpager);
        getData();
        final ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < topic.length; i++) {
            View view1;
            if (i == topic.length - 1) {
                view1 = View.inflate(InsutActivity.this, R.layout.insu_test_end, null);
                Button button1 = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
            } else if (i == 0) {
                view1 = View.inflate(InsutActivity.this, R.layout.insu_test_fir, null);
                Button button = (Button) view1.findViewById(R.id.next);
                final int temp = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp + 1, true);
                    }
                });
            } else {
                view1 = View.inflate(InsutActivity.this, R.layout.insu_test_mid, null);
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
                    for (int ii = 0; ii < choose.size(); ii++) {
                        ArrayList<Integer> temp = choose.get(ii);
                        if (temp.size() == 0) {
                            pager.setCurrentItem(ii);
                            Toast.makeText(InsutActivity.this, "您这道题还没选择哦~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SharedPreferences preferences = getSharedPreferences("insut", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("choose", new Gson().toJson(choose));
                    editor.apply();
                }
            });
            final ListView listView = (ListView) view1.findViewById(R.id.items);
            setAdapter(listView, data.get(i), i);
            TextView textView = (TextView) view1.findViewById(R.id.topic);
            textView.setText(topic[i]);
            ImageView imageView = (ImageView) view1.findViewById(R.id.pic);

            imageView.setImageResource(bg[i]);

            views.add(view1);
        }
        pager.setAdapter(new ViewPagerAdapter(views));
    }

    private void getData() {
        data = new ArrayList<>();

        topic = getResources().getStringArray(R.array.insu_topic);
        for (int i = 1; i < topic.length + 1; i++) {
            ArrayList<String> temp = new ArrayList<>();
            int a = getResources().getIdentifier("an" + i, "array", getApplicationContext().getPackageName());
            String[] ss = getResources().getStringArray(a);
            Collections.addAll(temp, ss);
            data.add(temp);
        }

        choose = new ArrayList<>();
        for (String aTopic : topic) {
            choose.add(new ArrayList<Integer>());
        }
    }

    private void setAdapter(final ListView listView, final ArrayList<String> ss, final int pos) {
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
                    convertView = (getLayoutInflater()).inflate(R.layout.insut_list_item, null);
                }
                final CheckableLinearLayout layout = (CheckableLinearLayout) convertView;
                TextView textView = (TextView) layout.findViewById(R.id.node_title);
                final CheckBox box = (CheckBox) layout.findViewById(R.id.node_icon);
                layout.setBox(box);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            choose.get(pos).clear();
                            choose.get(pos).add(position);
                            listView.setItemChecked(position, true);
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

}
