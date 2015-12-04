import com.google.inject.Guice;
import com.google.inject.Injector;
import serverdaemon.controller.RefreshGroupsTask;
import shared.controller.ApplicationModule;

import java.util.Timer;

public class Daemon {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        RefreshGroupsTask refreshGroupsTask = injector.getInstance(RefreshGroupsTask.class);

        Timer time = new Timer();
        int sixHours = 1000 * 60 * 60 * 6;
        int threeSeconds = 1000 * 3;
        time.schedule(refreshGroupsTask, 0, sixHours);
    }
}
