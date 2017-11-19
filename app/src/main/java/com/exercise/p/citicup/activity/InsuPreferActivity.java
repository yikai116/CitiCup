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

import com.exercise.p.citicup.helper.MyCards;
import com.exercise.p.citicup.tree_node_helper.MyHolder;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.tree_node_helper.TreeNodeHelper;
import com.exercise.p.citicup.helper.ViewPagerAdapter;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.presenter.InsuPreferPresenter;
import com.exercise.p.citicup.view.ShowDialogView;
import com.google.gson.Gson;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

public class InsuPreferActivity extends AppCompatActivity implements ShowDialogView {

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
        tabLayout = (TabLayout) findViewById(R.id.insul_tab);
        pager = (ViewPager) findViewById(R.id.insul_pager);
    }

    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.insul_toolbar);
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

    private void initPage1Data() {

        TreeNode p1 = new TreeNode(new MyHolder.IconTreeItem("理财型", R.drawable.icon_insu_prefer_1_1_1));
        TreeNode p1_1 = new TreeNode(new MyHolder.IconTreeItem("保本"));
        TreeNode p1_2 = new TreeNode(new MyHolder.IconTreeItem("非保本"));
        p1.addChildren(p1_1, p1_2);

        TreeNode p2 = new TreeNode(new MyHolder.IconTreeItem("保障型", R.drawable.icon_insu_prefer_1_1_2));

        String[] ss_1_2 = getResources().getStringArray(R.array.insu_prefer_1_2);
        for (int i = 1; i < ss_1_2.length + 1; i++) {
            TreeNode temp2 = new TreeNode(new MyHolder.IconTreeItem(ss_1_2[i - 1], R.drawable.icon_insu_prefer_1_2));
            int res = getResources().getIdentifier("insu_prefer_1_2_" + i, "array", getApplicationContext().getPackageName());
            String[] ss_1_3 = getResources().getStringArray(res);
            for (int j = 0; j < ss_1_3.length; j++) {
                TreeNode temp3 = new TreeNode(new MyHolder.IconTreeItem(ss_1_3[j]));
                temp2.addChildren(temp3);
            }
            p2.addChildren(temp2);
        }

        page1_root.addChildren(p1, p2);

    }


    private void initPage2() {
        List<MyCards.MyTextCard> cards1Content = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            int id = getResources().getIdentifier("b_1_" + i, "id", getApplicationContext().getPackageName());
            cards1Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(id), false));
        }

        for (final MyCards.MyTextCard card : cards1Content) {
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
        cards1 = new MyCards<>(cards1Content);

        List<MyCards.MyTextCard> cards2Content = new ArrayList<>();
        cards2Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_2_1), false));
        cards2Content.add(new MyCards.MyTextCard((TextView) page2.findViewById(R.id.b_2_2), false));
        for (final MyCards.MyTextCard card : cards2Content) {
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
        cards2 = new MyCards<>(cards2Content);

        Button submit = (Button) page2.findViewById(R.id.fragment_insul_2_commit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        try {
            List<TreeNode> list = page1_root.getChildren();

            ArrayList<String> one = (ArrayList<String>) TreeNodeHelper.getNodeText(TreeNodeHelper.getSelected(list.get(0)));
            if (one.size() == 0) {
                one.addAll(TreeNodeHelper.getNodeText(TreeNodeHelper.getLeaf(list.get(0))));
            }

            ArrayList<String> two = (ArrayList<String>) TreeNodeHelper.getNodeText(TreeNodeHelper.getSelected(list.get(1)));
            if (two.size() == 0) {
                two.addAll(TreeNodeHelper.getNodeText(TreeNodeHelper.getLeaf(list.get(1))));
            }

            ArrayList<String> three = (ArrayList<String>) cards1.getSelectedText();
            if (three.size() == 0) {
                three.addAll(cards1.getAllText());
            }

            ArrayList<String> four = (ArrayList<String>) cards2.getSelectedText();
            if (four.size() == 0) {
                four.addAll(cards2.getAllText());
            }

            ArrayList<ArrayList<String>> json = new ArrayList<>();
            json.add(one);
            json.add(two);
            json.add(three);
            json.add(four);
            Log.i("Test", new Gson().toJson(json));
            InsuPreferInfo info = new InsuPreferInfo();
            one.addAll(two);
            info.setInsuType(one);
            info.setTheme(three);
            info.setPayMethod(four);
            presenter.submit(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(InsuPreferActivity.this);
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
        InsuPreferActivity.this.finish();
    }
}

