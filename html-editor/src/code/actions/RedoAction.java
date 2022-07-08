package code.actions;

import code.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс для возврата действия
 */
public class RedoAction extends AbstractAction {
    private  View view;

    public RedoAction(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        view.redo();
    }
}
