import controller.logic.AppLogicImpl;
import controller.logic.IAppLogic;
import model.group.Group;
import model.group.GroupInfo;
import model.post.Post;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppLogicImplTest {
    private IAppLogic logic = new AppLogicImpl();

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


    @Test
    public void testFilterGroupsByFollowerLike() throws Exception {
        Group group1 = mock(Group.class);
        Group group2 = mock(Group.class);
        ArrayList<Post> posts = new ArrayList<>();
        for(int i=0; i<3; i++) {
            Post post = mock(Post.class);
            when(post.getLikedUserIds()).thenReturn(new ArrayList<>(Arrays.<Long>asList((long) i, 2L, 3L)));
            posts.add(post);
        }
        when(group1.getPosts()).thenReturn(posts);
        when(group2.getPosts()).thenReturn(new ArrayList<>());
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        ArrayList<Group> filteredGroups = logic.filterGroupsByFollowerLike(groups, 1L);

        assertEquals(1, filteredGroups.size());
        assertEquals(1, filteredGroups.get(0).getPosts().size());
    }
}