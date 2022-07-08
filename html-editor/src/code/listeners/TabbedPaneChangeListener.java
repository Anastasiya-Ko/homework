package code.listeners;

import code.View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
Слушатель Listener - это уведомляемый о некотором событии объект.
Чтобы слушатель смог реагировать на определенное событие источника он должен быть им зарегистрирован, т.е. подключен к источнику.
Listener должен реализовывать определенные методы для получения и обработки уведомлений о событии.

Listener находится в постоянном ожидании, пока в источнике, в котором он зарегистрирован, не наступит соответствующее событие,
при возникновении которого, слушатель получает управление.
Также слушателю передается объект события (источник), чтобы он смог правильно на него отреагировать.
 */

/**
 * Класс, Слушающий и обрабатывающий изменения состояния панели вкладок
 */
public class TabbedPaneChangeListener implements ChangeListener {

    private View view;

    public TabbedPaneChangeListener(View view) { //конструктор принимает Представление, в качестве параметра и сохраняет его во внутреннем поле view класса
        this.view = view;
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        view.selectedTabChanged();
    }
}
