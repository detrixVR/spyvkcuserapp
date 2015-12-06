import serverdaemon.controller.logic.AppLogicImpl;
import serverdaemon.controller.logic.IAppLogic;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppLogicImplTest {
    private IAppLogic logic = new AppLogicImpl();

    @Test
    public void testFilterGroupsByFollowerLike() throws Exception {
        Group group1 = mock(Group.class);
        Group group2 = mock(Group.class);
        Set<Post> posts = new HashSet<>();
        for(int i=0; i<3; i++) {
            Post post = mock(Post.class);
            when(post.getLikedUserIds()).thenReturn(new HashSet<>(Arrays.<Long>asList((long) i, 2L, 3L)));
            posts.add(post);
        }
        when(group1.getPosts()).thenReturn(posts);
        when(group2.getPosts()).thenReturn(new LinkedHashSet<>());
        Set<Group> groups = new HashSet<>();
        groups.add(group1);
        groups.add(group2);

        Set<Group> filteredGroups = logic.filterGroupsByFollowingLike(groups, 1L);

        assertEquals(1, filteredGroups.size());
    }
}