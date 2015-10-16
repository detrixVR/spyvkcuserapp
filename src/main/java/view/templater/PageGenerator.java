package view.templater;

import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "public_tml";
    private static final Configuration CFG = new Configuration(Configuration.VERSION_2_3_23);

    public static String getPage(String filename, Map<String, Object> data) {
        try {
            CFG.setDirectoryForTemplateLoading(new File("c:\\Users\\aminought\\IdeaProjects\\VKChase\\public_tml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }
}
