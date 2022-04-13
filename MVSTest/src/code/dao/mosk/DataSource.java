package code.dao.mosk;

import code.bean.User;
import java.util.LinkedList;
import java.util.List;

// DataSource реализован в виде синглтона
public class DataSource {

    private static DataSource ourInstance = new DataSource();

    public static DataSource getInstance() {
        return ourInstance;
    }

    private DataSource() {
    }

    private List<User> users = new LinkedList<User>() {{
        add(new User("Александр", 123l, 1));
        add(new User("Анастасия", 124l, 2));
        add(new User("Злата", 125l, 3));
        add(new User("Лада", 126l, 3));
        add(new User("Нежданчик", 127l, 3));
        add(new User("Ратибор", 129l, 3));
        add(new User("Удалец", 128l, 4));
        add(new User());
    }};

    private long maxUserId = 129l;

    public List<User> getUsers() {
        return users;
    }

    public User createOrUpdate(User newUser) {
        if (newUser == User.NULL_USER)
            return User.NULL_USER;

        //new User
        if (newUser.getId() == 0)
            return createNewUser(newUser);
        else
            return updateUser(newUser);
    }

    private User createNewUser(User newUser) {
        User clone = newUser.clone(++maxUserId);
        users.add(clone);
        return clone;
    }

    private User updateUser(User newUser) {
        for (User user : users) {
            if (user.getId() == newUser.getId()) {
                user.setName(newUser.getName());
                user.setLevel(newUser.getLevel());
                return user;
            }
        }
        //если бы мы не нашли такого пользователя
        return User.NULL_USER;
    }
}
