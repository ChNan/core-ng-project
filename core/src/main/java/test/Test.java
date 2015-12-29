package test;

import web.Domain;

/**
 * @author Dylan
 */
public class Test {

    public static void main(String[] args) {
        Integer v1 = 100;
        Integer v2 = 128;
        System.out.println(v1 == Integer.valueOf(100));
        System.out.println(v2 == Integer.valueOf(128));
        Domain domain = new Domain(10D);

        Double d = domain.getValue();
        domain.setValue(3d); // 减完之后

        System.out.println(d);
    }
}
