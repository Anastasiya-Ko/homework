package main.java.client;

import java.io.IOException;
import java.net.Socket;

import main.java.Connection;
import main.java.ConsoleHelper;
import main.java.MessageType;
import main.java.Message;


//Консольный клиент, который умеет подключаться к серверу и обмениваться сообщениями с другими участниками.
public class Client {

    protected Connection connection;
    private volatile boolean clientConnected;

    //Метод, запрашивающий ввод адреса сервера у пользователя и возвращающий введённое значение
    protected String getServerAddress() throws IOException {
        ConsoleHelper.writeMessage("Введите адрес сервера");
        return ConsoleHelper.readString();
    }

    //Метод, запрашивающий ввод порта сервера и возвращающий его
    protected int getServerPort(){
        ConsoleHelper.writeMessage("Введите порт сервера");
        return ConsoleHelper.readInt();
    }

    //Метод, запрашивающий и возвращающий имя клиента
    protected String getUserName() {
        ConsoleHelper.writeMessage("Введите ваше имя");
        return ConsoleHelper.readString();
    }




    //Отвечает за поток, устанавливающий сокетное соединение и читающий сообщения сервера.
    public class SocketTread extends Thread {

        //Метод, реализующий главный цикл обработки сообщений сервера
        @Override
        public void run() {
            try {
                //Создаём соединение с сервером
                connection = new Connection(new Socket(getServerAddress(), getServerPort()));

                clientHandshake();
                clientMainLoop();

            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }

        //Метод представляет клиента серверу
        protected void clientHandshake() throws IOException, ClassNotFoundException {
            //в цикле получает сообщения, используя соединение connection
            while (true) {
                Message message = connection.receive();


                //если тип полученного сообщения NAME_REQUEST:
                // + запросить ввод имени пользователя +
                // + создать новое сообщение с типом MessageType.USER_NAME и введённым именем+
                // + отправить сообщение серверу
                if (message.getMessageType() == MessageType.NAME_REQUEST) {          //сервер запросил имя пользователя
                    //запрашиваем ввод имени с консоли
                    String name = getUserName();
                    //отправляем имя на сервер
                    connection.send(new Message(MessageType.USER_NAME, name));
                }

                //если тип полученного сообщения NAME_ACCEPTED, значит сервер принял имя клиента -> сообщить главному потоку
                else if (message.getMessageType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    return;
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        //Метод получения сообщений от сервера
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            //цикл обработки сообщений сервера
            while (true) {
                Message message = connection.receive();

                if (message.getMessageType() == MessageType.TEXT) { //Сервер прислал сообщение с текстом
                    processIncomingMessage(message.getData());
                } else if (message.getMessageType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else if (message.getMessageType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType");
                }
            }
        }

        //Метод, выводящий message в консоль
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        //Метод, выводящий в консоль инфо, что участник с именем userName присоединился к чату
        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage("Пользователь " + userName + " присоединился к чату");
        }

        //Метод, выводит в консоль инфо, что пользователь userName покинул чат
        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage("Пользователь " + userName + " покинул чат");
        }

        //Метод:
        //1)устанавливает новое значение поля clientConnected внешнего объект Client, в соответствии с переданным параметром
        //2)оповещает(пробуждает ожидающий) основной поток класса Client
        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

    }



    //Метод, возвращающий новый объект класса SocketTread
    protected SocketTread getSocketTread() {
        return new SocketTread();
    }

    //Метод создаёт новое текстовое сообщение, используя переданный текст и отправляет его серверу через соединение connection.
    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));

        } catch (IOException e) {
            ConsoleHelper.writeMessage("Не удалось отправить сообщение");
            clientConnected = false;
        }
    }

    //Метод всегда возвращает true, т.к мы всегда отправляем текст, введённый в консоль.
    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    //Метод run:
    //-> создаёт вспомогательный поток SocketTread
    //-> ожидает пока поток установит соединение с сервером
    //-> считывает сообщения с консоли и отправляет их серверу
    public void run() {
        SocketTread socketTread = getSocketTread();   //Создаём новый поток
        socketTread.setDaemon(true);                  //Помечаем поток, как демон
        socketTread.start();

        //Заставляем текущий поток ожидать, пока он не получит нотификацию из другого потока
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента");
            return;
        }

        if (clientConnected)
            ConsoleHelper.writeMessage("Соединение установлено. Для выхода наберите команду 'exit'.");
        else
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента");

        //Пока не будет введена команда 'exit', считываем сообщения с консоли и отправляем их на сервер
        while (clientConnected) {
            String text = ConsoleHelper.readString();
            //sendTextMessage(text);
            if (text.equalsIgnoreCase("exit"))
                break;
            if (shouldSendTextFromConsole())
                sendTextMessage(text);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}

