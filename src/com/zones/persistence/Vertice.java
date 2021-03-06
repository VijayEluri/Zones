package com.zones.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.avaje.ebean.validation.NotNull;


/*
@Entity
@Table( name = "zones_vertices", 
        uniqueConstraints=
            @UniqueConstraint(columnNames={"id", "vertexorder"})
) */
public class Vertice {
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id")
    private Zone zone;
    
    @Column(insertable = false, updatable = false)
    private int id;

    private int vertexorder;
    
    private int x;
    
    private int z;
    
    
    public static Vertice from(Zone zone, ResultSet set) throws SQLException {
        Vertice v = new Vertice();
        v.setZone(zone);
        v.setVertexorder(set.getInt("vertexorder"));
        v.setX(set.getInt("vertexx"));
        v.setZ(set.getInt("vertexz"));
        return v;
    }

    public Zone getZone() {
        return zone;
    }

    public int getId() {
        return id;
    }

    public int getVertexorder() {
        return vertexorder;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
        this.id = zone.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVertexorder(int vertexorder) {
        this.vertexorder = vertexorder;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
