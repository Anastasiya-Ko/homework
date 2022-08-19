package main;

import java.util.*;

public class Person {
    private int id;
    private String name;
    private int age;
    private Gender gender;


    /**
     * Мапа для хранения списка всех Персонов
     */
    private static Map<Integer, Person> allPersons = new HashMap<>();

    /**
     * Счётчик. При создании нового пользователя, присваивает порядковый ID персону
     */
    private static int countId = 0;


    public Person(String name, int age, Gender gender) {

        if (name != null && !name.isEmpty() && age > 0 && gender != null) {
            this.name = name;
            this.age = age;
            this.gender = gender;


            if (!hasPerson()) {            //проверяет есть ли такой объект в allPersons
                countId++;                //если нет, то увеличиваем счётчик
                this.id = countId;
                allPersons.put(id, this); //и добавляем его в список пользователей
            }

        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                gender == person.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }

    /**
     * Метод проверяет есть ли такой объект в списке allPersons
     *
     * @return
     */
    private boolean hasPerson() {
        for (Person person : allPersons.values()) {
            if (person.equals(this) && person.hashCode() == this.hashCode()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }

    /**
     * Метод возвращает список всех Персонов
     *
     * @return
     */
    public static List<Person> getAllPersons() {
        return new ArrayList<>(allPersons.values());
    }

    /**
     * Перегруженный метод. Возвращает список Персонов, в зависимости, от переданного гендера
     *
     * @param gender
     * @return
     */
    public static List<Person> getAllPersons(Gender gender) {
        List<Person> listAllPerson = new ArrayList<>();

        for (Person person : allPersons.values()) {
            if (person.gender == gender) {
                listAllPerson.add(person);
            }
        }
        return listAllPerson;
    }

    /**
     * Возвращает количество персонов в общем списке
     *
     * @return
     */
    public static int getHowManyPerson() {
        return allPersons.size();
    }

    /**
     * Возвращает количество персонов в списке по половому признаку
     *
     * @return
     */
    public static int getHowManyPerson(Gender gender) {
        return getAllPersons(gender).size();
    }

    /**
     * Метод считает общую сумму по возрасту пользователей
     */
    public static int getAllAgePersons() {
        int countAge = 0;
        for (Person person : allPersons.values()) {
            countAge += person.age;
        }
        return countAge;
    }

    /**
     * Метод считает общую сумму по возрасту пользователей, с учётом полового признака.
     */
    public static int getAllAgePersons(Gender gender) {
        int countAge = 0;
        for (Person person : getAllPersons(gender)) {
            countAge += person.age;
        }
        return countAge;
    }

    /**
     * Метод считает средний возраст персонов
     */
    public static int getAverageOfAllPersons() {
        return getAllAgePersons() / getHowManyPerson();
    }

    /**
     * Метод считает средний возраст персонов по половому признаку
     */
    public static int getAverageAgeOfAllPersons(Gender gender) {
        return getAllAgePersons(gender) / getHowManyPerson(gender);
    }

    /**
     * Пример тестирования с использованием System.out.println();
     *
     * @param args
     */
    public static void main(String[] args) {
        new Person("Злата", 8, Gender.FEMALE);
        new Person("Лада", 3, Gender.FEMALE);
        new Person("Ратибор", 1, Gender.MALE);

        System.out.println("Все пользователи:");
        Person.getAllPersons();

        System.out.println("Все пользователи мужчины:");
        Person.getAllPersons(Gender.MALE).forEach(System.out::println);

        System.out.println("Все пользователи женщины:");
        Person.getAllPersons(Gender.FEMALE).forEach(System.out::println);

        System.out.println("=================================================");
        System.out.println("        всех пользователей: " + Person.getHowManyPerson());
        System.out.println(" всех пользователей мужчин: " + Person.getHowManyPerson(Gender.MALE));
        System.out.println(" всех пользователей женщин: " + Person.getHowManyPerson(Gender.FEMALE));
        System.out.println("==================================================");
        System.out.println("        общий возраст всех пользователей: " + Person.getAllAgePersons());
        System.out.println(" общий возраст всех пользователей мужчин: " + Person.getAllAgePersons(Gender.MALE));
        System.out.println(" общий возраст всех пользователей женщин: " + Person.getAllAgePersons(Gender.FEMALE));
        System.out.println("==================================================");
        System.out.println("        средний возраст всех пользователей: " + Person.getAverageOfAllPersons());
        System.out.println(" средний возраст всех пользователей мужчин: " + Person.getAverageAgeOfAllPersons(Gender.MALE));
        System.out.println(" средний возраст всех пользователей женщин: " + Person.getAverageAgeOfAllPersons(Gender.FEMALE));

    }
}
