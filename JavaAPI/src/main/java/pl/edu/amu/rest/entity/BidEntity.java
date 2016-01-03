package pl.edu.amu.rest.entity;

import javax.persistence.*;
import javax.ws.rs.core.Link;

/**
 * Created by Altenfrost on 2015-12-29.
 */
@Table
public class BidEntity {

    private String title;

    private String description;

    private Link picture_path;


}
