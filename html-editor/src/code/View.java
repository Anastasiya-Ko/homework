package code;

import code.Controller;
import code.listeners.FrameListener;
import code.listeners.TabbedPaneChangeListener;
import code.listeners.UndoListener;

import javax.swing.*;  //Библиотека для создания GUI (графического интерфейса для программ) на языке Java
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;

    /**
     * Позволяет Отменить или Повторить соответствующие изменения
     */
    private UndoManager undoManager = new UndoManager();

    /**
     * Слушатель Отмены
     */
    private UndoListener undoListener = new UndoListener(undoManager);

    /**
     * Панель с двумя вкладками
     */
    private JTabbedPane tabbedPane = new JTabbedPane();

    /**
     * Компонент для визуального редактирования HTML (размещён на первой вкладке).
     * Это текстовая панель, для отрисовки HTML страницы (на ней можно формировать и редактировать текст страницы)
     */
    private JTextPane htmlTextPane = new JTextPane();

    /**
     * Компонент для редактирования HTML в виде текста (размещён на второй вкладке). Он будет отображать код HTML (теги и их содержимое).
     * В нём также можно менять текст страницы, добавлять и удалять различные теги.
     */
    private JEditorPane plainTextPane = new JEditorPane();

    /**
     * Конструктор устанавливает внешний вид и поведение(Look and feel), как установлено в классе UIManager
     */
    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e); //логирование исключений
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    /**
     * Метод будет вызываться при выборе пунктов меню, у которых Представление указано в виде Слушателя событий.
     * @param actionEvent событие
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {  //получение из События команды(это будет обычная строка)
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                controller.exit();
                break;
            case "О программе":
                showAbout();
        }
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    /**
     * Метод, отвечающий за инициализацию контроллера
     */
    public void init() {
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }

    /**
     * Метод, отвечающий за инициализацию меню
     */
    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar(); //создание панели меню

        //инициализация пунктов меню:
        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);

        getContentPane().add(jMenuBar, BorderLayout.NORTH); //добавляет в верхнюю часть панели контента текущего фрейма(кадр, рамка) нашу панель меню.


    }

    /**
     * Метод, отвечающий за инициализацию панели редактора
     */
    public void initEditor(){
        htmlTextPane.setContentType("text/html"); //установка типа контента для компонента htmlTextPane
        JScrollPane htmlScrollPane = new JScrollPane(htmlTextPane); //создание нового компонента JScrollPane через конструктор, принимающий htmlTextPane
        tabbedPane.addTab("HTML", htmlScrollPane); //добавление вкладки в панель tabbedPane

        JScrollPane plainScrollPane = new JScrollPane(plainTextPane); //создание нового компонента JScrollPane через конструктор, принимающий plainTextPane
        tabbedPane.addTab("Текст", plainScrollPane); //добавление вкладки в панель tabbedPane


        tabbedPane.setPreferredSize(new Dimension(300, 300)); //Устанавливает предпочтительный размер панели.
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this)); //устанавливает объект класса TabbedPaneChangeListener в качестве Слушателя изменений в tabbedPane
        getContentPane().add(tabbedPane, BorderLayout.CENTER); //добавляет по центру панели контента текущего фрейма(кадр, рамка) нашу панель с вкладками.

    }

    /**
     * Метод, инициализирующий графический интерфейс
     */
    public void initGui(){
        initMenuBar();
        initEditor();
        pack(); //Изменяет размер этого окна в соответствии с предпочтительным размером и расположением его подкомпонентов (из JFrame)
    }

    /**
     * Метод вызывается при смене выбранной вкладки
     */
    public void selectedTabChanged(){
        switch (tabbedPane.getSelectedIndex()) {
            case 0:                                                  //если выбрана вкладка с индексом 0 (html вкладка)
                controller.setPlainText(plainTextPane.getText());    //то получаем текст и устанавливаем его в контроллер
                break;
            case 1:                                                  //если выбрана вкладка с индексом 1 (вкладка с html текстом)
                plainTextPane.setText(controller.getPlainText());    //то получаем текст у Контроллера и устанавливаем его в панель plainTextPane
                break;
        }
        resetUndo();

        /*if (tabbedPane.getSelectedIndex() == 0) {  //если выбрана вкладка с индексом 0 (html вкладка)
            controller.setPlainText(plainTextPane.getText()); //то получаем текст и устанавливаем его в контроллер
        } if (tabbedPane.getSelectedIndex() == 1) { //если выбрана вкладка с индексом 1 (вкладка с html текстом)
            plainTextPane.setText(controller.getPlainText()); //то получаем текст у Контроллера и устанавливаем его в панель plainTextPane
        }
        resetUndo();*/
    }

    /**
     *
     */
    public void exit(){
        controller.exit();
    }

    /**
     * Возвращает значение false, если изменения НЕ могут быть отменены.
     * @return
     */
    public boolean canUndo(){
        return undoManager.canUndo();
    }

    /**
     * Возвращает значение false, если изменения НЕ могут быть повторены.
     * @return
     */
    public boolean canRedo(){
        return undoManager.canRedo();
    }

    /**
     * Метод отменяет последнее действие
     */
    public void undo(){
        try { undoManager.undo();
        } catch (CannotUndoException e) {
            ExceptionHandler.log(e);
        }
    }

    /**
     * Метод возвращает ранее отменёное действие (делает повтор)
     */
    public void redo(){
        try { undoManager.redo();
        } catch (CannotRedoException e) {
            ExceptionHandler.log(e);
        }
    }

    /**
     * Метод сбрасывает все поправки в менеджере undoManager
     */
    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    /**
     * Метод должен возвращать true, если выбрана вкладка, отображающая html (её индекс 0)
     * @return
     */
    public boolean isHtmlTabSelected(){
        return tabbedPane.getSelectedIndex() == 0;
    }

    /**
     * Метод выбирает html вкладку, сбрасывает все правки
     */
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0); //выбор html вкладки(переключение на неё)
        resetUndo(); //сброс всех правок, реализованных ранее
    }

    /**
     * Метод, получающий документ(Модель) у Контроллера и устанавливающий его в панель редактирования
     */
    public void update(){
        htmlTextPane.setDocument(controller.getHtmlDocument()); //получение Модели у Контроллера и установка его в Панель редактирования
    }

    /**
     * Метод, показывающий диалоговое окно с инфо о программе
     */
    public void showAbout(){
        JOptionPane.showMessageDialog(this, "HTML редактор", "О программе", JOptionPane.INFORMATION_MESSAGE);
    }
}
