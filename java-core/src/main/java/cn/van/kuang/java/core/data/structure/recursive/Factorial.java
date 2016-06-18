package cn.van.kuang.java.core.data.structure.recursive;

public class Factorial {

    public int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n can't be negative");
        }

        if (n == 0) {
            return 1;
        }

        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();

        try {
            factorial.factorial(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(factorial.factorial(0));
        System.out.println(factorial.factorial(1));
        System.out.println(factorial.factorial(3));
    }

}
