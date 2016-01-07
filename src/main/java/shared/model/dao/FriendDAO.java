package shared.model.dao;

import org.hibernate.Session;
import shared.model.friend.Friend;

public class FriendDAO extends DAO<Friend> {
    public FriendDAO(Session session) {
        super(session, Friend.class);
    }
}
