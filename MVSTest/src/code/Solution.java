package code;

import code.controller.Controller;
import code.model.FakeModel;
import code.model.MainModel;
import code.model.Model;
import code.view.EditUserView;
import code.view.UsersView;

//Эмулятор пользователя
public class Solution {
    public static void main(String[] args) {

    Model model = new MainModel();
    UsersView usersView = new UsersView();
    EditUserView editUserView = new EditUserView();
    Controller controller = new Controller();

    usersView.setController(controller);
    editUserView.setController(controller);

    controller.setModel(model);
    controller.setUsersView(usersView);
    controller.setEditUserView(editUserView);

    //эмулирует пользовательские события
    usersView.fireEventShowAllUsers();
    usersView.fireEventOpenUserEditForm(123l);
    editUserView.fireEventUserDeleted(128l);
    editUserView.fireEventUserChanged("Нежданчик", 127l, 3 );
    usersView.fireEventShowAllUsers();
    usersView.fireEventShowDeletedUsers();


    }
}
