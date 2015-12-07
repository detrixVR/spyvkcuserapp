package shared.controller.db_service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import shared.model.dao.*;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.group.GroupSnapshot;
import shared.model.post.Post;
import shared.model.post.PostSnapshot;
import shared.model.user.*;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DBServiceImpl implements IDBService {
    private SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserInfo.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Following.class);
        configuration.addAnnotatedClass(Follower.class);
        configuration.addAnnotatedClass(Group.class);
        configuration.addAnnotatedClass(GroupInfo.class);
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(FollowerCount.class);
        configuration.addAnnotatedClass(GroupSnapshot.class);
        configuration.addAnnotatedClass(PostSnapshot.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vkchase");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.connection.CharSet", "utf8mb4");
        configuration.setProperty("hibernate.connection.characterEncoding", "utf8");
//        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.autocommit", "false");

        sessionFactory = createSessionFactory(configuration);
        setUTF8MB4();
    }

    private void setUTF8MB4() {
        Session session = sessionFactory.openSession();
        session.doReturningWork(conn -> {
            try(Statement stmt = conn.createStatement()) {
                stmt.executeQuery("SET NAMES utf8mb4");
            }
            return null;
        });
        session.close();
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void saveFollower(Follower follower) {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        dao.save(follower);
        session.close();
    }

    @Override
    public void saveFollowing(Following following) {
        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        dao.save(following);
        session.close();
    }

    @Override
    public void updateFollower(Follower follower) {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        dao.update(follower);
        session.close();
    }

    @Override
    public Following getFollowingByVkId(Long id) {
        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        Following following = dao.getByVkId(id);
        session.close();
        return following;
    }

    @Override
    public void saveGroup(Group group) {
        Session session = sessionFactory.openSession();
        GroupDAO dao = new GroupDAO(session);
        dao.save(group);
        session.close();
    }

    @Override
    public Set<Group> getAllGroups() {
        Session session = sessionFactory.openSession();
        GroupDAO dao = new GroupDAO(session);
        Set<Group> groups = dao.getAll();
        for (Group group : groups) {
            Hibernate.initialize(group.getFollowing());
            Hibernate.initialize(group.getGroupInfo());
            for (Following following : group.getFollowing()) {
                Hibernate.initialize(following.getGroups());
                Hibernate.initialize(following.getFollowers());
            }
        }
        session.close();
        return groups;
    }

    @Override
    public Map<Long, Follower> getAllFollowers() {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        Map<Long, Follower> idFollowerMap = new HashMap<>();
        Set<Follower> followers = dao.getAll();
        for (Follower follower : followers) {
            Hibernate.initialize(follower.getFollowing());
            for (Following following : follower.getFollowing()) {
                Hibernate.initialize(following.getUserInfo());
            }
            idFollowerMap.put(follower.getUserInfo().getVkId(), follower);
        }
        session.close();
        return idFollowerMap;
    }

    @Override
    public void saveFollowerCount(FollowerCount followerCount) {
        Session session = sessionFactory.openSession();
        FollowerCountDAO dao = new FollowerCountDAO(session);
        dao.save(followerCount);
        session.close();
    }

    @Override
    public void updateGroup(Group group) {
        Session session = sessionFactory.openSession();
        GroupDAO dao = new GroupDAO(session);
        dao.update(group);
        session.close();
    }

    @Override
    public Set<Post> getAllPosts() {
        Session session = sessionFactory.openSession();
        PostDAO dao = new PostDAO(session);
        session.close();
        return dao.getAll();
    }

    @Override
    public Set<Following> getAllFollowings() {
        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        Set<Following> following = dao.getAll();
        for (Following followingOne : following) {
            Hibernate.initialize(followingOne.getGroups());
            for (Group group : followingOne.getGroups().keySet()) {
                Hibernate.initialize(group.getPosts());
                for (Post post : group.getPosts()) {
                    Hibernate.initialize(post.getLikedUserIds());
                }
            }
            Hibernate.initialize(followingOne.getUserInfo());
        }
        session.close();
        return following;
    }

    @Override
    public Follower getFollowerByVkId(Long id) {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        Follower follower = dao.getByVkId(id);
        Hibernate.initialize(follower.getFollowing());
        for (Following following : follower.getFollowing()) {
            Hibernate.initialize(following.getLikedPosts());
            for (Post post : following.getLikedPosts()) {
                Hibernate.initialize(post);
                Hibernate.initialize(post.getGroup().getGroupInfo());
            }
            Hibernate.initialize(following.getUserInfo());
        }
        session.close();
        return follower;
    }

    @Override
    public void saveGroupSnapshot(GroupSnapshot groupSnapshot) {
        Session session = sessionFactory.openSession();
        GroupSnapshotDAO dao = new GroupSnapshotDAO(session);
        dao.save(groupSnapshot);
        session.close();
    }

    @Override
    public void updateFollowing(Following following) {
        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        dao.update(following);
        session.close();
    }
}
