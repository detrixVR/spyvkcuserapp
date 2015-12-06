package shared.controller.request;

import java.io.IOException;

public interface IRequest {
    String get(String request) throws IOException;
}
