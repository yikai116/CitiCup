package com.exercise.p.citicup.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.MyCards;
import com.exercise.p.citicup.TreeNodeHelper.MyHolder;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.TreeNodeHelper.TreeNodeHelper;
import com.exercise.p.citicup.ViewPagerAdapter;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.presenter.InsuPreferPresenter;
import com.exercise.p.citicup.view.ShowDialogView;
import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

public class InsuPreferActivity extends AppCompatActivity implements ShowDialogView{

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    View page1;
    View page2;
    TreeNode page1_root = TreeNode.root();

    MyCards cards1;
    MyCards cards2;
    ProgressDialog dialog;

    InsuPreferPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_prefer);
        presenter = new InsuPreferPresenter(this);
        findView();
        initToolBar();
        initTab();
        initPager();
        initPage1();
        initPage2();
    }

    private void findView() {
        toolbar = (Toolbar) findViewById(R.id.insul_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.insul_tab);
        pager = (ViewPager) findViewById(R.id.insul_pager);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsuPreferActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText("险种自选"));
        tabLayout.addTab(tabLayout.newTab().setText("其他条件"));
    }

    private void initPager() {
        List<View> views = new ArrayList<>();
        page1 = getLayoutInflater().inflate(R.layout.fragment_insu_prefer_1, null);
        page2 = getLayoutInflater().inflate(R.layout.fragment_insu_prefer_2, null);
        views.add(page1);
        views.add(page2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        pager.setAdapter(adapter);
        //结合TabLayout和Pager
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    private void initPage1() {
        LinearLayout layout = (LinearLayout) page1.findViewById(R.id.insul_1_root);
        initPage1Data();
        final AndroidTreeView tView = new AndroidTreeView(this, page1_root);
        tView.setDefaultViewHolder(MyHolder.class);
        layout.addView(tView.getView(),
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void initPage2() {
        List<MyCards.MyTextCard> cards1Content = new ArrayList<>();
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_1), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_2), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_3), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_4), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_5), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_6), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_7), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_8), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_9), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_10), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_11), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_12), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_13), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_14), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_15), false));
        cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_1_16), false));
        for (final MyCards.MyTextCard card : cards1Content) {
            card.setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (card.isSelected()) {
                        card.setAppearance(R.drawable.shape_insul_card_normal,getResources().getColor(R.color.cardTextNormal));
                        card.setSelected(false);
                    } else {
                        card.setAppearance(R.drawable.shape_insul_card_selected,getResources().getColor(R.color.white));
                        card.setSelected(true);
                    }
                }
            });
        }
        cards1 = new MyCards<>(cards1Content);

        List<MyCards.MyTextCard> cards2Content = new ArrayList<>();
        cards2Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_2_1), false));
        cards2Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_2_2), false));
        for (final MyCards.MyTextCard card : cards2Content) {
            card.setOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (card.isSelected()) {
                        card.setAppearance(R.drawable.shape_insul_card_normal,getResources().getColor(R.color.cardTextNormal));
                        card.setSelected(false);
                    } else {
                        card.setAppearance(R.drawable.shape_insul_card_selected,getResources().getColor(R.color.white));
                        card.setSelected(true);
                    }
                }
            });
        }
        cards2 = new MyCards<>(cards2Content);

        Button button = (Button) page2.findViewById(R.id.fragment_insul_2_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<TreeNode> list = page1_root.getChildren();

                    ArrayList<String> one = (ArrayList<String>) TreeNodeHelper.getNodeText(TreeNodeHelper.getSelected(list.get(0)));
                    if (one.size() == 0){
                        one.addAll(TreeNodeHelper.getNodeText(TreeNodeHelper.getLeaf(list.get(0))));
                    }

                    ArrayList<String> two = (ArrayList<String>) TreeNodeHelper.getNodeText(TreeNodeHelper.getSelected(list.get(1)));
                    if (two.size() == 0){
                        two.addAll(TreeNodeHelper.getNodeText(TreeNodeHelper.getLeaf(list.get(1))));
                    }

                    ArrayList<String> three = (ArrayList<String>) cards1.getSelectedText();
                    if (three.size() == 0){
                        three.addAll(cards1.getAllText());
                    }

                    ArrayList<String> four = (ArrayList<String>) cards2.getSelectedText();
                    if (four.size() == 0){
                        four.addAll(cards2.getAllText());
                    }

                    ArrayList<ArrayList<String>> json = new ArrayList<>();
                    json.add(one);
                    json.add(two);
                    json.add(three);
                    json.add(four);
                    Log.i("Test",new Gson().toJson(json));
                    InsuPreferInfo info = new InsuPreferInfo();
                    info.setInsuType(new Gson().toJson(one.addAll(two)));
                    info.setTheme(new Gson().toJson(three));
                    info.setPayMethod(new Gson().toJson(four));
                    presenter.submit(info);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void initPage1Data() {

        TreeNode p1 = new TreeNode(new MyHolder.IconTreeItem("理财型", R.drawable.icon_insu_prefer_1_1_1));
        TreeNode p1_1 = new TreeNode(new MyHolder.IconTreeItem("保本"));
        TreeNode p1_2 = new TreeNode(new MyHolder.IconTreeItem("非保本"));
        p1.addChildren(p1_1, p1_2);

        TreeNode p2 = new TreeNode(new MyHolder.IconTreeItem("保障型", R.drawable.icon_insu_prefer_1_1_2));

        TreeNode p2_1 = new TreeNode(new MyHolder.IconTreeItem("寿险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_1_1 = new TreeNode(new MyHolder.IconTreeItem("普通型"));
        TreeNode p2_1_2 = new TreeNode(new MyHolder.IconTreeItem("两全险"));
        p2_1.addChildren(p2_1_1, p2_1_2);

        TreeNode p2_2 = new TreeNode(new MyHolder.IconTreeItem("年金险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_2_1 = new TreeNode(new MyHolder.IconTreeItem("养老年金"));
        TreeNode p2_2_2 = new TreeNode(new MyHolder.IconTreeItem("教育年金"));
        TreeNode p2_2_3 = new TreeNode(new MyHolder.IconTreeItem("普通年金"));
        p2_2.addChildren(p2_2_1, p2_2_2, p2_2_3);

        TreeNode p2_3 = new TreeNode(new MyHolder.IconTreeItem("意外险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_3_1 = new TreeNode(new MyHolder.IconTreeItem("人身年金"));
        TreeNode p2_3_2 = new TreeNode(new MyHolder.IconTreeItem("交通年金"));
        TreeNode p2_3_3 = new TreeNode(new MyHolder.IconTreeItem("航空年金"));
        p2_3.addChildren(p2_3_1, p2_3_2, p2_3_3);

        TreeNode p2_4 = new TreeNode(new MyHolder.IconTreeItem("个人财险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_4_1 = new TreeNode(new MyHolder.IconTreeItem("家财险"));
        TreeNode p2_4_2 = new TreeNode(new MyHolder.IconTreeItem("汽车险"));
        TreeNode p2_4_3 = new TreeNode(new MyHolder.IconTreeItem("房贷险"));
        p2_4.addChildren(p2_4_1, p2_4_2, p2_4_3);

        TreeNode p2_5 = new TreeNode(new MyHolder.IconTreeItem("企业财险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_5_1 = new TreeNode(new MyHolder.IconTreeItem("财产保险"));
        TreeNode p2_5_2 = new TreeNode(new MyHolder.IconTreeItem("短期意健险"));
        TreeNode p2_5_3 = new TreeNode(new MyHolder.IconTreeItem("保证保险"));
        TreeNode p2_5_4 = new TreeNode(new MyHolder.IconTreeItem("信用保险"));
        TreeNode p2_5_5 = new TreeNode(new MyHolder.IconTreeItem("农业保险"));
        TreeNode p2_5_6 = new TreeNode(new MyHolder.IconTreeItem("责任保险"));
        p2_5.addChildren(p2_5_1, p2_5_2, p2_5_3, p2_5_4, p2_5_5, p2_5_6);

        TreeNode p2_6 = new TreeNode(new MyHolder.IconTreeItem("旅游险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_6_1 = new TreeNode(new MyHolder.IconTreeItem("境内"));
        TreeNode p2_6_2 = new TreeNode(new MyHolder.IconTreeItem("境外"));
        TreeNode p2_6_3 = new TreeNode(new MyHolder.IconTreeItem("港澳台"));
        p2_6.addChildren(p2_6_1, p2_6_2, p2_6_3);

        TreeNode p2_7 = new TreeNode(new MyHolder.IconTreeItem("健康险", R.drawable.icon_insu_prefer_1_2));
        TreeNode p2_7_1 = new TreeNode(new MyHolder.IconTreeItem("护理"));
        TreeNode p2_7_2 = new TreeNode(new MyHolder.IconTreeItem("女性疾病"));
        TreeNode p2_7_3 = new TreeNode(new MyHolder.IconTreeItem("失能收入损失险"));
        TreeNode p2_7_4 = new TreeNode(new MyHolder.IconTreeItem("重大疾病"));
        TreeNode p2_7_5 = new TreeNode(new MyHolder.IconTreeItem("住院医疗"));
        p2_7.addChildren(p2_7_1, p2_7_2, p2_7_3, p2_7_4, p2_7_5);

        p2.addChildren(p2_1, p2_2, p2_3, p2_4, p2_5, p2_6, p2_7);

        page1_root.addChildren(p1, p2);

    }

    @Override
    public void showDialog(String title) {
        if (dialog == null){
            dialog = new ProgressDialog(InsuPreferActivity.this);
        }
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog!=null)
            dialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myFinish(boolean finish) {
        InsuPreferActivity.this.finish();
    }
}
