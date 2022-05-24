package launcher;

import gui.Gui;

public class LauncherGui extends Gui {

    @Override
    public void init() {
        addWindow(new LauncherWindow("test", 100, 100));
        lowerInit();
    }

    @Override
    public void loop() {
        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
