package web.DAO;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getListUser() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getById(long id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id",User.class);
        query.setParameter("id",id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        String sql = "delete from User where id =  " + user.getId();
        entityManager.createQuery(sql).executeUpdate();
    }

    @Override
    public void edit(User user) {
        entityManager.merge(user);

    }
}
