package code.model;

//Модель - объект, который содержит в себе данные, для отображения инфо на клиенте. Также содержит все неободимые ссылки на все сервисы.
public interface Model {
    ModelData getModelData();

    void loadUsers();
    void loadDeletedUsers();
    void loadUserById(long userId);
    void deletedUserById(long id);
    void changeUserData(String name, long id, int level);
}
