import com.google.inject.Guice;
import com.google.inject.Injector;
import handler.FollowingHandler;
import shared.controller.ApplicationModule;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Handler {
    public static void main(String[] args) throws IOException, TimeoutException {
        Injector injector = Guice.createInjector(new ApplicationModule());
        FollowingHandler followingHandler = injector.getInstance(FollowingHandler.class);
        followingHandler.connect();
        followingHandler.processFollowings();
        followingHandler.close();
    }
}
