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
    @Column(nullable = false)
    private Short from;
    @Column(nullable = false)
    private Short to;
    @Column(nullable = false)
    private Short plateSize;
}
