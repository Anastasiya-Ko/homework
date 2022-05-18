package main.java.client;


import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

//Клиент для чата с графическим интерфейсом
public class ClientGuiModel {
    private final Set<String> allUserNames = new TreeSet<>(); //в этом множестве хранится список всех участников чата
    private String newMessage; //здесь хранится новое сообщение, которое получил клиент

    //Этот геттер запрещает модифицировать возвращаемое множество, с помощью метода класса Collections
    public Set<String> getAllUserNames() {
        return Collections.unmodifiableSet(allUserNames);
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    //Метод добавляет имя участника во Множество, хранящее всех участников
    public void addUser(String newUserName){
        allUserNames.add(newUserName);
    }

    //Метод, удаляющий имя участника из множества
    public void deleteUser(String userName){
        allUserNames.remove(userName);
    }
}
