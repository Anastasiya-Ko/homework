package code.view;

import code.controller.Controller;
import code.model.ModelData;
import code.bean.User;
// отображение всех пользователей в консоль
public class UsersView implements View{

    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {
        System.out.println("All " + (modelData.isDisplayDeletedUserList() ? "deleted " : "") + "users:");

        for(User user: modelData.getUsers()){
            System.out.println("\t" + user);
        }

        System.out.println("============================================================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller=controller;
    }

    //эмулирует событие клиента
    public void fireEventShowAllUsers(){
        controller.onShowAllUsers();
    }
    public void fireEventShowDeletedUsers(){
        controller.onShowAllDeletedUsers();
    }
    public void fireEventOpenUserEditForm(long id){
        controller.onOpenUserEditForm(id);
    }

    }
