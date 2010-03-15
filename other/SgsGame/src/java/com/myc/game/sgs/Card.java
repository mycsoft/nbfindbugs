/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.game.sgs;

import java.util.HashMap;
import java.util.Map;

/**
 * 牌
 * @author MaYichao
 */
public abstract class Card {

    /** 说明 */
    private String name;
    private String desc;
    /** 权重,用于洗牌时用. */
    private int idx;

    /**
     * 卡片名称
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 卡片名称
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 说明
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 说明
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 权重,用于洗牌时用.
     * @return the idx
     */
    public int getIdx() {
        return idx;
    }

    /**
     * 权重,用于洗牌时用.
     * @param idx the idx to set
     */
    public void setIdx(int idx) {
        this.idx = idx;
    }

    /** 身份牌 */
    public static final class IdCard extends Card {
        //身份类型常量

        /**主公*/
        public static final int TYPE_MASTER = 100;
        /**未知*/
        public static final int TYPE_UNKNOWN = 0;
        /**忠臣*/
        public static final int TYPE_MINISTER = 90;
        /**内奸*/
        public static final int TYPE_TRAITOR = 500;
        /**反贼*/
        public static final int TYPE_THIEF = 20;
        /** 名称对应关系 */
        private Map nameMap = new HashMap();
        /**身份类型.*/
        private int type = 0;

        public IdCard(int t) {
            type = t;
            nameMap.put(TYPE_MASTER, "主公");
            nameMap.put(TYPE_MINISTER, "忠臣");
            nameMap.put(TYPE_TRAITOR, "内奸");
            nameMap.put(TYPE_THIEF, "反贼");
            setName((String) nameMap.get(type));
        }

        /**
         * 身份类型.
         * @return the type
         */
        public int getType() {
            return type;
        }

        /**
         * 身份类型.
         * @param type the type to set
         */
        public void setType(int type) {
            this.type = type;
        }
    }

    /** 武将牌 */
    public class ActCard extends Card {

        /** 血量 */
        private int life = 3;

        /**
         * 血量
         * @return the life
         */
        public int getLife() {
            return life;
        }

        /**
         * 血量
         * @param life the life to set
         */
        public void setLife(int life) {
            this.life = life;
        }
    }

    /** 手牌 */
    public static class HandCard extends Card {

        /** 杀 */
        public static final int TYPE_KILL = 10;
        /** 闪 */
        public static final int TYPE_SWING = 20;
        /** 桃 */
        public static final int TYPE_BLOOD = 30;
        /**手牌类型.*/
        private int type = 0;
        /** 花色 */
        private int kind;
        /** 红桃 */
        public static final int KIND_RED = 30;
        /** 黑桃 */
        public static final int KIND_BLACK = 40;
        /** 方片 */
        public static final int KIND_RANG = 10;
        /** 草花 */
        public static final int KIND_FLOWER = 20;
        /** 点数 */
        private int number;

        /**
         * 手牌类型.
         * @return the type
         */
        public int getType() {
            return type;
        }

        /**
         * 手牌类型.
         * @param type the type to set
         */
        public void setType(int type) {
            this.type = type;
        }

        /**
         * 花色
         * @return the kind
         */
        public int getKind() {
            return kind;
        }

        /**
         * 花色
         * @param kind the kind to set
         */
        public void setKind(int kind) {
            this.kind = kind;
        }

        /**
         * 点数
         * @return the number
         */
        public int getNumber() {
            return number;
        }

        /**
         * 点数
         * @param number the number to set
         */
        public void setNumber(int number) {
            this.number = number;
        }
    }
    /** 锦囊 */
    /** 装备 */
    /** 武器 */
    /** 防具 */
    /** +1马 */
    /** -1马 */
}
