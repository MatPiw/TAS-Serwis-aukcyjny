package pl.edu.amu.rest.database;

import com.google.common.collect.Lists;
import org.omg.CORBA.SystemException;
import pl.edu.amu.rest.entity.OfferEntity;
import pl.edu.amu.rest.entity.UserEntity;
import pl.edu.amu.rest.model.Prices;
import pl.edu.amu.rest.model.Offer;
import pl.edu.amu.rest.model.User;

import javax.naming.OperationNotSupportedException;
import javax.persistence.*;
import javax.ws.rs.NotSupportedException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDB implements ObjectBuilder, UserDatabase, OfferDatabase {

    private static final String USER_NAME = "14622709_tasy";
    private static final String PASSWORD = "TASY!2015";

    private enum operationType {INSERT, DELETE}

    ;

    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            String dbUrl = "jdbc:mysql://serwer1464462.home.pl/" + USER_NAME;

            Map<String, String> properties = new HashMap<String, String>();

            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            properties.put("hibernate.connection.url", dbUrl);
            properties.put("hibernate.connection.username", USER_NAME);
            properties.put("hibernate.connection.password", PASSWORD);
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false"); //PERFORMANCE TIP!
            properties.put("hibernate.hbm2ddl.auto", "update"); //update schema for entities (create tables if not exists)
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQLConnectionUnit", properties);
                entityManager = emf.createEntityManager();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return entityManager;
    }

    private void executeInDatabase(Object entity, operationType type) {

        try {
            getEntityManager().getTransaction().begin();
            if (type == operationType.INSERT)// Operations that modify the database should come here.
                getEntityManager().persist(entity);
            else
                getEntityManager().remove(entity);


            getEntityManager().getTransaction().commit();
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }

        }

    }

    @Override
    public User getUser(String sid) {
        Long id = getId(sid);

        UserEntity userEntity = getEntityManager()
                .find(UserEntity.class, id);
        if (userEntity != null) {
            return (User) buildResponse(userEntity);
        }

        return null;
    }

    @Override
    public User getUserbyLogin(String login) {

        Query query = getEntityManager().createNamedQuery("users.findByLogin");
        query.setParameter("login", login);
        UserEntity userEntity;
        try {
            userEntity = (UserEntity) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return (User) buildResponse(userEntity);
    }

    @Override
    public User updateUser(String sid, User user) {
        Long id = getId(sid);

        UserEntity userEntity = getEntityManager()
                .find(UserEntity.class, id);

        if (userEntity != null) {

            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setLogin(user.getLogin());
            userEntity.setHashPassword(user.getHashPassword());
            userEntity.setEmail(user.getEmail());
            userEntity.setCity(user.getCity());
            userEntity.setAddress(user.getAddress());
            userEntity.setPhone(user.getPhone());
            userEntity.setZipCode(user.getZipCode());
            userEntity.setPermissions(user.getPermissions());
            userEntity.setConfirmed(user.getConfirmed());

            executeInDatabase(userEntity, operationType.INSERT);

            return (User) buildResponse(userEntity);
        }

        return null;
    }

    @Override
    public User saveUser(final User user) {
        UserEntity userEntity = (UserEntity) buildEntity(user);

        executeInDatabase(userEntity, operationType.INSERT);

        return new User(userEntity.getId(), userEntity.getLogin(), userEntity.getHashPassword(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPermissions(), userEntity.getEmail(), userEntity.getCity(), userEntity.getAddress(), userEntity.getPhone(), userEntity.getZipCode(), userEntity.getCreatedAt(), userEntity.getConfirmed());
    }

    @Override
    public Collection<User> getUsers() {
        Query query = getEntityManager().createNamedQuery("users.findAll");
        List<UserEntity> resultList = query.getResultList();

        List<User> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (UserEntity user : resultList) {
                list.add((User) buildResponse(user));
            }
        }

        return list;
    }

    @Override
    public Boolean deleteUser(final String uid) {
        Long id = getId(uid);
        try {
            UserEntity userEntity = getEntityManager()
                    .find(UserEntity.class, id);
            executeInDatabase(userEntity, operationType.DELETE);
        } catch (IllegalArgumentException e) {
            return false;

        }
        return true;
    }

    private Long getId(String sid) {
        try {
            return Long.parseLong(sid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*private User buildUserResponse(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getLogin(), userEntity.getHashPassword(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPermissions(), userEntity.getEmail(), userEntity.getCity(), userEntity.getAddress(), userEntity.getPhone(), userEntity.getZipCode(), userEntity.getCreatedAt(), userEntity.getConfirmed());
    }

    private UserEntity buildUserEntity(User user) {

        return new UserEntity(user.getLogin(),user.getHashPassword(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getCity(),user.getAddress(),user.getPhone(),user.getZipCode(),user.getCreatedAt());
    }*/

    @Override
    public Object buildResponse(Object entity) {
        if (entity instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) entity;
            return new User(userEntity.getId(), userEntity.getLogin(), userEntity.getHashPassword(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPermissions(), userEntity.getEmail(), userEntity.getCity(), userEntity.getAddress(), userEntity.getPhone(), userEntity.getZipCode(), userEntity.getCreatedAt(), userEntity.getConfirmed());
        } else if (entity instanceof OfferEntity) {
            OfferEntity offerEntity = (OfferEntity) entity;
            return new Offer(offerEntity.getId(), offerEntity.getTitle(), offerEntity.getDescription(), offerEntity.getPicture_path(), offerEntity.getOwner_id(), new Prices(offerEntity.getBest_price(), offerEntity.getMinimal_price(), offerEntity.getBuy_now_price(), offerEntity.getCurrency()), offerEntity.getActive(), offerEntity.getCreated_at(), offerEntity.getFinished_at(), offerEntity.getBuyer_id(), offerEntity.getWeight(), offerEntity.getSize(), offerEntity.getShipment(), offerEntity.getCategory());
        }
        return null;//pozniej usunąć

    }

    @Override
    public Object buildEntity(Object model) {
        if (model instanceof User) {
            User user = (User) model;
            return new UserEntity(user.getLogin(), user.getHashPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCity(), user.getAddress(), user.getPhone(), user.getZipCode(), user.getCreatedAt());
        } else if (model instanceof Offer) {
            Offer offer = (Offer) model;
            return new OfferEntity(offer.getTitle(), offer.getDescription(), offer.getPicture_path(), offer.getOwner_id(), offer.getPrices().getBuy_now_price(), offer.getPrices().getCurrency(), offer.getActive(), offer.getCreated_at(), offer.getFinished_at(), offer.getBuyer_id(), offer.getPrices().getBest_price(), offer.getPrices().getMinimal_price(), offer.getWeight(), offer.getSize(), offer.getShipment(), offer.getCategory());
        }
        return null;//pozniej usunąć
    }

    //----------------------------------------------------------OFFER
    @Override
    public Offer getOffer(String offerId) {
        Long id = getId(offerId);

        OfferEntity offerEntity = getEntityManager()
                .find(OfferEntity.class, id);
        if (offerEntity != null) {
            return (Offer) buildResponse(offerEntity);
        }

        return null;
    }

    @Override
    public Offer updateOffer(String offerId, Offer offer) {
        Long id = getId(offerId);

        OfferEntity offerEntity = getEntityManager()
                .find(OfferEntity.class, id);
        if (offerEntity != null) {
            offerEntity.setTitle(offer.getTitle());
            offerEntity.setDescription(offer.getDescription());
            offerEntity.setPicture_path(offer.getPicture_path());
            offerEntity.setBuy_now_price(offer.getPrices().getBuy_now_price());
            offerEntity.setCurrency(offer.getPrices().getCurrency());
            offerEntity.setActive(offer.getActive());
            offerEntity.setFinished_at(offer.getFinishedAt());
            offerEntity.setBuyer_id(offer.getBuyer_id());
            offerEntity.setBest_price(offer.getPrices().getBest_price());
            offerEntity.setMinimal_price(offer.getPrices().getMinimal_price());
            offerEntity.setWeight(offer.getWeight());
            offerEntity.setShipment(offer.getShipment());
            offerEntity.setCategory(offer.getCategory());


            executeInDatabase(offerEntity, operationType.INSERT);

            return (Offer) buildResponse(offerEntity);
        }
        return null;

    }

    @Override
    public Offer saveOffer(Offer offer) {
        OfferEntity offerEntity = (OfferEntity) buildEntity(offer);

        executeInDatabase(offerEntity, operationType.INSERT);

        return new Offer(offerEntity.getId(), offerEntity.getTitle(), offerEntity.getDescription(), offerEntity.getPicture_path(), offerEntity.getOwner_id(), new Prices(offerEntity.getBest_price(), offerEntity.getMinimal_price(), offerEntity.getBuy_now_price(), offerEntity.getCurrency()), offerEntity.getActive(), offerEntity.getCreated_at(), offerEntity.getFinished_at(), offerEntity.getBuyer_id(), offerEntity.getWeight(), offerEntity.getSize(), offerEntity.getShipment(), offerEntity.getCategory());

    }

    @Override
    public Boolean deleteOffer(String offerId) {
        try {

            OfferEntity offerEntity = getEntityManager().find(OfferEntity.class, getId(offerId));
            //dodać jeszcze usuwanie komentarzy do niej i stawek do niej
            executeInDatabase(offerEntity, operationType.DELETE);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;

    }

    @Override
    public Boolean deleteOffersByOwnerId(String owner_id) {
        try {
            Query query = getEntityManager().createNamedQuery("offers.findAllByOwner");
            query.setParameter("owner", getId(owner_id));
            List<OfferEntity> resultList = query.getResultList();
            if (resultList.size() >= 1) {
                for (OfferEntity offerEntity : resultList) {
                    executeInDatabase(offerEntity, operationType.DELETE);
                }
            }
            return true;
        } catch (IllegalArgumentException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException e) {
            return false;
        }
        //LUB

        /*Query query = getEntityManager().createNamedQuery("offers.deleteAllByOwnerId");
        query.setParameter("owner_id", getId(owner_id));
        try {
            getEntityManager().getTransaction().begin();
            query.executeUpdate();


            getEntityManager().getTransaction().commit();
        } catch (IllegalArgumentException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException e) {
            return false;


        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        }
        return true;*/
    }


    @Override
    public Collection<Offer> getOffersByOwner(String uid) {
        Query query = getEntityManager().createNamedQuery("offers.findAllByOwner");
        query.setParameter("owner", getId(uid));
        List<OfferEntity> resultList = query.getResultList();

        List<Offer> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (OfferEntity offer : resultList) {
                list.add((Offer) buildResponse(offer));
            }
        }

        return list;

    }

    /*@Override
    public Collection<Offer> getOffersByBuyer(String uid) {
        Query query = getEntityManager().createNamedQuery("offers.findAllByBuyer");
        query.setParameter("buyer", getId(uid));
        List<OfferEntity> resultList = query.getResultList();

        List<Offer> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (OfferEntity offer : resultList) {
                list.add((Offer) buildResponse(offer));
            }
        }

        return list;

    }

    @Override
    public Collection<Offer> getOffersByCategory(String category) {
        Query query = getEntityManager().createNamedQuery("offers.findAllByCategory");
        query.setParameter("category", category);
        List<OfferEntity> resultList = query.getResultList();

        List<Offer> list = Collections.emptyList();

        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (OfferEntity offer : resultList) {
                list.add((Offer) buildResponse(offer));
            }
        }

        return list;

    }*/

    @Override
    public Collection<Offer> getOffersWithFilters(String owner_id, String buyer_id, String category) {
        String constraints = new String();
        String owner_idFilter = (owner_id == null) ? "" : " o.owner_id=" + owner_id;

        constraints += owner_idFilter;


        String buyer_idFilter = (buyer_id == null) ? "" : " o.buyer_id=" + buyer_id;
        //buyer_idFilter=buyer_idFilter.replaceAll("\\=null"," is NULL"); TO ZASTOSOWAĆ, GDYBY BUYER_ID BYŁ STRINGIEM NA POCZĄTKU

        constraints += buyer_idFilter;

        String categoryFilter = (category == null) ? "" : " o.category=" + category;

        constraints += categoryFilter;
        if (constraints.length() > 3) {
            constraints = "WHERE" + constraints;
            int i = constraints.indexOf("o.");

            //WTF
            String temp = constraints.substring(i + 1);
            constraints = constraints.substring(0, i + 1);
            temp = temp.replaceAll("o\\.", "AND o\\.");

            constraints = constraints + temp;

        }

        Query query = getEntityManager().createQuery("SELECT o FROM OfferEntity o " + constraints);
        List<OfferEntity> resultList = query.getResultList();

        List<Offer> list = Collections.emptyList();
        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (OfferEntity offer : resultList) {
                list.add((Offer) buildResponse(offer));
            }
        }

        return list;

    }


}
