import controller.Logic;
import model.Group;
import model.Post;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic = new Logic();

    @Test
    public void testFormGroupsFromSources() throws Exception {
        ArrayList<Group> expected = new ArrayList<>();
        String[] ids = new String[]{"1", "2", "3"};
        String[] names = new String[]{"name1", "name3", "name3"};
        String[] screenNames = new String[]{"screenName1", "screenName2", "screenName3"};
        for(int i=0; i< ids.length; i++) {
            Group group = new Group();
            group.setId(Long.valueOf(ids[i]));
            group.setName(names[i]);
            group.setScreenName(screenNames[i]);
            expected.add(group);
        }
        ArrayList<Group> actual = logic.formGroupsFromSources(ids, names, screenNames);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindGroupsWithPostsLikedByUser() throws Exception {
        Long userId = 5L;
        ArrayList<Group> groups = new ArrayList<>();
        ArrayList<Group> expectedGroups = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            Group group = new Group();
            Group expectedGroup = new Group();

            ArrayList<Post> posts = new ArrayList<>();
            ArrayList<Post> expectedPosts = new ArrayList<>();

            boolean isGroupAdded = false;
            for (int j = 0; j < 20; j++) {
                Post post = new Post();
                ArrayList<Long> likedUserIds = new ArrayList<>();

                Random rand = new Random();
                int random = rand.nextInt(50)+7;
                for (int k = 6; k < random; k++) {
                    likedUserIds.add((long) k);
                }
                boolean isPostAdded = false;
                if(j%3==0) {
                    likedUserIds.add(userId);
                    isPostAdded = true;
                    isGroupAdded = true;
                }
                post.setLikedUserIds(likedUserIds);

                posts.add(post);
                if(isPostAdded) {
                    expectedPosts.add(post);
                }
            }

            group.setPosts(posts);
            expectedGroup.setPosts(expectedPosts);

            groups.add(group);
            if(isGroupAdded) {
                expectedGroups.add(expectedGroup);
            }
        }
        ArrayList<Group> actual = logic.findGroupsWithPostsLikedByUser(groups, userId);
        assertEquals(expectedGroups, actual);
    }
}