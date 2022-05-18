package main.java.client;

import java.io.IOException;

public class ClientGuiController extends Client{
    private ClientGuiModel model = new ClientGuiModel();
    private ClientGuiView view = new ClientGuiView(this);

    @Override
    protected String getServerAddress() throws IOException {
        return view.getServerAddress();
    }

    @Override
    protected int getServerPort() {
        return view.getServerPort();
    }

    @Override
    protected String getUserName() {
        return view.getUserName();
    }

    @Override
    protected void sendTextMessage(String text) {
        super.sendTextMessage(text);
    }

    @Override
    protected SocketTread getSocketTread() {
        return new GuiSocketThread();
    }

    @Override
    public void run() {
        SocketTread socketTread = getSocketTread();
        //нет необходимости запускать SocketTread в отдельном потоке
        socketTread.run();
    }

    public ClientGuiModel getModel(){
        return model;
    }

    public static void main(String[] args) {
        ClientGuiController clientGuiController = new ClientGuiController();
        clientGuiController.run();
    }

    public class GuiSocketThread extends SocketTread{
        @Override
        protected void processIncomingMessage(String message) {
            //выводим текст сообщения
           model.setNewMessage(message);
           view.refreshMessages();
        }

        @Override
        protected void informAboutAddingNewUser(String userName) {
            //выводим информацию о добавлении нового юзера
            model.addUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void informAboutDeletingNewUser(String userName) {
            //выводим актуальный список юзеров
            model.deleteUser(userName);
            view.refreshUsers();
        }

        @Override
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            view.notifyConnectionStatusChanged(clientConnected);
        }
    }
}
