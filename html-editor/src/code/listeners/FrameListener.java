package code.listeners;

import code.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
Слушатель Listener - это уведомляемый о некотором событии объект.
Чтобы слушатель смог реагировать на определенное событие источника он должен быть им зарегистрирован, т.е. подключен к источнику.
Listener должен реализовывать определенные методы для получения и обработки уведомлений о событии.

Listener находится в постоянном ожидании, пока в источнике, в котором он зарегистрирован, не наступит соответствующее событие,
при возникновении которого, слушатель получает управление.
Также слушателю передается объект события (источник), чтобы он смог правильно на него отреагировать.
 */

/**
 * Класс для создания Окна Слушателя
 * WindowAdapter - Абстрактный класс адаптера для получения событий окна(существует для удобства создания объектов Cлушателя).
 */
public class FrameListener extends WindowAdapter {
    private View view;

    public FrameListener(View view) {
        this.view = view;
    }

    @Override
    public void windowClosing(WindowEvent e) {
       view.exit();
    }
}
