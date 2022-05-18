package main.java;

import java.io.Serializable;

/* Класс, отвечающий за пересылаемые сообщения.
Т.к. сообщения буду создаваться в одной программе, а читаться в другой ->
нужно использовать м-м сериализации, для перевода класса в биты, и наоборот.
 */
public class Message implements Serializable {
    private final MessageType messageType; // тип сообщения
    private final String data; //данные сообщения

    public Message(MessageType messageType) {
        this.messageType = messageType;
        this.data = null;
    }

    public Message(MessageType messageType, String data) {
        this.messageType = messageType;
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getData() {
        return data;
    }
}
