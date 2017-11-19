package com.exercise.p.citicup.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.helper.CheckableLinearLayout;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.helper.ViewPagerAdapter;
import com.exercise.p.citicup.presenter.RiskTestPresenter;
import com.exercise.p.citicup.view.ShowDialogView;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.Collections;

public class RiskTestActivity extends AppCompatActivity implements ShowDialogView {

    String[] topic;
    ArrayList<ArrayList<String>> data;
    ArrayList<ArrayList<Integer>> score;
    ArrayList<ArrayList<Integer>> choose;
    String[] type;
    String[] ability;
    String[] expect;
    String[] text;

    int sco_all = 0;

    ViewPager pager;
    LinearLayout page1;
    LinearLayout page2;
    TextView scoreText;
    TextView typeView;
    TextView abilityView;
    TextView expectView;
    TextView textView;
    Button retest;
    DonutProgress donutProgress;
    ProgressDialog dialog;

    RiskTestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_test);
        presenter = new RiskTestPresenter(this);
        getData();
        initToolBar();
        findView();
        initView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.riskt_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiskTestActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void findView() {
        page1 = (LinearLayout) findViewById(R.id.page1);
        page2 = (LinearLayout) findViewById(R.id.page2);
        pager = (ViewPager) findViewById(R.id.viewpager);

        scoreText = (TextView) findViewById(R.id.score);
        typeView = (TextView) findViewById(R.id.type);
        abilityView = (TextView) findViewById(R.id.ability);
        expectView = (TextView) findViewById(R.id.expect);
        textView = (TextView) findViewById(R.id.text);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        retest = (Button) findViewById(R.id.retest);
    }

    private void initView() {

        final ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < topic.length; i++) {
            View view1;
            if (i == topic.length - 1) {
                view1 = View.inflate(RiskTestActivity.this, R.layout.layout_risk_test_end, null);
                Button before = (Button) view1.findViewById(R.id.before);
                final int temp1 = i;
                before.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp1 - 1, true);
                    }
                });
            } else if (i == 0) {
                view1 = View.inflate(RiskTestActivity.this, R.layout.layout_risk_test_fir, null);
                Button next = (Button) view1.findViewById(R.id.next);
                final int temp = i;
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pager.setCurrentItem(temp + 1, true);
                    }
                });
            } else {
                view1 = View.inflate(RiskTestActivity.this, R.layout.layout_risk_test_mid, null);
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

            //初始化选项信息
            final ListView listView = (ListView) view1.findViewById(R.id.items);
            if (i != 8 && i != 11) {
                setAdapter(listView, data.get(i), i, true);
            } else {
                setAdapter(listView, data.get(i), i, false);
            }

            //初始化标题
            TextView textView = (TextView) view1.findViewById(R.id.topic);
            textView.setText(topic[i]);

            views.add(view1);
        }
        pager.setAdapter(new ViewPagerAdapter(views));

        //设置page2即测试结果页面
        retest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sco_all = 0;
                page1.setVisibility(View.VISIBLE);
                pager.setCurrentItem(0);
                page2.setVisibility(View.GONE);
            }
        });
    }

    private void getData() {
        data = new ArrayList<>();
        score = new ArrayList<>();
        choose = new ArrayList<>();
        topic = getResources().getStringArray(R.array.risk_test_topic);
        for (int i = 1; i < topic.length + 1; i++) {
            //选项信息
            ArrayList<String> temp_s = new ArrayList<>();
            int a_s = getResources().getIdentifier("rish_test_q" + i, "array", getApplicationContext().getPackageName());
            String[] ss = getResources().getStringArray(a_s);
            Collections.addAll(temp_s, ss);
            data.add(temp_s);

            //选项分数
            ArrayList<Integer> temp_i = new ArrayList<>();
            int a_i = getResources().getIdentifier("rish_test_s" + i, "array", getApplicationContext().getPackageName());
            int[] ii = getResources().getIntArray(a_i);
            for (int j = 0; j < ii.length; j++) {
                temp_i.add(ii[j]);
            }
            score.add(temp_i);

            choose.add(new ArrayList<Integer>());
        }

        type = getResources().getStringArray(R.array.rish_test_result_type);
        ability = getResources().getStringArray(R.array.rish_test_result_ability);
        expect = getResources().getStringArray(R.array.rish_test_result_expect);
        text = getResources().getStringArray(R.array.rish_test_result_text);
    }

    private int getLevel(int sco) {
        if (sco < 30)
            return 0;
        if (sco < 40)
            return 1;
        if (sco < 62)
            return 2;
        if (sco < 78)
            return 3;
        else return 4;
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
                    convertView = (getLayoutInflater()).inflate(R.layout.item_test_list, null);
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
        sco_all = 0;
        for (int ii = 0; ii < choose.size(); ii++) {
            ArrayList<Integer> temp = choose.get(ii);
            if (temp.size() == 0) {
                pager.setCurrentItem(ii);
                Toast.makeText(RiskTestActivity.this, "您这道题还没选择哦~", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int ij = 0; ij < temp.size(); ij++) {
                sco_all += score.get(ii).get(temp.get(ij));
            }
        }
        presenter.submit(sco_all);
        scoreText.setText(sco_all + "分");
        int temp = getLevel(sco_all);
        typeView.setText(type[temp]);
        abilityView.setText("您的风险承受能力：" + ability[temp]);
        expectView.setText("您的获利期待：" + expect[temp]);
        textView.setText(text[temp]);
        donutProgress.setDonut_progress(sco_all + "");
    }

    @Override
    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(RiskTestActivity.this);
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
        page1.setVisibility(finish ? View.GONE : View.VISIBLE);
        page2.setVisibility(finish ? View.VISIBLE : View.GONE);
    }

}
