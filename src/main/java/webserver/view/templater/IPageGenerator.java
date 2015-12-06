package webserver.view.templater;

import java.util.Map;

public interface IPageGenerator {
    String getPage(String filename, Map<String, Object> data);
}
