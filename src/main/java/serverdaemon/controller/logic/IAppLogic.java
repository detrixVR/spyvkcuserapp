package serverdaemon.controller.logic;

import shared.model.group.Group;
import shared.model.group.GroupInfo;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by aminought on 27.10.2015.
 */
public interface IAppLogic {
    ArrayList<GroupInfo> formGroupsInfoFromSources(String[] ids, String[] names, String[] screenNames);

    Set<Group> filterGroupsByFollowingLike(Set<Group> groups, Long userId);
}
