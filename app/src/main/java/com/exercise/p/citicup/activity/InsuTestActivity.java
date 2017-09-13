package com.exercise.p.citicup.activity;

import android.app.ProgressDialog;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.CheckableLinearLayout;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.ViewPagerAdapter;
import com.exercise.p.citicup.presenter.InsuTestPresenter;
import com.exercise.p.citicup.view.ShowDialogView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class InsuTestActivity extends AppCompatActivity implements ShowDialogView {

    int[] bg = {R.drawable.icon_insu_test_1, R.drawable.icon_insu_test_2, R.drawable.icon_insu_test_3, R.drawable.icon_insu_test_4, R.drawable.icon_insu_test_5, R.drawable.icon_insu_test_6, R.drawable.icon_insu_test_7};
    String[] topic;
    ArrayList<ArrayList<String>> data;
    //    ArrayList<ArrayList<String>> car;
    ArrayList<ArrayList<Integer>> choose;
    //    ArrayList<ArrayList<Integer>> choose_car;
    LinearLayout page1;
    //    LinearLayout page2;
    ViewPager pager;
    ProgressDialog dialog;
    InsuTestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_test);
        presenter = new InsuTestPresenter(this);
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
                InsuTestActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        page1 = (LinearLayout) findViewById(R.id.page1);
//        page2 = (LinearLayout) findViewById(R.id.page2);
        pager = (ViewPager) findViewById(R.id.viewpager);
        getData();
        initPage1();
//        initPage2();
    }

    private void initPage1() {
        final ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < topic.length; i++) {
            View view1;
            if (i == topic.length - 1) {
                view1 = View.inflate(InsuTestActivity.this, R.layout.layout_insu_test_end, null);
                Button before = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                before.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
            } else if (i == 0) {
                view1 = View.inflate(InsuTestActivity.this, R.layout.layout_insu_test_fir, null);
                Button next = (Button) view1.findViewById(R.id.next);
                final int temp = i;
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp + 1, true);
                    }
                });
            } else {
                view1 = View.inflate(InsuTestActivity.this, R.layout.layout_insu_test_mid, null);
                Button before = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                before.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
                Button next = (Button) view1.findViewById(R.id.next);
                final int temp2 = i;
                next.setOnClickListener(new View.OnClickListener() {
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
                    submit();
                }
            });
            //初始化listview，即选项信息
            final ListView listView = (ListView) view1.findViewById(R.id.items);
            setAdapter(listView, data.get(i), i);
            //设置标题
            TextView textView = (TextView) view1.findViewById(R.id.topic);
            textView.setText(topic[i]);
            //设置图
            ImageView imageView = (ImageView) view1.findViewById(R.id.pic);
            imageView.setImageResource(bg[i]);

            views.add(view1);
        }
        pager.setAdapter(new ViewPagerAdapter(views));
    }

//    private void initPage2() {
//        ArrayList<RadioGroup> groups = new ArrayList<>();
//        groups.add((RadioGroup) findViewById(R.id.radio_group1));
//        groups.add((RadioGroup) findViewById(R.id.radio_group2));
//        groups.add((RadioGroup) findViewById(R.id.radio_group3));
//        groups.add((RadioGroup) findViewById(R.id.radio_group4));
//        groups.add((RadioGroup) findViewById(R.id.radio_group5));
//        groups.add((RadioGroup) findViewById(R.id.radio_group6));
//        for (int i = 0; i < groups.size(); i++) {
//            for (int j = 0; j < car.get(i).size(); j++) {
//                RadioButton tempButton = new RadioButton(this);
//                tempButton.setPadding(10, 0, 0, 0);                 // 设置文字距离按钮四周的距离
//                tempButton.setText(car.get(i).get(j));
//                tempButton.setTextColor(getResources().getColor(R.color.inputTextColor));
//                tempButton.setTextSize(12);
//                tempButton.setButtonDrawable(R.drawable.selector_checkbox);
//                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                tempButton.setGravity(Gravity.CENTER_VERTICAL);
//                params.setMargins(0, 0, 45, 0);
//                groups.get(i).addView(tempButton, params);
//            }
//            groups.get(i).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                    try {
//                        Log.i("Test", "checkId: " + checkedId);
//                        int temp = checkedId;
//                        int ii = 0;
//                        while (true) {
//                            if (temp > car.get(ii).size()) {
//                                temp -= car.get(ii).size();
//                            } else {
//                                break;
//                            }
//                            ii++;
//                        }
//                        choose_car.get(ii).add(temp - 1);
//                        Log.i("Test", "topic ans " + ii + "  " + temp);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        Button button = (Button) page2.findViewById(R.id.submit);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < choose_car.size(); i++) {
//                    if (choose_car.get(i).size() == 0) {
//                        int y = i + 1;
//                        Toast.makeText(InsuTestActivity.this, "第" + y + "题还没选哦~", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
//                InsuTestActivity.this.finish();
//            }
//        });
//
//    }

    private void getData() {
        topic = getResources().getStringArray(R.array.insu_test_topic);
        data = new ArrayList<>();
        choose = new ArrayList<>();
        for (int i = 1; i < topic.length + 1; i++) {
            ArrayList<String> temp = new ArrayList<>();
            int a = getResources().getIdentifier("insu_test_q" + i, "array", getApplicationContext().getPackageName());
            String[] ss = getResources().getStringArray(a);
            Collections.addAll(temp, ss);
            data.add(temp);
            choose.add(new ArrayList<Integer>());
        }

//        car = new ArrayList<>();
//        choose_car = new ArrayList<>();
//        for (int i = 1; i < 7; i++) {
//            ArrayList<String> temp = new ArrayList<>();
//            int a = getResources().getIdentifier("car" + i, "array", getApplicationContext().getPackageName());
//            String[] ss = getResources().getStringArray(a);
//            Collections.addAll(temp, ss);
//            car.add(temp);
//            choose_car.add(new ArrayList<Integer>());
//        }
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
                    convertView = (getLayoutInflater()).inflate(R.layout.item_insut_list, null);
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

    private void submit() {
        try {
            ArrayList<String> keyword = new ArrayList<String>();
            for (int ii = 0; ii < choose.size(); ii++) {
                ArrayList<Integer> temp = choose.get(ii);
                if (temp.size() == 0) {
                    pager.setCurrentItem(ii);
                    Toast.makeText(InsuTestActivity.this, "您这道题还没选择哦~", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (ii) {
                    case 0:
                        keyword.add(data.get(ii).get(temp.get(0)));
                        break;
                    case 1:
                        if (temp.get(0) == 0)
                            keyword.add("宠物");
                        break;
                    case 2:
                        keyword.add(data.get(ii).get(temp.get(0)));
                        break;
                    case 3:
                        if (temp.get(0) == 0)
                            keyword.add("出差");
                        break;
                    case 4:
                        if (temp.get(0) == 0)
                            keyword.add("旅游");
                        break;
                    case 5:
                        if (temp.get(0) == 0)
                            keyword.add("住房");
                        break;
                    case 6:
                        if (temp.get(0) == 0)
                            keyword.add("车");
                        break;
                }
            }
            presenter.submit(new Gson().toJson(keyword));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void setVisibility(int x) {
//        page1.setVisibility(View.GONE);
//        page2.setVisibility(View.GONE);
//        LinearLayout temp = (x == 1 ? page1 : page2);
//        temp.setVisibility(View.VISIBLE);
//    }

    @Override
    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(InsuTestActivity.this);
        }
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myFinish(boolean finish) {
        InsuTestActivity.this.finish();
    }

}
