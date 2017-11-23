package com.exercise.p.citicup.fragment.main;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.activity.AssetsActivity;
import com.exercise.p.citicup.activity.FinaPreferActivity;
import com.exercise.p.citicup.activity.FinaProDetailActivity;
import com.exercise.p.citicup.dto.FinaPro;
import com.exercise.p.citicup.dto.FinaPro;
import com.exercise.p.citicup.presenter.FinaProPresenter;
import com.exercise.p.citicup.view.FinaFragView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by p on 2017/8/5.
 */

public class FinaFragment extends Fragment implements FinaFragView {
    View root;
    PullToRefreshListView finaConentView;
    FinaProPresenter presenter;
    ArrayList<FinaPro> pros;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_fina, container, false);
        finaConentView = (PullToRefreshListView) root.findViewById(R.id.fina_content);
        initTop();
        presenter = new FinaProPresenter(this);
        if (pros == null)
            presenter.getFinaPro(false);
        else
            initView(pros, false);
//        initView(getData());
//        finaConentView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                presenter.getFinaPro();
//            }
//        });
        finaConentView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.getFinaPro(false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                presenter.getFinaPro(true);
            }
        });
        return root;
    }

    private void initTop() {
        root.findViewById(R.id.fina_asset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinaFragment.this.getContext(), AssetsActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.fina_fina_prefer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FinaFragment.this.getContext(), FinaPreferActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initView(final ArrayList<FinaPro> pros1, boolean more) {
        if (finaConentView != null)
            finaConentView.onRefreshComplete();
        if (more) {
            this.pros.addAll(pros1);
//            Log.i("Test", "Size" + this.pros.size());
        } else
            this.pros = pros1;
        finaConentView.setAdapter(new BaseAdapter() {
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
                    convertView = (getActivity().getLayoutInflater()).inflate(R.layout.item_fina_content_list, null);
                }
                final RelativeLayout layout = (RelativeLayout) convertView;

                initView(layout, position);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), FinaProDetailActivity.class);
                        intent.putExtra("product", pros.get(position));
                        startActivity(intent);
                    }
                });
                return layout;
            }

            private void initView(RelativeLayout layout, int position) {

                TextView proName = (TextView) layout.findViewById(R.id.pro_name);
                proName.setText(Html.fromHtml("<b><tt>" + pros.get(position).getName() + "</tt></b>"));

                TextView proPreEarn = (TextView) layout.findViewById(R.id.pro_pre_earn);
                proPreEarn.setText(Html.fromHtml("预期年化收益率：<b><tt>" + pros.get(position).getPreEarn() + "%" + "</tt></b>"));

                TextView proMinAmount = (TextView) layout.findViewById(R.id.pro_min_amount);
                proMinAmount.setText(Html.fromHtml("起购金额：<b><tt>" + pros.get(position).getMinAmount() + "</tt></b>"));

                TextView proDuration = (TextView) layout.findViewById(R.id.pro_duration);
                proDuration.setText(Html.fromHtml("投资期限：<b><tt>" + pros.get(position).getDuration() + "</tt></b>"));

                TextView proIssuintDate = (TextView) layout.findViewById(R.id.pro_issue_date);
                proIssuintDate.setText(Html.fromHtml("发行时间：<b><tt>" + pros.get(position).getIssuintDate() + "</tt></b>"));

                TextView proLevel = (TextView) layout.findViewById(R.id.pro_level);
                proLevel.setText(Html.fromHtml("风险等级：<b><tt>" + pros.get(position).getLevel() + "</tt></b>"));

                ImageView proRecomment = (ImageView) layout.findViewById(R.id.pro_recomment);
                proRecomment.setVisibility(position == 1 || position == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showMessage(String msg) {
        if (finaConentView != null)
            finaConentView.onRefreshComplete();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<FinaPro> getData() {
        ArrayList<FinaPro> pros = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FinaPro pro = new FinaPro();
            pro.setId(i);
            pro.setName("乾元-优享型 2017-24理财产品");
            pro.setCompany("平安保险");
            pro.setMinAmount("5万");
            pro.setDuration("76天");
            pro.setIssuintDate("9.13 09:00- 9.18 17:00");
            pro.setPreEarn(4.7f);
            pro.setRedeem(false);
            pro.setGuaranteed(false);
            pros.add(pro);
        }
        return pros;
    }
}
