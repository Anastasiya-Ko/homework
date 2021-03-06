package code;

import code.actions.*;
import code.listeners.TextEditMenuListener;
import code.listeners.UndoMenuListener;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Вспомогательный класс для инициализации и настройки меню
 */
public class MenuHelper {
    /**
     * Метод для добавления пункта меню
     * @param parent меню, в которое мы добавляем пункт
     * @param text текст, добавляемого пункта
     * @param actionListener слушатель действий добавляемого пункта меню
     * @return созданный новый пункт меню
     */
    public static JMenuItem addMenuItem (JMenu parent, String text, ActionListener actionListener) {
        JMenuItem jMenuItem = new JMenuItem(text); //создание нового пункта меню
        jMenuItem.addActionListener(actionListener); //установка Слушателя действий для нового пункта меню
        parent.add(jMenuItem); //добавление в parent созданный пункт меню(jMenuItem)

        return jMenuItem;
    }

    /**
     * Метод для добавления пункта меню
     * @param parent меню, в которое мы добавляем пункт
     * @param action действие, которое нужно выполнить при выборе пункта меню
     * @return созданный новый пункт меню
     */
    public static JMenuItem addMenuItem(JMenu parent, Action action){
        JMenuItem jMenuItem = new JMenuItem(action); //создание нового пункта меню
        parent.add(jMenuItem); //добавление в parent созданный пункт меню(jMenuItem)

        return jMenuItem;
    }

    /**
     * Метод для добавления нового пункта меню
     * @param parent меню, в которое мы добавляем пункт
     * @param text текст, добавляемого пункта
     * @param action действие, которое нужно выполнить при выборе пункта меню
     * @return созданный новый пункт меню
     */
    public static JMenuItem addMenuItem(JMenu parent, String text, Action action){
        JMenuItem jMenuItem = addMenuItem(parent, action); //создание нового пункта меню и выборе действия action при выборе метода
        parent.add(jMenuItem); //добавление в parent созданный пункт меню(jMenuItem)

        return jMenuItem;
    }

    /**
     * Метод для инициализации Меню помощи
     * @param view
     * @param menuBar
     */
    public static void initHelpMenu(View view, JMenuBar menuBar){
        JMenu helpMenu = new JMenu("Помощь");
        menuBar.add(helpMenu);

        addMenuItem(helpMenu, "О программе", view);
    }

    /**
     * Метод для инициализации Меню выбора шрифта
     * @param view
     * @param menuBar
     */
    public static void initFontMenu(View view, JMenuBar menuBar){
        JMenu fontMenu = new JMenu("Шрифт");
        menuBar.add(fontMenu);

        JMenu fontTypeMenu = new JMenu("Шрифт");
        fontMenu.add(fontTypeMenu);

        String[] fontTypes = {Font.SANS_SERIF, Font.SERIF, Font.MONOSPACED, Font.DIALOG, Font.DIALOG_INPUT};
        for (String fontType : fontTypes) {
            addMenuItem(fontTypeMenu, fontType, new StyledEditorKit.FontFamilyAction(fontType, fontType));
        }

        JMenu fontSizeMenu = new JMenu("Размер шрифта");
        fontMenu.add(fontSizeMenu);

        String[] fontSizes = {"6", "8", "10", "12", "14", "16", "20", "24", "32", "36", "48", "72", "102"};
        for (String fontSize : fontSizes) {
            addMenuItem(fontSizeMenu, fontSize, new StyledEditorKit.FontSizeAction(fontSize, Integer.parseInt(fontSize)));
        }

        fontMenu.addMenuListener(new TextEditMenuListener(view));
    }

    /**
     * Метод для инициализации Меню выбора цвета
     * @param view
     * @param menuBar
     */
    public static void initColorMenu(View view, JMenuBar menuBar){
        JMenu colorMenu = new JMenu("Цвет");
        menuBar.add(colorMenu);

        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Красный", Color.red));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Оранжевый", Color.orange));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Желтый", Color.yellow));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Зеленый", Color.green));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Синий", Color.blue));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Голубой", Color.cyan));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Пурпурный", Color.magenta));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Черный", Color.black));

        colorMenu.addMenuListener(new TextEditMenuListener(view));
    }

    /**
     * Метод для инициализации Меню выравнивания
     * @param view
     * @param menuBar
     */
    public  static void initAlignMenu(View view, JMenuBar menuBar){
        JMenu alignMenu = new JMenu("Выравнивание");
        menuBar.add(alignMenu);

        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По левому краю", StyleConstants.ALIGN_LEFT));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По центру", StyleConstants.ALIGN_CENTER));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По правому краю", StyleConstants.ALIGN_RIGHT));

        alignMenu.addMenuListener(new TextEditMenuListener(view));
    }

    /**
     * Метод для инициализации Меню выбора стиля текста
     * @param view
     * @param menuBar
     */
    public static void initStyleMenu(View view, JMenuBar menuBar){
        JMenu styleMenu = new JMenu("Стиль");
        menuBar.add(styleMenu);
        addMenuItem(styleMenu, "Полужирный", new StyledEditorKit.BoldAction());
        addMenuItem(styleMenu, "Подчеркнутый", new StyledEditorKit.UnderlineAction());
        addMenuItem(styleMenu, "Курсив", new StyledEditorKit.ItalicAction());

        styleMenu.addSeparator();

        addMenuItem(styleMenu, "Подстрочный знак", new SubscriptAction());
        addMenuItem(styleMenu, "Надстрочный знак", new SuperscriptAction());
        addMenuItem(styleMenu, "Зачеркнутый", new StrikeThroughAction());

        styleMenu.addMenuListener(new TextEditMenuListener(view));
    }

    /**
     * Метод для инициализации Меню редактирования текста
     * @param view
     * @param menuBar
     */
    public static void initEditMenu(View view, JMenuBar menuBar){
        JMenu editMenu = new JMenu("Редактировать");
        menuBar.add(editMenu);

        JMenuItem undoItem = addMenuItem(editMenu, "Отменить", new UndoAction(view));
        JMenuItem redoItem = addMenuItem(editMenu, "Вернуть", new RedoAction(view));
        addMenuItem(editMenu, "Вырезать", new DefaultEditorKit.CutAction());
        addMenuItem(editMenu, "Копировать", new DefaultEditorKit.CopyAction());
        addMenuItem(editMenu, "Вставить", new DefaultEditorKit.PasteAction());

        editMenu.addMenuListener(new UndoMenuListener(view, undoItem, redoItem));
    }

    /**
     * Метод для инициализации Меню файл
     * @param view
     * @param menuBar
     */
    public static void initFileMenu(View view, JMenuBar menuBar){
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        addMenuItem(fileMenu, "Новый", view);
        addMenuItem(fileMenu, "Открыть", view);
        addMenuItem(fileMenu, "Сохранить", view);
        addMenuItem(fileMenu, "Сохранить как...", view);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Выход", view);
    }
}
