package com.jinax.adweb_backend.Entity;

import javax.persistence.*;

/**
 * @author jinaxCai
 */
@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer userId;
    @Column(name = "`from`",nullable = false)
    private Short from;
    @Column(name = "`to`",nullable = false)
    private Short to;
    @Column(nullable = false)
    private Short plateSize;

    public Operation(Integer userId, Short from, Short to, Short plateSize) {
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.plateSize = plateSize;
    }

    public Operation(Integer id, Integer userId, Short from, Short to, Short plateSize) {
        this.id = id;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.plateSize = plateSize;
    }

    public Operation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Short getFrom() {
        return from;
    }

    public void setFrom(Short from) {
        this.from = from;
    }

    public Short getTo() {
        return to;
    }

    public void setTo(Short to) {
        this.to = to;
    }

    public Short getPlateSize() {
        return plateSize;
    }

    public void setPlateSize(Short plateSize) {
        this.plateSize = plateSize;
    }
}
