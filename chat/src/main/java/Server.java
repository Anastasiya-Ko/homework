package main.java;

import main.java.Connection;
import main.java.ConsoleHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/* Основной класс Сервера
Сервер должен поддерживать множество соединения с разными клиентами одновременно. Алгоритм:
-Сервер создаёт сокетное соединение;
-В цикле ожидает, когда какой0то клиент подключится к сокету;
-Создаёт новый поток обработчика Handler, в котором будет происходить обмен сообщениями с клиентом;
-Ожидает следующее соединение.

 */
public class Server {

    public static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Введите порт сервера:");
        int port = ConsoleHelper.readInt();

        try (ServerSocket serverSocket = new ServerSocket(port)){
            ConsoleHelper.writeMessage("Чат сервера запущен");
            while (true){
                //Ожидаем входящее соединение и запускаем отдельный поток при его принятии
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (Exception e){
            ConsoleHelper.writeMessage("Произошла ошибка при запуске или работе сервера");
        }

    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        //Главный метод класса Handler, который вызывает все вспомогательные методы, написанные ранее.
        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с " + socket.getRemoteSocketAddress());
            String userName = null;
            try (Connection connection = new Connection(socket)) {
                // Сообщаем всем участникам, что присоединился новый участник
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));

                //Сообщаем новому участнику, о существующих участниках
                notifyUsers(connection, userName);

                //Обрабатываем сообщения пользователей
                serverMainLoop(connection, userName);

            } catch(IOException | ClassNotFoundException e){
                ConsoleHelper.writeMessage("Ошибка при обмене данными с " + socket.getRemoteSocketAddress());
        }
            if (userName != null){
                connectionMap.remove(userName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
            }
            ConsoleHelper.writeMessage("Соединение с " + socket.getRemoteSocketAddress() + " закрыто.");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                //запрос имени пользователя
                connection.send(new Message(MessageType.NAME_REQUEST));

                //Пока тип сообщения полученного в ответе не будет равен MessageType.USER_NAME, запрос имени должен быть выполнен снова
                Message message = connection.receive();
                if (message.getMessageType() != MessageType.USER_NAME) {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу.");
                    continue;
                }
                String userName = message.getData();

                //Если пришло пустое имя, то выполнить запрос имени снова
                // Получение имени пользователя из ответа и проверка его на 1) пустоту
                if (userName.isEmpty()) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с пустым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }

                //Если пришло имя, уже содержащееся в connectionMap, то выполнить запрос снова
                // 2) на отсутствие пользователя с таким именем
                if (connectionMap.containsKey(userName)) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с уже используемым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
                // После успешных проверок, добавление новой пары (имя, соединение)
                connectionMap.put(userName, connection);

                //Отправка клиенту команды, что его сообщение принято
                connection.send(new Message(MessageType.NAME_ACCEPTED));

                return userName;
            }
        }

        //где connection - соединение с участником, которому будем слать информацию,
        //userName - его имя.
        public void notifyUsers(Connection connection, String userName) throws IOException {
            for (String name : connectionMap.keySet()) {
                if (name.equals(userName))
                    continue;
                connection.send(new Message(MessageType.USER_ADDED, name));
            }
        }

        //главный цикл обработки сообщений сервером
        public void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getMessageType() == MessageType.TEXT) {
                    String data = message.getData();
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + data));
                } else {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + "Тип сообщения не соответствует протоколу.");
                }
            }
        }
    }
    public static void sendBroadcastMessage(Message message) {
        //Рассылаем сообщение по всем соединениям connectionMap
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Сообщение не отправлено" + connection.getRemoteSocketAddress());
            }
        }
    }

}
