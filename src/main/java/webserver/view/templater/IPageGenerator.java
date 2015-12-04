package webserver.view.templater;

import java.util.Map;

/**
 * Created by aminought on 27.10.2015.
 */
public interface IPageGenerator {
    String getPage(String filename, Map<String, Object> data);
}
