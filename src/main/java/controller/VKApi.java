package controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import com.sun.istack.internal.NotNull;
import model.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import view.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VKApi {
    private Client client = null;

    public class Auth {
        public String authorize(@NotNull Client client) throws IOException {
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
            final HtmlForm[] form = new HtmlForm[1];
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        System.setErr(new PrintStream("NUL")); // disable warnings
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BrowserVersion browserVersion = BrowserVersion.CHROME;
                    browserVersion.setBrowserLanguage("ru-RU");
                    browserVersion.setUserLanguage("ru-RU");
                    WebClient webClient = new WebClient(browserVersion);
                    webClient.addRequestHeader("charset", "utf-8");
                    HtmlPage page = null;
                    try {
                        page = webClient.getPage(authorizeLink.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<HtmlForm> forms = page.getForms();
                    form[0] = forms.get(0);
                }
            };
            t.run();
            // form filling
            String[] emailpass = UI.getInstance().requestLoginData();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HtmlTextInput textInput = form[0].getInputByName("email");
            textInput.setText(emailpass[0]);
            HtmlPasswordInput passwordInput = form[0].getInputByName("pass");
            passwordInput.setText(emailpass[1]);
            HtmlButton button = (HtmlButton) form[0].getByXPath("//*[@id=\"install_allow\"]").get(0);
            // try to remove extra input that interrupt authorization
            HtmlSubmitInput trap = (HtmlSubmitInput) form[0].getByXPath("//*[@id=\"box\"]/div/input[8]").get(0);
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

    public long resolveScreenName(String userlink) {
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

    public ArrayList<Long> getPostIDs(long groupID, int countOfPosts) {
        String postsJSON = "";
        try {
            StringBuilder request = new StringBuilder();
            request.append("https://api.vk.com/method/wall.get?");
            request.append("owner_id=-");
            request.append(groupID);
            request.append("&offset=0");
            request.append("&count=");
            request.append(countOfPosts);
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
