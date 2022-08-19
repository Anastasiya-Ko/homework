package main;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTest {
    private Person personOne;
    private Person personTwo;
    private Person personThree;

    private Person personNotAddOne;
    private Person personNotAddTwo;

    @Before
    public void setUp() throws Exception{
       personOne = new Person("Злата", 8, Gender.FEMALE);
       personTwo = new Person("Лада", 3, Gender.FEMALE);
       personThree = new Person("Ратибор", 1, Gender.MALE);

       personNotAddOne = new Person("Ира", 0, null);
       personNotAddTwo = new Person(null, 0, null);
    }

    @Test
    public void getAllPersons() {
       //создаём список expected и заполняем его данными нашего метода
        List<Person> expected = Person.getAllPersons();

        //создаём список actual и в него помещаем данные для сравнения (то, что мы предполагаем метод должен вернуть)
        List<Person> actual = new ArrayList<>();
        actual.add(personOne);
        actual.add(personTwo);
        actual.add(personThree);


        //Запускаем тест
        //в случае, если список expected и actual не будут равны - тест провален.
        //Метод будет сравнивать все поля объектов, даже пройдётся по полям родителей, если есть наследование.
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllPersons_NO_NULL() {
        //добавим проверку на null
        List<Person> expected  = Person.getAllPersons();
        Assert.assertNotNull(expected);
    }

    @Test
    public void getAllPersons_MALE() {
        List<Person> expected  = Person.getAllPersons(Gender.MALE);
        List<Person> actual = new ArrayList<>();
        actual.add(personThree);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllPersons_NO_NULL_MALE() {
        //добавим проверку на null
        List<Person> expected  = Person.getAllPersons(Gender.MALE);
        Assert.assertNotNull(expected);
    }

    @Test
    public void getAllPersons_FEMALE() {
        List<Person> expected  = Person.getAllPersons(Gender.FEMALE);
        List<Person> actual = new ArrayList<>();
        actual.add(personOne);
        actual.add(personTwo);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllPersons_NO_NULL_FEMALE() {
        //добавим проверку на null
        List<Person> expected  = Person.getAllPersons(Gender.FEMALE);
        Assert.assertNotNull(expected);
    }

    @Test
    public void getHowManyPerson() {
        int expected = Person.getHowManyPerson();

        int actual = 3;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHowManyPerson_MALE() {
        int expected = Person.getHowManyPerson(Gender.MALE);

        int actual = 1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHowManyPerson_FEMALE() {
        int expected = Person.getHowManyPerson(Gender.FEMALE);

        int actual = 2;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgePersons() {
        int expected = Person.getAllAgePersons();

        int actual = 8 + 3 + 1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgePersons_MALE() {
        int expected = Person.getAllAgePersons(Gender.MALE);

        int actual = 1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllAgePersons_FEMALE() {
        int expected = Person.getAllAgePersons(Gender.FEMALE);

        int actual = 8 + 3;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void newPerson_EMPTY_NAME(){
        for (Person person : Person.getAllPersons()) {
            if (person.getName() != null && person.getName().isEmpty()) {
                Assert.fail("Попытка создания пользователя с пустым именем");
            }
        }
    }

    @Test
    public void newPerson_AGE_ZERO(){
        for (Person person : Person.getAllPersons()){

            if(person.getAge() <= 0) {
                Assert.fail("Попытка создания пользователя с нулевым или отрицательным возрастом");
            }
        }
    }

    @Test
    public void setPerson_GENDER_NO_NULL(){
        for (Person person : Person.getAllPersons()){
            if(person.getGender() == null) {
                Assert.fail("Попытка создания пользователя с указание пола = null");
            }
         }
    }

    @After
    public void print (){
        System.out.println("Тесты пройдены :)");
    }
}