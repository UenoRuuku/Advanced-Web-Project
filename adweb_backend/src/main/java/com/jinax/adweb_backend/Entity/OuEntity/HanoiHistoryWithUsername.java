package com.jinax.adweb_backend.Entity.OuEntity;

import javax.persistence.Column;

/**
 * @author : chara
 */
public class HanoiHistoryWithUsername {
    private Integer id;
    private String firstTower;
    private String secondTower;
    private String thirdTower;
    private Integer operationId;
    private Integer gameId;
    private String username;

    public HanoiHistoryWithUsername() {
    }

    public HanoiHistoryWithUsername(Integer id, String firstTower, String secondTower, String thirdTower, Integer operationId, Integer gameId, String username) {
        this.id = id;
        this.firstTower = firstTower;
        this.secondTower = secondTower;
        this.thirdTower = thirdTower;
        this.operationId = operationId;
        this.gameId = gameId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
