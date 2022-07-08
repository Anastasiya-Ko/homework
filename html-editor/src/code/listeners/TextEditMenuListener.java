package code.listeners;

import code.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

/**
 * Пункты меню: стиль, шрифт, цвет и т.д. должны быть доступны только в первой вкладке редактора
 */
public class TextEditMenuListener implements MenuListener {
    private View view;

    public TextEditMenuListener(View view) {
        this.view = view;
    }

    @Override
    public void menuSelected(MenuEvent menuEvent) {
        JMenu source = (JMenu) menuEvent.getSource(); //получает объект из переданного параметра, над которым было совершено действие
        Component[] menuComponents = source.getMenuComponents(); //получает список компонентов у полученного меню
        for (Component component: menuComponents){
            component.setEnabled(view.isHtmlTabSelected());
        }
    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {

    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {

    }
}
