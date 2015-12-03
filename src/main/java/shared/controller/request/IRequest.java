package shared.controller.request;

import java.io.IOException;

/**
 * Created by aminought on 27.10.2015.
 */
public interface IRequest {
    String get(String request) throws IOException;
}
