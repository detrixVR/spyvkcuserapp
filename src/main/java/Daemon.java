import com.google.inject.Guice;
import com.google.inject.Injector;
import serverdaemon.controller.RefreshTask;
import shared.controller.ApplicationModule;

import java.util.Timer;

public class Daemon {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        RefreshTask refreshTask = injector.getInstance(RefreshTask.class);

        Timer time = new Timer();
        int sixHours = 1000 * 60 * 60 * 6;
        int thirtySeconds = 1000 * 30;
        int twoSeconds = 1000 * 2;
        time.schedule(refreshTask, 0, twoSeconds);
    }
}
