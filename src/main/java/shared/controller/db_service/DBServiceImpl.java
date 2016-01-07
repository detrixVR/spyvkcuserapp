package shared.controller.db_service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;
import shared.model.audio.Audio;
import shared.model.dao.*;
import shared.model.event.*;
import shared.model.friend.Friend;
import shared.model.group.Group;
import shared.model.snapshots.*;
import shared.model.post.Post;
import shared.model.user.*;
import shared.model.video.Video;

import java.sql.Connection;
import java.sql.SQLException;
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
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(FollowingEventTypes.class);
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(FollowerEvents.class);
        configuration.addAnnotatedClass(Snapshot.class);
        configuration.addAnnotatedClass(GroupListSnapshot.class);
        configuration.addAnnotatedClass(Audio.class);
        configuration.addAnnotatedClass(AudioListSnapshot.class);
        configuration.addAnnotatedClass(AudioEvent.class);
        configuration.addAnnotatedClass(Video.class);
        configuration.addAnnotatedClass(VideoListSnapshot.class);
        configuration.addAnnotatedClass(VideoEvent.class);
        configuration.addAnnotatedClass(Friend.class);
        configuration.addAnnotatedClass(FriendListSnapshot.class);
        configuration.addAnnotatedClass(FriendEvent.class);
        configuration.addAnnotatedClass(GroupListSnapshot.class);
        configuration.addAnnotatedClass(GroupEvent.class);
        configuration.addAnnotatedClass(PostListSnapshot.class);
        configuration.addAnnotatedClass(PostEvent.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vkchase?useUnicode=true&amp;characterEncoding=UTF-8");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
        configuration.setProperty("hibernate.cache.use_query_cache", "false");
        configuration.setProperty("hibernate.connection.CharSet", "utf8mb4");
        configuration.setProperty("hibernate.connection.characterEncoding", "utf8");
//        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.autocommit", "true");

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
    public Map<Long, Follower> getAllFollowers() {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        Map<Long, Follower> idFollowerMap = new HashMap<>();
        Set<Follower> followers = dao.getAll();
        for (Follower follower : followers) {
            Hibernate.initialize(follower.getFollowing());
            Hibernate.initialize(follower.getFollowing_EventTypesList());
            for (Following following : follower.getFollowing()) {
                Hibernate.initialize(following.getUserInfo());
                Hibernate.initialize(following.getFollower_EventsList());
                Hibernate.initialize(following.getFollowers());
                for (FollowerEvents follower_events : following.getFollower_EventsList()) {
                    Hibernate.initialize(follower_events.getFollower());
                    Hibernate.initialize(follower_events.getSnapshots());
                    Hibernate.initialize(follower_events.getEvents());
                    for (Snapshot snapshot : follower_events.getSnapshots()) {
                        Hibernate.initialize(snapshot);
                    }
                }
            }
            for (FollowingEventTypes following_eventTypes : follower.getFollowing_EventTypesList()) {
                Hibernate.initialize(following_eventTypes.getEventTypes());
            }
            idFollowerMap.put(follower.getUserInfo().getVkId(), follower);
        }
        session.close();
        return idFollowerMap;
    }

    @Override
    public Follower getFollowerByVkId(Long id) {
        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        Follower follower = dao.getByVkId(id);
        Hibernate.initialize(follower.getFollowing());
        for (Following following : follower.getFollowing()) {
            Hibernate.initialize(following.getUserInfo());
            Hibernate.initialize(following.getFollower_EventsList());
            for (FollowerEvents followerEvents : following.getFollower_EventsList()) {
                Hibernate.initialize(followerEvents.getEvents());
            }
        }
        session.close();
        return follower;
    }

    @Override
    public void updateFollowerEvents(FollowerEvents followerEvents) {
        Session session = sessionFactory.openSession();
        session.doReturningWork(conn -> {
            try(Statement stmt = conn.createStatement()) {
                stmt.executeQuery("SET NAMES utf8mb4");
            }
            return null;
        });
        FollowerEventsDAO dao = new FollowerEventsDAO(session);
        dao.update(followerEvents);
        session.close();
    }

    @Override
    public <T> void save(T t, Class<T> classT) {
        Session session = sessionFactory.openSession();
        DAO<T> dao = new DAO<T>(session, classT) {
            @Override
            public void save(T object) {
                super.save(object);
            }

            @Override
            public void update(T object) {
                super.update(object);
            }

            @Override
            public Set<T> getAll() {
                return super.getAll();
            }
        };
        dao.save(t);
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
