package code.model;

import code.bean.User;
import code.model.service.UserService;
import code.model.service.UserServiceImpl;

import java.util.List;
// Реальный пользователь
// Рефакторинг — это переработка исходного кода программы, чтобы он стал более простым и понятным.
// Рефакторинг не меняет поведение программы, не исправляет ошибки и не добавляет новую функциональность.

public class MainModel implements Model {

    // использовать полезные сервисы
    private UserService userService = new UserServiceImpl();

    // использовать специальный объект для сохранения данных для рендеринга(отрисовки) во вью
    private ModelData modelData = new ModelData();


    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadUsers() {
        List<User> users = getAllUsers();
        // обновить данные модели
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(false);
    }

    @Override
    public void loadDeletedUsers() {
        List<User> users = userService.getAllDeletedUsers();
        // обновить данные модели
        modelData.setUsers(users);
        modelData.setDisplayDeletedUserList(true);
    }

    @Override
    public void loadUserById(long userId) {
        User user = userService.getUsersById(userId);
        modelData.setActiveUser(user);
    }

    @Override
    public void deletedUserById(long id) {
        userService.deleteUser(id);
        List<User> users = getAllUsers();
        // обновить данные модели
        modelData.setUsers(users);
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        userService.createOrUpdateUser(name, id, level);
        List<User> users = getAllUsers();
        modelData.setUsers(users);
    }

    private List<User> getAllUsers(){
        // Модель должна содержать всю бизнес-логику в методах
        List<User> allUsers = userService.getUsersBetweenLevels(1, 100);
        allUsers = userService.filterOnlyActiveUsers(allUsers);
        return allUsers;
    }
}
