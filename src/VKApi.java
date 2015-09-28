import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VKApi {
    private Client client = null;
    public volatile boolean isPasswordEntered = false;
    private volatile String password = null;
    private JFrame frame = null;

    public class Auth {
        public String authorize(Client client) throws IOException {
            StringBuilder authorizeLink = new StringBuilder();
            authorizeLink.append("https://oauth.vk.com/authorize?");
            authorizeLink.append("client_id=");
            authorizeLink.append(client.getClientID());
            authorizeLink.append("&redirect_uri=https://oauth.vk.com/blank.html");
            authorizeLink.append("&display=page");
            authorizeLink.append("&scope=groups");
            authorizeLink.append("&response_type=token");
            authorizeLink.append("&v=5.37");

            // authorization via console browser
            // browser setup
            System.setErr(new PrintStream("NUL"));
            BrowserVersion browserVersion = BrowserVersion.CHROME;
            browserVersion.setBrowserLanguage("ru-RU");
            browserVersion.setUserLanguage("ru-RU");
            WebClient webClient = new WebClient(browserVersion);
            webClient.addRequestHeader("charset", "utf-8");
            HtmlPage page = webClient.getPage(authorizeLink.toString());
            List<HtmlForm> forms = page.getForms();
            HtmlForm form = forms.get(0);
            // form filling
            String[] emailpass = requestLoginData();
            HtmlTextInput textInput = form.getInputByName("email");
            textInput.setText(emailpass[0]);
            HtmlPasswordInput passwordInput = form.getInputByName("pass");
            passwordInput.setText(emailpass[1]);
            HtmlButton button = (HtmlButton) form.getByXPath("//*[@id=\"install_allow\"]").get(0);
            // try to remove extra input that interrupt authorization
            HtmlSubmitInput trap = (HtmlSubmitInput) form.getByXPath("//*[@id=\"box\"]/div/input[8]").get(0);
            trap.remove();
            HtmlPage result = button.click();

            String accessToken = parseAccessToken(result.getUrl().toString());
            if(accessToken != null) {
                System.setErr(new PrintStream(System.err));
                return accessToken;
            }

            HtmlButton allowButton = (HtmlButton) result.getByXPath("//*[@id=\"install_allow\"]").get(0);
            HtmlPage accessPage = allowButton.click();
            String url = accessPage.getUrl().toString();
            accessToken = parseAccessToken(url);
            System.setErr(new PrintStream(System.err));
            return accessToken;
        }

        private String parseAccessToken(String url) {
            Pattern pattern = Pattern.compile("access_token=([^&]*)");
            Matcher matcher = pattern.matcher(url);
            if(matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }

        public String[] requestLoginData() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] emailpass = new String[2];
            System.out.print("Login: ");
            emailpass[0] = br.readLine();
            showPasswordRequest();
            while(!isPasswordEntered) {/*wait...*/}
            emailpass[1] = password;
            return emailpass;
        }

        private void showPasswordRequest() {
            Panel p = new Panel();
            Runnable r = () -> {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                p.createAndShowGUI();
            };
            SwingUtilities.invokeLater(r);
        }

        class Panel extends JPanel
                implements ActionListener {
            private final String OK = "OK";
            private JPasswordField passwordField;

            public Panel() {
                passwordField = new JPasswordField(10);
                passwordField.setActionCommand(OK);
                passwordField.addActionListener(this);

                JLabel passLabel = new JLabel("Password: ");
                passLabel.setLabelFor(passwordField);
                JComponent buttonPane = createButtonPanel();

                JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
                textPane.add(passwordField);
                textPane.add(passLabel);

                add(textPane);
                add(buttonPane);
            }

            private JComponent createButtonPanel() {
                JPanel p = new JPanel(new GridLayout(0,1));
                JButton okButton = new JButton("OK");
                okButton.setActionCommand(OK);
                okButton.addActionListener(this);
                p.add(okButton);
                return p;
            }

            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();

                if (OK.equals(cmd)) {
                    char[] input = passwordField.getPassword();
                    password = String.valueOf(input);
                    isPasswordEntered = true;
                    frame.dispose();
                }
            }


            public void createAndShowGUI() {
                frame = new JFrame("Password");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                final Panel newContentPane = new Panel();
                newContentPane.setOpaque(true);
                frame.setContentPane(newContentPane);
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    public String authorize(Client client) {
        this.client = client;
        try {
            return (new Auth()).authorize(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long resolve(String userlink) {
        userlink = userlink.replace("https://vk.com/", "");
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/utils.resolveScreenName?");
            request.append("screen_name=");
            request.append(userlink);
            request.append("&v=5.37");

            String result = (new Request()).get(request.toString());
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getJSONObject("response").getLong("object_id");
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Long> getGroupIDs(long userID) {
        String groupsJSON = "";
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/groups.get?");
            request.append("user_id=");
            request.append(userID);
            request.append("&count=1000");
            request.append("&v=5.37");
            request.append("&access_token=");
            request.append(client.getAccessToken());

            groupsJSON = (new Request()).get(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(groupsJSON);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
        ArrayList<Long> groupIDs = new ArrayList<>();
        for(int i=0; !jsonArray.isNull(i); i++) {
            groupIDs.add(jsonArray.getLong(i));
        }
        return groupIDs;
    }

    public ArrayList<Long> getPostIDs(long groupID) {
        String postsJSON = "";
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/wall.get?");
            request.append("owner_id=-");
            request.append(groupID);
            request.append("&offset=0");
            request.append("&count=100");
            request.append("&v=5.37");

            postsJSON = (new Request()).get(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Long> postIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(postsJSON);
        JSONArray itemsArray = jsonObject.getJSONObject("response").getJSONArray("items");
        for (int i = 0; !itemsArray.isNull(i); i++) {
            postIDs.add(itemsArray.getJSONObject(i).getLong("id"));
        }
        return postIDs;
    }

    public ArrayList<Long> getLikesUserIDs(long groupID, long postID) throws IOException {
        StringBuilder request = new StringBuilder();
        request.append("https://api.vk.com/method/likes.getList?");
        request.append("type=post");
        request.append("&owner_id=-");
        request.append(groupID);
        request.append("&item_id=");
        request.append(postID);
        request.append("&offset=0");
        request.append("&count=1000");

        String likesJSON = (new Request()).get(request.toString());
        ArrayList<Long> userIDs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(likesJSON);
        JSONArray userArray = jsonObject.getJSONObject("response").getJSONArray("users");
        for (int i = 0; !userArray.isNull(i); i++) {
            userIDs.add(userArray.getLong(i));
        }
        return userIDs;
    }
}
