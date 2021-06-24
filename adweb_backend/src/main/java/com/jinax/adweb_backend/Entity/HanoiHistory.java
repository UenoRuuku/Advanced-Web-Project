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

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name="operation_id",referencedColumnName = "id")
    private Operation operation;

    public HanoiHistory() {
    }

    public HanoiHistory(Integer id, String firstTower, String secondTower, String thirdTower, Integer operationId, Integer gameId) {
        this.id = id;
        this.firstTower = firstTower;
        this.secondTower = secondTower;
        this.thirdTower = thirdTower;
        this.operationId = operationId;
        this.gameId = gameId;
    }

    public HanoiHistory(String firstTower, String secondTower, String thirdTower, Integer operationId, Integer gameId, Operation operation) {
        this.firstTower = firstTower;
        this.secondTower = secondTower;
        this.thirdTower = thirdTower;
        this.operationId = operationId;
        this.gameId = gameId;
        this.operation = operation;
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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
