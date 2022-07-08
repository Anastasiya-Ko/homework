package code.actions;

import code.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс для отмены действия(action)
 */
public class UndoAction extends AbstractAction {
    private View view;

    public UndoAction(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        view.undo();
    }
}
