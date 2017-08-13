package com.exercise.p.citicup.TreeNodeHelper;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2017/8/10.
 */

public class TreeNodeHelper {
    /**
     * 得到选中的TreeNode，针对叶节点
     *
     * @param root 根节点
     * @return 选中的叶节点集合
     */
    public static List<TreeNode> getSelected(TreeNode... root) {
        List<TreeNode> result = new ArrayList<>();
        for (TreeNode node : root) {
            if (node.isLeaf()) {
                if (node.isSelected()) {
                    result.add(node);
                }
            } else {
                for (TreeNode node1 : node.getChildren()) {
                    result.addAll(getSelected(node1));
                }
            }
        }
        return result;
    }

    public static List<TreeNode> getLeaf(TreeNode... root) {
        List<TreeNode> result = new ArrayList<>();
        for (TreeNode node : root) {
            if (node.isLeaf()) {
                result.add(node);
            } else {
                for (TreeNode node1 : node.getChildren()) {
                    result.addAll(getLeaf(node1));
                }
            }
        }
        return result;
    }

    public static List<String> getNodeText(List<TreeNode> nodes) {
        ArrayList<String> texts = new ArrayList<>();
        for (TreeNode node : nodes) {
            MyHolder.IconTreeItem item = (MyHolder.IconTreeItem) node.getValue();
            texts.add(item.text);
        }
        return texts;
    }
}
