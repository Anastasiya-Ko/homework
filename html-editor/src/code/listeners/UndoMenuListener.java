package code.listeners;

import code.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Этот Слушатель следит за Меню(точнее за моментом, когда Меню редактирования будет выбрано пользователем).
 * В этот момент он будет запрашивать у Представления можем ли мы сейчас отменить или вернуть какое-то действие,
 * И в зависимости от этого делать доступным пункты меню ОТМЕНИТЬ и ВЕРНУТЬ
 */
public class UndoMenuListener implements MenuListener {

    private View view;

    /**
     * Пункт меню - ОТМЕНИТЬ
     */
    private JMenuItem undoMenuItem;

    /**
     * Пункт меню - ВЕРНУТЬ
     */
    private JMenuItem redoMenuItem;

    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    /**
     * Метод Меню Выбрано - вызывается перед показом меню
     * @param menuEvent
     */
    @Override
    public void menuSelected(MenuEvent menuEvent) {
        undoMenuItem.setEnabled(view.canUndo()); //запрос у Представления можем ли мы ОТМЕНИТЬ действие +
                                                 // делает доступным/недоступным пункт меню undoMenuItem, в зависимости от того, что нам вернуло Представление

        redoMenuItem.setEnabled(view.canRedo()); //запрос у Представления можем ли мы ВЕРНУТЬ действие +
                                                 // делает доступным/недоступным пункт меню redoMenuItem, в зависимости от того, что нам вернуло Представление
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
