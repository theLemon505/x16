package launcher;

import gui.Window;
import gui.elements.Button;

public class LauncherWindow extends Window {
    public LauncherWindow(String title, float width, float height) {
        super(title, width, height);
        addElement(new Button());
    }
}
