import cn.sxy.compare.CompareCollections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        List<Integer> listA = Arrays.asList(1, 2, 3);
        List<Integer> listB = Arrays.asList(2, 3, 4);

        CompareCollections<Integer, Integer> c0 = CompareCollections.create(listA, listB);
        System.out.println(c0.getAddMap());  // {4: 4}
        System.out.println(c0.getSubMap());  // {1: 1}
        System.out.println(c0.getUpdateMap());  // {}

        Bean b0 = new Bean().setId(0).setHalf(0d).setContent("0");
        Bean b1 = new Bean().setId(1).setHalf(.5).setContent("1");
        Bean b0x = new Bean().setId(0).setHalf(0d).setContent("0x");
        Bean b1x = new Bean().setId(1).setHalf(5d).setContent("1x");
        List<Bean> listC = Arrays.asList(b0, b1);
        List<Bean> listD = Arrays.asList(b0x, b1x);

        CompareCollections<Integer, Bean> c1 = CompareCollections.create(listC, listD, Bean::getId);
        System.out.println(c1.getAddMap());  // {}
        System.out.println(c1.getSubMap());  // {}
        System.out.println(c1.getUpdateMap());  // {0: {$b0, $b0x}, 1: {$b1, $b1x}}

        Map<Integer, Bean> mapA = new HashMap<>();
        mapA.put(1, b0);
        mapA.put(2, b1);
        Map<Integer, Bean> mapB = new HashMap<>();
        mapB.put(1, b0);
        mapB.put(2, b1x);
        CompareCollections<Integer, Bean> c2 = CompareCollections.create(mapA, mapB);
        System.out.println(c2.getAddMap());  // {}
        System.out.println(c2.getSubMap());  // {}
        System.out.println(c2.getUpdateMap());  // {2: {$b1, $b1x}}
    }

    static class Bean {
        private Integer id;
        private Double half;
        private String content;

        public Integer getId() {
            return id;
        }

        public Bean setId(Integer id) {
            this.id = id;
            return this;
        }

        public Double getHalf() {
            return half;
        }

        public Bean setHalf(Double half) {
            this.half = half;
            return this;
        }

        public String getContent() {
            return content;
        }

        public Bean setContent(String content) {
            this.content = content;
            return this;
        }
    }

}
