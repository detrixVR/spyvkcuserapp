package controller.logic;

import model.group.Group;
import model.group.GroupInfo;

import java.util.ArrayList;

/**
 * Created by aminought on 27.10.2015.
 */
public interface IAppLogic {
    ArrayList<GroupInfo> formGroupsInfoFromSources(String[] ids, String[] names, String[] screenNames);

    ArrayList<Group> filterGroupsByFollowerLike(ArrayList<Group> groups, Long userId);
}
