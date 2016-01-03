package pl.edu.amu.rest.database;

/**
 * Created by Altenfrost on 2015-12-31.
 */
public interface ObjectBuilder {
    Object buildResponse(Object entity);

    Object buildEntity(Object model);
}
