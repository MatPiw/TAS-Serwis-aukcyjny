package pl.edu.amu.rest.database;

import com.google.common.collect.Lists;
import org.omg.CORBA.SystemException;
import pl.edu.amu.rest.entity.BidEntity;
import pl.edu.amu.rest.entity.CommentEntity;
import pl.edu.amu.rest.entity.OfferEntity;
import pl.edu.amu.rest.entity.UserEntity;
import pl.edu.amu.rest.model.*;
import scala.util.parsing.combinator.testing.Str;

import javax.naming.OperationNotSupportedException;
import javax.persistence.*;
import javax.ws.rs.NotSupportedException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDB implements ObjectBuilder, UserDatabase, OfferDatabase, CommentDatabase {

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

    private Boolean executeInDatabase(Object entity, operationType type) {

        try {
            getEntityManager().getTransaction().begin();
            if (type == operationType.INSERT)// Operations that modify the database should come here.
                getEntityManager().persist(entity);
            else
                getEntityManager().remove(entity);


            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }

        }
        return true;

    }
    private void deleteItemList(List<Object> entitiesToDelete, Class entityClass){
        if (entitiesToDelete.size()>0){
            if (entityClass==UserEntity.class){
                List<UserEntity> temp=(List<UserEntity>)(List<?>) entitiesToDelete;
                for (UserEntity userEntity:temp){
                    executeInDatabase(userEntity,operationType.DELETE);
                }

            }
            else if (entityClass==OfferEntity.class){
                List<OfferEntity> temp=(List<OfferEntity>)(List<?>) entitiesToDelete;
                for (OfferEntity offerEntity:temp){
                    executeInDatabase(offerEntity,operationType.DELETE);
                }
            }
            else if (entityClass==CommentEntity.class){
                List<CommentEntity> temp=(List<CommentEntity>)(List<?>) entitiesToDelete;
                for (CommentEntity commentEntity:temp){
                    executeInDatabase(commentEntity,operationType.DELETE);
                }
            }
            else if (entityClass==BidEntity.class){
                List<BidEntity> temp=(List<BidEntity>)(List<?>) entitiesToDelete;
                for (BidEntity bidEntity:temp){
                    executeInDatabase(bidEntity,operationType.DELETE);
                }
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


            if (executeInDatabase(userEntity, operationType.INSERT))
                return (User) buildResponse(userEntity);
            else
                return (User) buildResponse(new UserEntity());
        }

        return null;
    }

    @Override
    public User saveUser(final User user) {
        UserEntity userEntity = (UserEntity) buildEntity(user);

        if (executeInDatabase(userEntity, operationType.INSERT))
            return new User(Long.toString(userEntity.getId()), userEntity.getLogin(), userEntity.getHashPassword(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPermissions(), userEntity.getEmail(), userEntity.getCity(), userEntity.getAddress(), userEntity.getPhone(), userEntity.getZipCode(), userEntity.getCreatedAt(), userEntity.getConfirmed());
        else
            return null;
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
            return new User(Long.toString(userEntity.getId()), userEntity.getLogin(), userEntity.getHashPassword(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getPermissions(), userEntity.getEmail(), userEntity.getCity(), userEntity.getAddress(), userEntity.getPhone(), userEntity.getZipCode(), userEntity.getCreatedAt(), userEntity.getConfirmed());
        } else if (entity instanceof OfferEntity) {
            OfferEntity offerEntity = (OfferEntity) entity;
            String buyerId=(offerEntity.getBuyer_id()==null)?null:Long.toString(offerEntity.getBuyer_id());
            return new Offer(Long.toString(offerEntity.getId()), offerEntity.getTitle(), offerEntity.getDescription(), offerEntity.getPicture_path(), Long.toString(offerEntity.getOwner_id()), new Prices(offerEntity.getBest_price(), offerEntity.getMinimal_price(), offerEntity.getBuy_now_price(), offerEntity.getCurrency()), offerEntity.getActive(), offerEntity.getCreatedAt(), offerEntity.getFinishedAt(), buyerId, offerEntity.getWeight(), offerEntity.getSize(), offerEntity.getShipment(), offerEntity.getCategory());
        } else if (entity instanceof CommentEntity) {
            CommentEntity commentEntity = (CommentEntity) entity;
            return new Comment(Long.toString(commentEntity.getId()), commentEntity.getGiverId(), commentEntity.getGiverId(), commentEntity.getOfferId(), commentEntity.getCommentText(), commentEntity.getPositive(), commentEntity.getCreatedAt());
        } /*else if (entity instanceof BidEntity) {

        }*/ else
            return null;//pozniej usunąć

    }

    @Override
    public Object buildEntity(Object model) {
        if (model instanceof User) {
            User user = (User) model;
            return new UserEntity(user.getLogin(), user.getHashPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCity(), user.getAddress(), user.getPhone(), user.getZipCode(), user.getCreatedAt());
        } else if (model instanceof Offer) {
            Offer offer = (Offer) model;
            Long buyerId=(offer.getBuyer_id()!=null)? Long.valueOf(offer.getBuyer_id()):null;
            return new OfferEntity(offer.getTitle(), offer.getDescription(), offer.getPicture_path(), Long.valueOf(offer.getOwner_id()), offer.getPrices().getBuy_now_price(), offer.getPrices().getCurrency(), offer.getActive(), offer.getCreatedAt(), offer.getFinishedAt(),buyerId , offer.getPrices().getBest_price(), offer.getPrices().getMinimal_price(), offer.getWeight(), offer.getSize(), offer.getShipment(), offer.getCategory());
        } else if (model instanceof Comment) {
            Comment comment = (Comment) model;
            return new CommentEntity(comment.getRecieverId(), comment.getOfferId(), comment.getCommentText(), comment.isPositive(), comment.getCreatedAt());
        } /*else if (model instanceof Bid) {

        }*/ else
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
            Long buyerId=(offer.getBuyer_id()==null)?null:Long.valueOf(offer.getBuyer_id());

            offerEntity.setTitle(offer.getTitle());
            offerEntity.setDescription(offer.getDescription());
            offerEntity.setPicture_path(offer.getPicture_path());
            offerEntity.setBuy_now_price(offer.getPrices().getBuy_now_price());
            offerEntity.setCurrency(offer.getPrices().getCurrency());
            offerEntity.setActive(offer.getActive());
            offerEntity.setFinishedAt(offer.getFinishedAt());
            offerEntity.setBuyer_id(buyerId);
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

        String buyerId=(offerEntity.getBuyer_id()==null)?null:Long.toString(offerEntity.getBuyer_id());

        return new Offer(Long.toString(offerEntity.getId()), offerEntity.getTitle(), offerEntity.getDescription(), offerEntity.getPicture_path(), Long.toString(offerEntity.getOwner_id()), new Prices(offerEntity.getBest_price(), offerEntity.getMinimal_price(), offerEntity.getBuy_now_price(), offerEntity.getCurrency()), offerEntity.getActive(), offerEntity.getCreatedAt(), offerEntity.getFinishedAt(), buyerId, offerEntity.getWeight(), offerEntity.getSize(), offerEntity.getShipment(), offerEntity.getCategory());

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
            deleteItemList(query.getResultList(),OfferEntity.class);
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
        Query query;
        if (owner_id != null || buyer_id != null || category != null) {
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
            query = getEntityManager().createQuery("SELECT o FROM OfferEntity o " + constraints);
        } else {
            query = getEntityManager().createNamedQuery("offers.findAll");
        }


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
    //------------------------------------------------COMMENTS

    @Override
    public Comment getComment(String commentId) {
        Long id = getId(commentId);

        CommentEntity commentEntity = getEntityManager()
                .find(CommentEntity.class, id);
        if (commentEntity != null) {
            return (Comment) buildResponse(commentEntity);
        }

        return null;
    }

    @Override
    public Collection<Comment> getCommentsWithFilters(String giverId, String receiverId, String offerId) {

        Query query;

        if (giverId != null || receiverId != null || offerId != null) {
            String constraints = new String();
            String giverIdFilter = (giverId == null) ? "" : " c.giverId=" + giverId;

            constraints += giverIdFilter;


            String receiverIdFilter = (receiverId == null) ? "" : " c.receiverId=" + receiverId;
            //buyer_idFilter=buyer_idFilter.replaceAll("\\=null"," is NULL"); TO ZASTOSOWAĆ, GDYBY BUYER_ID BYŁ STRINGIEM NA POCZĄTKU

            constraints += receiverIdFilter;

            String offerIdFilter = (offerId == null) ? "" : " c.offerId=" + offerId;

            constraints += offerIdFilter;
            if (constraints.length() > 3) {
                constraints = "WHERE" + constraints;
                int i = constraints.indexOf("c.");

                //WTF
                String temp = constraints.substring(i + 1);
                constraints = constraints.substring(0, i + 1);
                temp = temp.replaceAll("c\\.", "AND c\\.");

                constraints = constraints + temp;

            }
            query = getEntityManager().createQuery("SELECT c FROM CommentEntity c " + constraints);
        } else {
            query = getEntityManager().createNamedQuery("comments.findAll");
        }

        List<CommentEntity> resultList = query.getResultList();

        List<Comment> list = Collections.emptyList();
        if (resultList != null && !resultList.isEmpty()) {
            list = Lists.newArrayListWithCapacity(resultList.size());

            for (CommentEntity comment : resultList) {
                list.add((Comment) buildResponse(comment));
            }
        }

        return list;
    }

    @Override
    public Comment updateComment(String commentId, Comment comment) {
        Long id = getId(commentId);

        CommentEntity commentEntity = getEntityManager()
                .find(CommentEntity.class, id);
        if (commentEntity != null) {
            commentEntity.setGiverId(comment.getGiverId());
            commentEntity.setReceiverId(comment.getRecieverId());
            commentEntity.setOfferId(comment.getOfferId());
            commentEntity.setCommentText(comment.getCommentText());
            commentEntity.setPositive(comment.isPositive());
            if (executeInDatabase(commentEntity, operationType.INSERT))
                return (Comment) buildResponse(commentEntity);
            else
                return (Comment) buildResponse(new CommentEntity());


        }
        return null;

    }

    @Override
    public Comment saveComment(Comment comment) {
        CommentEntity commentEntity = (CommentEntity) buildEntity(comment);
        if (executeInDatabase(commentEntity, operationType.INSERT))
            return new Comment(Long.toString(commentEntity.getId()), commentEntity.getGiverId(), commentEntity.getReceiverId(), commentEntity.getOfferId(), commentEntity.getCommentText(), commentEntity.getPositive(), commentEntity.getCreatedAt());
        else
            return null;
    }

    @Override
    public Boolean deleteComment(String commentId) {
        try {
            Long id = getId(commentId);
            CommentEntity commentEntity = getEntityManager().find(CommentEntity.class, id);
            //dodać jeszcze usuwanie komentarzy do niej i stawek do niej
            executeInDatabase(commentEntity, operationType.DELETE);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteCommentsFromAuction(String offerId) {
        try {
            Long id = getId(offerId);
            /*OfferEntity offerEntity = getEntityManager().find(OfferEntity.class, id);
            if (offerEntity != null) {*/
            Query query = getEntityManager().createNamedQuery("comments.findAllByAuction");
            query.setParameter("offerId", id);
            deleteItemList(query.getResultList(),CommentEntity.class);

            /*}*/
        } catch (IllegalArgumentException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException e) {
            return false;
        }
        return true;
    }

        /*try {
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
        }*/

    @Override
    public Boolean deleteCommentsFromUser(String userId) {// usuwamy usera i checmey usunąć wszystkie komentarze do jego aukcji jak i jego wystawione komentarze
        try {
            Long id=getId(userId);

            Query query=getEntityManager().createNamedQuery("comments.findAllByUser");
            query.setParameter("userId",id);

            deleteItemList(query.getResultList(),CommentEntity.class);
        }catch (IllegalArgumentException | NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException e) {
            return false;
        }
        return true;


    }


}
