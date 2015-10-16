package controller;

import model.Group;

import java.util.ArrayList;

public class Logic {
    public ArrayList<Group> formGroupsFromSources(String[] ids, String[] names, String[] screenNames) {
        ArrayList<Group> groups = new ArrayList<>();
        for(int i=0; i<ids.length; i++) {
            Group group = new Group();
            group.setId(Long.valueOf(ids[i]));
            group.setName(names[i]);
            group.setScreenName(screenNames[i]);
            groups.add(group);
        }
        return groups;
    }
}
