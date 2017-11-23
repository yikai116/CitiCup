package com.exercise.p.citicup.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.helper.MyCards;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.presenter.ManaPreferPresenter;
import com.exercise.p.citicup.view.ShowDialogView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FinaPreferActivity extends AppCompatActivity implements ShowDialogView {

    List<MyCards> lists;
    ProgressDialog dialog;
    ManaPreferPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fina_prefer);
        presenter = new ManaPreferPresenter(this);
        initToolBar();
        findView();
        initView();
    }

    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.manal_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinaPreferActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 找到控件
     */
    private void findView() {
        List<MyCards.MyTextCard> cards1Content = new ArrayList<>();
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_1), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_2), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_3), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_4), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_5), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_6), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_1_7), false));

        List<MyCards.MyTextCard> cards2Content = new ArrayList<>();
        cards2Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_2_1), false));
        cards2Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_2_2), false));
        cards2Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_2_3), false));

        List<MyCards.MyTextCard> cards3Content = new ArrayList<>();
        cards3Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_3_1), false));
        cards3Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_3_2), false));
        cards3Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_3_3), false));
        cards3Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_3_4), false));
        cards3Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_3_5), false));

        List<MyCards.MyTextCard> cards4Content = new ArrayList<>();
        cards4Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_4_1), false));
        cards4Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_4_2), false));
        cards4Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_4_3), false));
        cards4Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_4_4), false));


        lists = new ArrayList<>();
        lists.add(new MyCards(cards1Content));
        lists.add(new MyCards(cards2Content));
        lists.add(new MyCards(cards3Content));
        lists.add(new MyCards(cards4Content));
    }

    /**
     * 设置控件，如点击事件等
     */
    private void initView(){
        for (final MyCards cards : lists) {
            for (final MyCards.MyTextCard card : (List<MyCards.MyTextCard>) cards.getCards()) {
                card.setOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (card.isSelected()) {
                            card.setAppearance(R.drawable.shape_insul_card_normal, getResources().getColor(R.color.cardTextNormal));
                            card.setSelected(false);
                        } else {
                            card.setAppearance(R.drawable.shape_insul_card_selected, getResources().getColor(R.color.white));
                            card.setSelected(true);
                        }
                    }
                });
            }
        }

        Button submit = (Button) findViewById(R.id.manal_commit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinaPreferInfo info = new FinaPreferInfo();
                for (int i = 0; i < lists.size(); i++) {
                    ArrayList<String> temp = (ArrayList<String>) lists.get(i).getSelectedText();
//                    Log.i("Test","temp" + i + temp + "");
                    if (temp.size() == 0) {
                        temp.addAll(lists.get(i).getAllText());
                    }
                    switch (i) {
                        case 0:
                            info.setDuration(temp);
                            break;
                        case 1:
                            info.setProType(temp);
                            break;
                        case 2:
                            info.setLevel(temp);
                            break;
                        case 3:
                            info.setRevenue(temp);
                            break;
                        default:
//                            Log.i("Test", new Gson().toJson(temp));
                    }
                }
//                Log.i("Test","info : " + new Gson().toJson(info));
                presenter.submit(info);
            }
        });
    }

    @Override
    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(FinaPreferActivity.this);
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
        FinaPreferActivity.this.finish();
    }
}
