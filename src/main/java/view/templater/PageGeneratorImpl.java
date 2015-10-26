package view.templater;

import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class PageGeneratorImpl implements IPageGenerator {
    private static final String HTML_DIR = "c:\\Users\\aminought\\IdeaProjects\\VKChase\\public_tml";
    private final Configuration CFG = new Configuration(Configuration.VERSION_2_3_23);

    @Override
    public String getPage(String filename, Map<String, Object> data) {
        try {
            CFG.setDirectoryForTemplateLoading(new File(HTML_DIR));
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
