package shared.controller.db_service;

import shared.model.dao.UserDAO;
import shared.model.group.Group;
import shared.model.group.GroupInfo;
import shared.model.post.Post;
import shared.model.user.Follower;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import shared.model.user.Following;
import shared.model.user.User;
import shared.model.user.UserInfo;

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

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/vkchase");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.autocommit", "false");

        sessionFactory = createSessionFactory(configuration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void saveUser(Follower user) {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);
        dao.save(user);
    }
}
