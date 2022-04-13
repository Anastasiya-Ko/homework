package code.model;

import code.bean.User;
import java.util.ArrayList;
import java.util.List;

public class FakeModel implements Model{
    ModelData modelData = new ModelData();

    @Override
    public ModelData getModelData() {
        return null;
    }

    @Override
    public void loadUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("A", 1, 1));
        users.add(new User("B", 2, 2));

        modelData.setUsers(users);
            }

    @Override
    public void loadDeletedUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadUserById(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deletedUserById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        throw new UnsupportedOperationException();
    }
}
