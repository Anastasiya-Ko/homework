package code;

import code.listeners.UndoListener;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    /**
     * Представление
     */
    private View view;

    /**
     * Модель
     */
    private HTMLDocument htmlDocument;

    /**
     * Текущий файл — файл, который открыт сейчас на нашем редакторе
     */
    private File currentFile;

    public HTMLDocument getHtmlDocument() {
        return htmlDocument;
    }

    public Controller(View view) {
        this.view = view;
    }

    /**
     * Метод, отвечающий за инициализацию контроллера
     */
    public void init(){
        createNewDocument();
    }

    /**
     * Метод, вызывающий статический метод exit у класса System, с параметром 0
     */
    public void exit(){
        System.exit(0);
    }

    /**
     * Метод, сбрасывающий текущий документ
     */
    public void resetDocument(){
        UndoListener undoListener = view.getUndoListener();
        if (htmlDocument != null) {
            htmlDocument.removeUndoableEditListener(undoListener); //Удаление у текущего документа Слушателя правок
        }
        htmlDocument = (HTMLDocument) new HTMLEditorKit().createDefaultDocument(); //создание нового Документа по умолчанию
        htmlDocument.addUndoableEditListener(undoListener); //добавление новому документу Слушателя правок
        view.update();
    }

    /**
     * Метод записывает переданный текст с html тегами в документ htmlDocument
     * @param text переданный текст
     */
    public void setPlainText(String text){
        resetDocument(); //сброс документа
        StringReader stringReader = new StringReader(text); //создание нового Ридера на базе переданного текста
        try {
            new HTMLEditorKit().read(stringReader, htmlDocument, 0); //вычитает данные из ридера в Документ
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e); //логирование исключения
        }

    }

    /**
     * Получает текст из Документа со всеми тегами
     * @return
     */
    public String getPlainText(){
        StringWriter stringWriter = new StringWriter();
        try {
            HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
            htmlEditorKit.write(stringWriter, htmlDocument, 0, htmlDocument.getLength()); //перепись содержимого Документа в созданный объект
        } catch (IOException | BadLocationException e) {
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }

    /**
     * Создание нового документа
     */
    public void createNewDocument(){
        view.selectHtmlTab(); //выбирает html вкладку у Представления
        resetDocument(); //сбрасывает текущий документ
        view.setTitle("Тестовый HTML редактор"); //установка нового заголовка окна
        currentFile = null; //обнуление текущего файла

    }

    public void openDocument() {
        view.selectHtmlTab(); //переключает Представление на html вкладку
        JFileChooser jFileChooser = new JFileChooser(); //создание нового объекта для выбора файла
        jFileChooser.setFileFilter(new HTMLFileFilter()); //установка фильтра для него
        if (jFileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) { //если пользователь подтвердил выбор файла
            currentFile = jFileChooser.getSelectedFile(); //то сохранить выбранный файл в currentFile
            resetDocument(); //сбрасывает текущий документ
            view.setTitle(currentFile.getName()); //установить имя файла в качестве заголовка окна представления

            try (FileReader fileReader = new FileReader(currentFile)){
                new HTMLEditorKit().read(fileReader, htmlDocument, 0); //вычитает данные из ридера в Документ
            } catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e); //логирование исключения
            }
            view.resetUndo(); //сброс правок
        }
    }

    /**
     * Метод для сохранения открытого файла
     */
    public void saveDocument(){
        view.selectHtmlTab(); //переключает Представление на html вкладку
        if(currentFile != null) { //если текущий файл не нулл
            try (FileWriter fileWriter = new FileWriter(currentFile)) { //создание FileWriter(писатель файлов) на базе currentFile
                new HTMLEditorKit().write(fileWriter, htmlDocument, 0, htmlDocument.getLength()); //перепись данных из документа htmlDocument в объект FileWriter
            } catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e); //логирование исключений
            }
        } else
            saveDocumentAs(); //иначе вызвать Сохранить как..
        }

    /**
     * Метод для сохранения файла под новым именем
     */
    public void saveDocumentAs(){
        view.selectHtmlTab(); //переключает Представление на html вкладку
        JFileChooser jFileChooser = new JFileChooser(); //создание нового объекта для выбора файла
        jFileChooser.setFileFilter(new HTMLFileFilter()); //установка фильтра для него
        if (jFileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) { //если пользователь подтвердил выбор файла
            currentFile = jFileChooser.getSelectedFile(); //то сохранить выбранный файл в currentFile
            view.setTitle(currentFile.getName()); //установить имя файла в качестве заголовка окна представления

            try (FileWriter fileWriter = new FileWriter(currentFile)) { //создание FileWriter(писатель файлов) на базе currentFile
                new HTMLEditorKit().write(fileWriter, htmlDocument, 0, htmlDocument.getLength()); //перепись данных из документа htmlDocument в объект FileWriter
            } catch (IOException | BadLocationException e) {
                ExceptionHandler.log(e); //логирование исключений
            }
        }
    }



    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);

        view.setController(controller); //установление у представления контроллер
        view.init(); // инициализация представления
        controller.init(); //инициализация контроллера

    }
}
