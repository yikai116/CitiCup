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
     * @param root 根节点
     * @return 选中的叶节点集合
     */
    private List<TreeNode> getSelected(TreeNode root) {
        List<TreeNode> result = new ArrayList<>();
        if (root.isLeaf()) {
            return null;
        }
        for (TreeNode node1 : root.getChildren()) {
            if (node1.isLeaf()) {
                if (node1.isSelected()) {
                    result.add(node1);
                }
            } else {
                List<TreeNode> rest = getSelected(node1);
                if (rest != null)
                    result.addAll(rest);
            }
        }
        return result;
    }
}
