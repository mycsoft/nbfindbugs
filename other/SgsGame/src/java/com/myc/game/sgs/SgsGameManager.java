/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.game.sgs;

import com.myc.game.sgs.Card.HandCard;
import com.myc.game.sgs.Card.IdCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 三国杀游戏管理器.
 * @author MaYichao
 */
public class SgsGameManager implements Comparator<Card> {

    /** 系统配置 */
    private static ConfigLoader configLoader = null;

    /**
     * @return the configLoader
     */
    public static ConfigLoader getConfigLoader() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
            configLoader.loadConfig(SgsGameManager.class.getResource("config.xml").getFile());
        }
        return configLoader;
    }
    private ArrayList<Player> players = new ArrayList<Player>(10);
    /** 主公ID */
    private int masterIndex = -1;
    /** 手牌堆 */
    private ArrayBlockingQueue<HandCard> handCards = null;

    public SgsGameManager() {
//        Player p1 = new Player();
//        p1.setName("开发人员");
//        p1.setIdCard(new IdCard(IdCard.TYPE_MASTER));
//        p1.setActCard(new ActCard());
//        p1.getActCard().setName("赵云");
//        p1.getActCard().life = 4;
//        players.add(p1);
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /** 开始游戏 */
    public void startGame() {
        //分配角色
        distIdCard();
        //主公选择武将
        //分配武将
        //发起始手牌.
        distStartHandCard();
    }

    /** 分配角色 */
    private void distIdCard() {
        //当前玩家人数
        int playerCount = players.size();
        if (playerCount < 4) {
            throw new RuntimeException("玩家人数不可少于4人.当前人数为" + playerCount);
        }
        ArrayList<IdCard> idCards = new ArrayList<IdCard>(playerCount);
        Random random = new Random(System.currentTimeMillis() + playerCount);
        switch (playerCount) {
            case 10:
                IdCard idCd = new IdCard(IdCard.TYPE_TRAITOR);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);
            case 9:
                idCd = new IdCard(IdCard.TYPE_MINISTER);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

            case 8:
                idCd = new IdCard(IdCard.TYPE_THIEF);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

            case 7:
                idCd = new IdCard(IdCard.TYPE_MINISTER);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

            case 6:
                idCd = new IdCard(IdCard.TYPE_THIEF);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

            case 5:
                idCd = new IdCard(IdCard.TYPE_THIEF);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

            default:
                idCd = new IdCard(IdCard.TYPE_MASTER);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

                idCd = new IdCard(IdCard.TYPE_MINISTER);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

                idCd = new IdCard(IdCard.TYPE_THIEF);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);

                idCd = new IdCard(IdCard.TYPE_TRAITOR);
                idCd.setIdx(random.nextInt(playerCount));
                idCards.add(idCd);
        }
        //洗牌
        Collections.sort(idCards, this);

        //将角色牌分给各人.
        for (int i = 0; i < playerCount; i++) {
            Player player = players.get(i);
            player.setIdCard(idCards.get(i));
            if (player.getIdCard().getType() == IdCard.TYPE_MASTER) {
                setMasterIndex(i);
            }

        }

    }

    /** 发起始手牌 */
    private void distStartHandCard() {

        handCards = new ArrayBlockingQueue<HandCard>(1000, true, getConfigLoader().getHandCardList());
        //TODO 洗牌.
        //每人发4张牌.
        for (Iterator<Player> it = players.iterator(); it.hasNext();) {
            Player player = it.next();
            for (int i = 0; i < 4; i++) {
                player.addHandCard(pollHandCard());
            }

        }
    }

    /** 抽牌,如果牌不够则重洗弃牌堆后,加入到手牌区,再抽. */
    private HandCard pollHandCard() {
        if (handCards == null) {
            handCards = new ArrayBlockingQueue<HandCard>(1000, true, getConfigLoader().getHandCardList());
        } else if (handCards.isEmpty()) {
            //TODO 重洗弃牌.
        }
        HandCard card = handCards.poll();
        return card;
    }

    public int compare(Card o1, Card o2) {
        return o1.getIdx() - o2.getIdx();
    }

    /**
     * 主公ID
     * @return the masterIndex
     */
    public int getMasterIndex() {
        return masterIndex;
    }

    /**
     * 主公ID
     * @param masterIndex the masterIndex to set
     */
    public void setMasterIndex(int masterIndex) {
        this.masterIndex = masterIndex;
    }

    /** TODO 退出游戏 */
    public void exitRoom(int playId){
        
    }

    /** 关闭房间 */
    public void closeRoom(){
        //TODO 如果当前还有用户在线,如何处理?

        configLoader = null;
    }
//    /** 卡片 */
//    class Card {
//
//        /** 说明 */
//        private String name;
//        private String desc;
//        /** 权重,用于洗牌时用. */
//        private int idx;
//
//        /**
//         * 卡片名称
//         * @return the name
//         */
//        public String getName() {
//            return name;
//        }
//
//        /**
//         * 卡片名称
//         * @param name the name to set
//         */
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        /**
//         * 说明
//         * @return the desc
//         */
//        public String getDesc() {
//            return desc;
//        }
//
//        /**
//         * 说明
//         * @param desc the desc to set
//         */
//        public void setDesc(String desc) {
//            this.desc = desc;
//        }
//
//        /**
//         * 权重,用于洗牌时用.
//         * @return the idx
//         */
//        public int getIdx() {
//            return idx;
//        }
//
//        /**
//         * 权重,用于洗牌时用.
//         * @param idx the idx to set
//         */
//        public void setIdx(int idx) {
//            this.idx = idx;
//        }
//    }
}
