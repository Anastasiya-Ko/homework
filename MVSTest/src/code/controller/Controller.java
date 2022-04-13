package code.controller;

import code.model.Model;
import code.view.EditUserView;
import code.view.UsersView;
//Класс, получающий запросы от клиента -> оповещает Модель -> Модель обновляет МодельДата
public class Controller
{
    private Model model;
    private UsersView usersView;
    private EditUserView editUserView;

    public void setEditUserView(EditUserView editUserView) {
        this.editUserView = editUserView;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setUsersView(UsersView usersView) {
        this.usersView = usersView;
    }

    //Метод, обращающийся к Модели и инициализирующий загрузку пользователей.
    public void onShowAllUsers(){
        model.loadUsers();
        usersView.refresh(model.getModelData());
    }

    //Метод, обращающийся к Модели и инициализирующий удаление пользователей.
    public void onShowAllDeletedUsers(){
        model.loadDeletedUsers();
        usersView.refresh(model.getModelData());
    }

    public void onOpenUserEditForm(long userId){
        model.loadUserById(userId);
        editUserView.refresh(model.getModelData());
    }

    public void onUserDelete(long id){
        model.deletedUserById(id);
        editUserView.refresh(model.getModelData());
    }

    public void onUserChange(String name, long id, int level){
        model.changeUserData(name, id, level);
        usersView.refresh(model.getModelData());
    }

}
