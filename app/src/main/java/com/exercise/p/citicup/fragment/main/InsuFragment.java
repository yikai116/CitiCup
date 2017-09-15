package com.exercise.p.citicup.fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.activity.FeedbackActivity;
import com.exercise.p.citicup.activity.InsuProDetailActivity;
import com.exercise.p.citicup.activity.MistakeActivity;
import com.exercise.p.citicup.activity.ReadActivity;
import com.exercise.p.citicup.activity.TeachActivity;
import com.exercise.p.citicup.dto.InsuPro;
import com.exercise.p.citicup.presenter.InsuProPresenter;
import com.exercise.p.citicup.view.InsuFragView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by p on 2017/8/5.
 */

public class InsuFragment extends Fragment implements InsuFragView {
    View root;
    PullToRefreshListView insuConentView;
    InsuProPresenter presenter;
    ArrayList<InsuPro> pros;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Test", "InsuFragment Creat");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("Test", "InsuFragment CreatView");
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_insu, container, false);
        initTop();
        insuConentView = (PullToRefreshListView) root.findViewById(R.id.insu_content);
        presenter = new InsuProPresenter(this);
        if (pros == null)
            presenter.getInsuPro(false);
        else
            initView(pros,false);

//        insuConentView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                presenter.getInsuPro();
//            }
//        });
        insuConentView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.getInsuPro(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.getInsuPro(true);
            }
        });
        return root;
    }

    private void initTop() {
        root.findViewById(R.id.insu_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InsuFragment.this.getContext(), ReadActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.insu_teach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InsuFragment.this.getContext(), TeachActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.insu_mistake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InsuFragment.this.getContext(), MistakeActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.insu_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InsuFragment.this.getContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(final ArrayList<InsuPro> pros1, boolean more) {
        if (insuConentView != null)
            insuConentView.onRefreshComplete();
        if (more) {
            this.pros.addAll(pros1);
            Log.i("Test", "Size" + this.pros.size());
        } else
            this.pros = pros1;
        insuConentView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return pros.size();
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
                    convertView = (getActivity().getLayoutInflater()).inflate(R.layout.item_insu_content_list, null);
                }
                final RelativeLayout layout = (RelativeLayout) convertView;

                initView(layout, position);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), InsuProDetailActivity.class);
                        intent.putExtra("product", pros.get(position));
                        startActivity(intent);
                    }
                });
                return layout;
            }

            private void initView(RelativeLayout layout, int position) {
                ImageView proIcon = (ImageView) layout.findViewById(R.id.pro_icon);
                proIcon.setImageResource(R.drawable.icon_item_insu_content_pan);

                TextView proName = (TextView) layout.findViewById(R.id.pro_name);
                proName.setText(Html.fromHtml("<b><tt>" + pros.get(position).getName() + "</tt></b>"));

                TextView proCompany = (TextView) layout.findViewById(R.id.pro_company);
                proCompany.setText(Html.fromHtml("保险公司：<b><tt>" + pros.get(position).getCompany() + "</tt></b>"));

                TextView proType = (TextView) layout.findViewById(R.id.pro_type);
                proType.setText(Html.fromHtml("产品类别：<b><tt>" + pros.get(position).getType() + "</tt></b>"));

                TextView proPayMethod = (TextView) layout.findViewById(R.id.pro_pay_method);
                proPayMethod.setText(Html.fromHtml("缴费方式：<b><tt>" + pros.get(position).getPayMethod() + "</tt></b>"));

                TextView proAdvance = (TextView) layout.findViewById(R.id.pro_advance);
                proAdvance.setText(pros.get(position).getAdvance());

                ImageView proRecomment = (ImageView) layout.findViewById(R.id.pro_recomment);
                proRecomment.setVisibility(position == 1 || position == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        if (insuConentView != null)
            insuConentView.onRefreshComplete();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<InsuPro> getData() {
        ArrayList<InsuPro> pros = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            InsuPro pro = new InsuPro();
            pro.setId(i);
            pro.setName("平安借款人意外伤害保险");
            pro.setCompany("平安保险");
            pro.setType("意外险");
            pro.setPayMethod("趸缴");
            pro.setAdvance("产品优势：1.个人借款人的配偶，对借款人家庭 进行更为全面的保障；2.对于企业及社会产品优势：1.个人借款人的配偶，对借款人家庭 进行更为全面的保障；2.对于企业及社会产品优势：1.个人借款人的配偶，对借款人家庭 进行更为全面的保障；2.对于企业及社会");
            pros.add(pro);
        }
        return pros;
    }

    @Override
    public void onDestroyView() {

        Log.i("Test", "InsuFragment DestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Test", "InsuFragment Destroy");
        super.onDestroy();
    }
}
