package cn.van.kuang.java.core.java8;

import java.time.Clock;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambda {

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    static class Functions {
        String startWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    @FunctionalInterface
    interface PersonFactory<P extends Person> {
        P create(int id, String name);
    }

    static class Person {
        int id;
        String name;

        public Person() {
        }

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("D", "C", "B", "A");
        Collections.sort(strings, String::compareTo);
        strings.forEach(System.out::println);

        printDelimeter();

        Converter<String, Integer> integerConverter = Integer::valueOf;
        Integer integer = integerConverter.convert("123");
        System.out.println(integer);

        printDelimeter();

        Functions functions = new Functions();
        Converter<String, String> stringConverter = functions::startWith;
        String prefix = stringConverter.convert("Kobe");
        System.out.println(prefix);

        printDelimeter();

        PersonFactory<Person> factory = Person::new;
        Person kobe = factory.create(1, "Kobe");
        System.out.println(kobe);

        printDelimeter();

        Supplier<Person> personSupplier = Person::new;
        Person emptyPerson = personSupplier.get();
        System.out.println(emptyPerson);

        printDelimeter();

        Predicate<String> stringPredicate = (s) -> s.length() > 0;
        System.out.println(stringPredicate.test(""));
        System.out.println(stringPredicate.test("Kobe"));

        printDelimeter();

        Consumer<Person> personConsumer = (p) -> System.out.println("Hello " + p.name);
        personConsumer.accept(kobe);

        printDelimeter();

        Person paul = factory.create(2, "Paul");
        Comparator<Person> personComparator = (p1, p2) -> (p1.id - p2.id);
        int compare = personComparator.compare(kobe, paul);
        System.out.println(compare);

        printDelimeter();

        Optional<String> optional = Optional.of("Kobe");
        System.out.println(optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("Paul"));
        optional.ifPresent(System.out::println);

        printDelimeter();

        strings.stream().filter((s) -> s.startsWith("A")).forEach(System.out::println);

        printDelimeter();

        strings.stream().sorted().forEach(System.out::println);

        printDelimeter();

        System.out.println(strings.stream().filter((s) -> s.contains("A")).count());

        Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.getZone());
        System.out.println(Date.from(clock.instant()));
    }

    private static void printDelimeter() {
        System.out.println("-------------------------------------------");
    }

}
