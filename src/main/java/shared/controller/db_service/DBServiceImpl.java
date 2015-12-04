package shared.controller.db_service;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import shared.model.dao.FollowerCountDAO;
import shared.model.dao.FollowerDAO;
import shared.model.dao.FollowingDAO;
import shared.model.dao.GroupDAO;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DBServiceImpl implements IDBService {
    private SessionFactory sessionFactory;
    private Session session; // dirty hack

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

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vkchase");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.connection.characterEncoding", "utf8");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.connection.autocommit", "false");

        sessionFactory = createSessionFactory(configuration);
        session = sessionFactory.openSession(); // dirty hack
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void saveFollower(Follower follower) {
//        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        dao.save(follower);
//        session.close();
    }

    @Override
    public void saveFollowing(Following following) {
//        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        dao.save(following);
//        session.close();
    }

    @Override
    public void updateFollower(Follower follower) {
//        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        dao.update(follower);
//        session.close();
    }

    @Override
    public Following getFollowingByVkId(Long id) {
//        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        Following following= dao.getByVkId(id);
//        session.close();
        return following;
    }

    @Override
    public void saveGroup(Group group) {
//        Session session = sessionFactory.openSession();
        GroupDAO dao = new GroupDAO(session);
        dao.save(group);
//        session.close();
    }

    @Override
    public Set<Group> getAllGroups() {
//        Session session = sessionFactory.openSession();
        GroupDAO dao = new GroupDAO(session);
        Set<Group> groups = dao.getAll();
//        session.close();
        return groups;
    }

    @Override
    public Map<Long, Follower> getAllFollowers() {
//        Session session = sessionFactory.openSession();
        FollowerDAO dao = new FollowerDAO(session);
        Map<Long, Follower> idFollowerMap = new HashMap<>();
        Set<Follower> followers = dao.getAll();
        for (Follower follower : followers) {
            idFollowerMap.put(follower.getUserInfo().getVkId(), follower);
        }
        return idFollowerMap;
    }

    @Override
    public void saveFollowerCount(FollowerCount followerCount) {
//        Session session = sessionFactory.openSession();
        FollowerCountDAO dao = new FollowerCountDAO(session);
        dao.save(followerCount);
//        session.close();
    }

    @Override
    public void updateFollowing(Following following) {
//        Session session = sessionFactory.openSession();
        FollowingDAO dao = new FollowingDAO(session);
        dao.update(following);
//        session.close();
    }
}
