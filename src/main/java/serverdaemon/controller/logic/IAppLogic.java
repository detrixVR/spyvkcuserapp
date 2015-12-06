package serverdaemon.controller.logic;

import shared.model.group.Group;

import java.util.Set;

public interface IAppLogic {
    Set<Group> filterGroupsByFollowingLike(Set<Group> groups, Long userId);
}
