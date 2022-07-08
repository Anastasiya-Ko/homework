package code.listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

/**
 * Класс, следящий за правками, которые можно отменить или вернуть
 */
public class UndoListener implements UndoableEditListener {

    /**
     * Менеджер отмены - позволяет вернуть или отменить действие
     */
    private UndoManager undoManager;

    public UndoListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    /**
     * Метод, получает правку из переданного события и добавляет её в undoManager
     * @param e правка (действие, которое мы хотим отменить или вернуть)
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());

    }
}
