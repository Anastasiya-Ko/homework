package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class NastyaSet <E>
        extends AbstractSet <E>
        implements Set<E>, Cloneable, Serializable {

    /**
     * Заглушка
     */
    private Object PRESENT = new Object();

    /**
     * Модификатором transient помечают поля, которые не должны быть сериализованны.
     * Список ключей будет нашим сетом
     * Значением будет наша заглушка
     * @return
     */
    private transient HashMap <E, Object> map;

    //Коллекции обычно имеют несколько конструкторов


    public NastyaSet() {
        this.map = new HashMap<>();
    }

    public NastyaSet(Collection <? extends E> collection) {
        //для вычисления Capacity:
        //максимальное из 16(начальная ёмкость) и округлённое в большую сторону значения.
        //0.75 - коэффициент загрузки (load factor)
        this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
        addAll(collection); //добавление всех элементов из collection в нашу коллекцию.
    }


    /**
     * Итератор ключей
     * Элементы возвращаются в произвольном порядке
     * @return множество ключей
     */
    @Override
    public Iterator <E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    /**
     * Метод для проверки количества ключей в мапе
     * @return количество ключей
     */
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Метод возвращает true, если map содержит анализируемый элемент
     */
    @Override
    public boolean contains(Object o) {
        return map.containsValue(o);
    }

    /**
     * Метод удаляет из map, полученный в качестве параметра, элемент.
     */
    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

    /**
     * Поверхностное клонирование.
     * @return
     */
    @Override
    public Object clone() {
        try {
            NastyaSet<E> newSet = (NastyaSet<E>) super.clone(); //клонирование множества
            newSet.map = (HashMap<E, Object>) map.clone(); //клонирование мапы
            return newSet;

        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    /**
     * Сериализация объектов мапы
     * @param objectOutputStream
     * @throws java.io.IOException
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws java.io.IOException {
        //записываем любую сериализацию
        objectOutputStream.defaultWriteObject();

        //записываем сериализацию capacity и loadFactor у объекта мапы
        objectOutputStream.writeInt(HashMapReflectionHelper.<Integer>callHiddenMethod(map, "capacity"));
        objectOutputStream.writeFloat(HashMapReflectionHelper.<Float>callHiddenMethod(map, "loadFactor"));

        //записываем сериализацию размера мапы
        objectOutputStream.writeInt(map.size());

        //записываем сериализацию всех методов в правильном порядке
        for(E e: map.keySet()){
            objectOutputStream.writeObject(e);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws java.io.IOException, ClassNotFoundException {
        //считываем любую десериализацию
        objectInputStream.defaultReadObject();

        //считываем capacity и loadFactor мапы и создаём её резервную копию
        int capacity = objectInputStream.readInt();
        float loadFactor = objectInputStream.readFloat();
        map = new HashMap<>(capacity, loadFactor);

        //считываем размер мапы
        int size = objectInputStream.readInt();

        //считываем все элементы в правильном порядке
        for (int i = 0; i < size; i++) {
            E e = (E) objectInputStream.readObject();
            map.put(e, PRESENT);
        }

    }
}
