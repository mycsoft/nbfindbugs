/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myc.game.sgs;

import com.myc.game.sgs.Card.ActCard;
import com.myc.game.sgs.Card.HandCard;
import com.myc.game.sgs.Card.IdCard;
import java.util.LinkedList;

/**
 * 玩家
 * @author MaYichao
 */
public class Player {
    private String name;
    /** 身份 */
    private IdCard idCard;
    private ActCard actCard;
    /** 当前血量 */
    private int life;
    /** 血量上限 */
    private int lifeMax;
    /** 手牌 */
    private LinkedList<HandCard> handCards = new LinkedList<HandCard>();

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 武将牌
     * @return the actCard
     */
    public ActCard getActCard() {
        return actCard;
    }

    /**
     * 武将牌
     * @param actCard the actCard to set
     */
    public void setActCard(ActCard actCard) {
        this.actCard = actCard;
    }

    /**
     * 身份
     * @return the idCard
     */
    public IdCard getIdCard() {
        return idCard;
    }

    /**
     * 身份
     * @param idCard the idCard to set
     */
    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    /**
     * 当前血量
     * @return the life
     */
    public int getLife() {
        return life;
    }

    /**
     * 当前血量
     * @param life the life to set
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * 血量上限
     * @return the lifeMax
     */
    public int getLifeMax() {
        return lifeMax;
    }

    /**
     * 血量上限
     * @param lifeMax the lifeMax to set
     */
    public void setLifeMax(int lifeMax) {
        this.lifeMax = lifeMax;
    }
    /** 增加一张手牌 */
    public void addHandCard(HandCard card) {
        getHandCards().add(card);
    }

    /**
     * 手牌
     * @return the handCards
     */
    public LinkedList<HandCard> getHandCards() {
        return handCards;
    }
    //状态
}
