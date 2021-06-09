package com.jinax.adweb_backend.Entity;

import javax.persistence.*;

/**
 * @author jinaxCai
 */
@Entity
@Table(name = "hanoi_history")
public class HanoiHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstTower;
    @Column(nullable = false)
    private String secondTower;
    @Column(nullable = false)
    private String thirdTower;
    @Column(nullable = false)
    private Integer operationId;
    @Column(nullable = false)
    private Integer gameId;

    public HanoiHistory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstTower() {
        return firstTower;
    }

    public void setFirstTower(String firstTower) {
        this.firstTower = firstTower;
    }

    public String getSecondTower() {
        return secondTower;
    }

    public void setSecondTower(String secondTower) {
        this.secondTower = secondTower;
    }

    public String getThirdTower() {
        return thirdTower;
    }

    public void setThirdTower(String thirdTower) {
        this.thirdTower = thirdTower;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}
