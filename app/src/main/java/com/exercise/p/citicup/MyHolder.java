package com.exercise.p.citicup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {

    public MyHolder(Context c){
        super(c);
    }
    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        Log.i("Test",node.getLevel() + "");
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        view = inflater.inflate(R.layout.layout_node_inter, null, false);
        if (!node.isLeaf()) {
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Helper.dp2px(context, 43)));
            view.setPadding(view.getPaddingLeft() + Helper.dp2px(context,30) * (node.getLevel()-1),
                    view.getPaddingTop(),view.getPaddingRight(),view.getPaddingBottom());
            TextView tvValue = (TextView) view.findViewById(R.id.node_title);
            tvValue.setText(value.text);
            ImageView imageView = (ImageView) view.findViewById(R.id.node_icon);
            imageView.setImageResource(value.icon);
            node.setClickListener(new TreeNode.TreeNodeClickListener() {
                @Override
                public void onClick(TreeNode node, Object value) {
                    ImageView view1 = (ImageView) node.getViewHolder().getView().findViewById(R.id.node_indicator);
                    if (!node.isExpanded()) {
                        view1.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    } else {
                        view1.setImageResource(R.drawable.ic_chevron_right_black_24dp);
                    }
                }
            });
            node.setSelectable(false);
        }
        else {
            view = inflater.inflate(R.layout.layout_node_leaf, null, false);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Helper.dp2px(context, 43)));
            view.setPadding(view.getPaddingLeft() + Helper.dp2px(context,30) * (node.getLevel()-1),
                    view.getPaddingTop(),view.getPaddingRight(),view.getPaddingBottom());
            TextView tvValue = (TextView) view.findViewById(R.id.node_title);
            tvValue.setText(value.text);
            node.setSelectable(true);
            node.setClickListener(new TreeNode.TreeNodeClickListener() {
                @Override
                public void onClick(TreeNode node, Object value) {
                    node.setSelected(true);

                }
            });
        }
        return view;
    }

    public static class IconTreeItem {
        public int icon;
        public String text;

        public IconTreeItem(String text) {
            this.text = text;
        }

        public IconTreeItem(String text, int icon) {
            this.icon = icon;
            this.text = text;
        }
    }
}