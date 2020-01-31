import java.util.*;

public class Main {
    public static void main(String[] args) {
        NavigableSet<MyClass> myClassTreeSet = new TreeSet<>();

        MyClass firstElement = new MyClass("aaa", 123);
        myClassTreeSet.add(firstElement);
        myClassTreeSet.add(new MyClass("bbb", 124));
        myClassTreeSet.add(new MyClass("ccc", 125));

        System.out.println(myClassTreeSet);

        firstElement.age = 200;

        System.out.println(myClassTreeSet);

        myClassTreeSet.pollLast();

        System.out.println(myClassTreeSet);


    }

    private static class MyClass implements Comparable<MyClass> {
        private String name;
        private int age;

        public MyClass(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(MyClass myClass) {
            return Integer.compare(this.age, myClass.age);
        }

        @Override
        public boolean equals(Object myOtherClass) {
            MyClass wat = (MyClass) myOtherClass;
            return this.name.equals(wat.name) && this.age == wat.age;
        }

        @Override
        public String toString() {
            return "MyClass{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
