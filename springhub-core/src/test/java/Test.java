import cn.springhub.base.tree.Node;
import cn.springhub.base.tree.Tree;
import cn.springhub.base.tree.TreeFactory;
import cn.springhub.base.tree.TreeNode;
import cn.springhub.base.util.RandomUtils;
import cn.springhub.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        testCustomerNode();
    }

    private static void testCustomerNode() {
        List<TestNode> list = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            list.add(new TestNode((long) (i + 1), (long) i));
            list1.add(i + 1);
            map.put(i + 1, i);
        }

        for (int i = 100; i < 300; i++) {
            Long parant = RandomUtils.random(100L);
            while (parant == (long) i) {
                parant = RandomUtils.random(100L);
            }
            list.add(new TestNode((long) i, parant));
            list1.add(i);
            map.put(i, parant.intValue());

        }

        // 构建树

//        Tree<TestNode> naryTree = TreeFactory.build(new TestNode(0L, 0L),
//            TestNode::getId,
//            TestNode::getParentId,
//            Comparator.comparingLong(TestNode::getPx),
//            list
//        );
//        System.out.println(naryTree);
//        Tree<Node<Long>> testNodeTree = TreeFactory.build(list);
//        System.out.println(testNodeTree);
//        String s = StringUtils.toString(naryTree);
//        System.out.println(s);
//        naryTree.postOrderTraverse(item -> {
//            List<? extends TreeNode<TestNode>> children = item.getChildren();
//        });

        Tree<Integer> tree = TreeFactory.build(0, item -> item, map::get, Comparator.comparingInt(Integer::intValue), list1);
        System.out.println(tree);

//        naryTree.postOrderTraverse(item -> {
//
//        }, 1);


    }

    public static class TestTreeNode implements TreeNode<TestNode> {

        @Override
        public TestNode getData() {
            return null;
        }

        @Override
        public List<? extends TreeNode<TestNode>> getChildren() {
            return List.of();
        }
    }

    public static class TestNode implements Node<Long> {

        private final Long id;
        private final Long parentId;

        public TestNode(Long id, Long parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public Long getParentId() {
            return parentId;
        }

        @Override
        public long getPx() {
            return id;
        }
    }
}