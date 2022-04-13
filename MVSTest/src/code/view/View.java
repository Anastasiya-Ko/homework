package code.view;

import code.controller.Controller;
import code.model.ModelData;

public interface View {
    void refresh(ModelData modelData);
    void setController(Controller controller);
}
