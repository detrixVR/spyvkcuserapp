import controller.Logic;
import model.GroupInfo;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic = new Logic();

    @Test
    public void testFormGroupsFromSources() throws Exception {
        ArrayList<GroupInfo> expected = new ArrayList<>();
        String[] ids = new String[]{"1", "2", "3"};
        String[] names = new String[]{"name1", "name3", "name3"};
        String[] screenNames = new String[]{"screenName1", "screenName2", "screenName3"};
        for(int i=0; i< ids.length; i++) {
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setId(Long.valueOf(ids[i]));
            groupInfo.setName(names[i]);
            groupInfo.setScreenName(screenNames[i]);
            expected.add(groupInfo);
        }
        ArrayList<GroupInfo> actual = logic.formGroupsInfoFromSources(ids, names, screenNames);
        assertEquals(expected, actual);
    }
}