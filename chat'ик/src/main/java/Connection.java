package main.java;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;


/* Класс соединения между клиентом и сервером. Будет выполнять роль обёртки над классом java.net.Socket.
Обёртка будет сериализовать и десериализовать(читать) объекты типа Message в сокет.
 */
public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private SocketAddress socketAddress;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    // Метод для записи (сериализации) сообщений в поток
    public void send(Message message) throws IOException {
        synchronized (objectOutputStream) {
            objectOutputStream.writeObject(message);
        }
    }

    // Метод для чтения (десериализации) сообщений из потока
    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (objectInputStream){
            Message message = (Message) objectInputStream.readObject();
            return message;
        }
    }

    //Метод для получения удалённого адреса сокета
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    //Метод, закрывающий все ресурсы класса
    public void close() throws IOException{
        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
