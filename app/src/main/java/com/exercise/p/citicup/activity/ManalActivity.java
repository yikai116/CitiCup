package com.exercise.p.citicup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exercise.p.citicup.MyCards;
import com.exercise.p.citicup.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManalActivity extends AppCompatActivity {

    MyCards cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manal);
        initToolBar();
        initView();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.manal_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManalActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView(){
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

        List<MyCards.MyTextCard> cards5Content = new ArrayList<>();
        cards5Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_5_1), false));
        cards5Content.add(new MyCards.MyTextCard((TextView) findViewById(R.id.b_5_2), false));

        final List<MyCards> lists = new ArrayList<>();
        lists.add(new MyCards(cards1Content));
        lists.add(new MyCards(cards2Content));
        lists.add(new MyCards(cards3Content));
        lists.add(new MyCards(cards4Content));
        lists.add(new MyCards(cards5Content));

        for (final MyCards cards : lists) {
            for (final MyCards.MyTextCard card : (List<MyCards.MyTextCard>)cards.getCards()) {
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

        Button button = (Button) findViewById(R.id.manal_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<String>> json = new ArrayList<>();
                for (int i = 0; i < lists.size(); i++){
                    ArrayList<String> temp = (ArrayList<String>) lists.get(i).getSelectedText();
                    if (temp.size() == 0){
                        temp.addAll(lists.get(i).getAllText());
                    }
                    json.add(temp);
                }
            }
        });
    }
}
