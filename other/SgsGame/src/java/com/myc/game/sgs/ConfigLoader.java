/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.game.sgs;

import com.myc.game.sgs.Card.HandCard;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 配置装载器
 * @author MaYichao
 */
public class ConfigLoader {

    private HashMap<String, HandCardTemplate> handCardTamplateMap = null;
    /** 手牌类型,配置值与系统值对应表. */
    private static final HashMap<String,Integer> HAND_CARD_TYPE_MAP = new HashMap<String, Integer>();

    /** 手牌花色对应表 */
    private static final HashMap<String,Integer> HAND_CARD_KIND_MAP = new HashMap<String,Integer>();

    static {
        HAND_CARD_TYPE_MAP.put("kill", Card.HandCard.TYPE_KILL);
        HAND_CARD_TYPE_MAP.put("swing", Card.HandCard.TYPE_SWING);
        HAND_CARD_TYPE_MAP.put("blood", Card.HandCard.TYPE_BLOOD);

        HAND_CARD_KIND_MAP.put("red", Card.HandCard.KIND_RED);
        HAND_CARD_KIND_MAP.put("black", Card.HandCard.KIND_BLACK);
        HAND_CARD_KIND_MAP.put("rang", Card.HandCard.KIND_RANG);
        HAND_CARD_KIND_MAP.put("flower", Card.HandCard.KIND_FLOWER);
    }

    /** 手牌 */
    private ArrayList<HandCard> handCardList = null;

    /** 读取配置 */
    public void loadConfig(String url) {
        try {
            //读取所有配置.
            File xmlFile = new File(url);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            loadHandCards(document);
            //读取武将牌
            //读取武将牌
        } catch (Exception ex) {
            //Logger.getLogger(ConfigLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("装载配置文件失败.", ex);
        }
    }

    /** 装载手牌 */
    private void loadHandCards(Document document) {
        //载入手牌模板.
        NodeList handCardTamplates = document.getElementsByTagName("HAND_CARD_TEMPLATE");
        //ArrayList<HandCardTemplate> tamplates = new ArrayList<HandCardTemplate>(handCardTamplates.getLength());
        HashMap<String, HandCardTemplate> tamplates = new HashMap<String, HandCardTemplate>();
        for (int i = 0; i < handCardTamplates.getLength(); i++) {
            Node node = handCardTamplates.item(i);
            HandCardTemplate tamplate = new HandCardTemplate(node);
            tamplates.put(tamplate.id, tamplate);
        }
        handCardTamplateMap = tamplates;

        //载入手牌
        NodeList handCards = document.getElementsByTagName("HAND_CARD");
        ArrayList<HandCard> cards = new ArrayList<HandCard>(handCards.getLength());
        //HashMap<String,HandCardTemplate> car = new HashMap<String,HandCardTemplate>();
        for (int i = 0; i < handCards.getLength(); i++) {
            Node node = handCards.item(i);
            //HandCardTemplate tamplate = new HandCardTemplate(node);
            //tamplates.put(tamplate.id,tamplate);
            cards.add(loadHandCard(node));
        }
        if (cards.isEmpty()){
            throw new RuntimeException("配置文件中没有任务有效的手牌配置.");
        }
        handCardList = cards;
    }

    /** 读取单张手牌 */
    private HandCard loadHandCard(Node node) {
        HandCard card = null;
        if (node != null) {
            card = new HandCard();
            NamedNodeMap attrs = node.getAttributes();
            String kind = attrs.getNamedItem("kind").getNodeValue();
            
            card.setKind(HAND_CARD_KIND_MAP.get(kind));

            //card.setDesc(node.getNodeValue());
            //取得模板,将模板值传给手牌.
            String templateId = attrs.getNamedItem("type").getNodeValue();
            HandCardTemplate template = handCardTamplateMap.get(templateId);
            if (template == null){
                throw new RuntimeException("配置文件中没有指定对应id("+templateId+")的模板.");
            }
            template.appendInfo(card);
        }
        if (card == null) {
            throw new RuntimeException("读取指定手牌失败." + node.toString());
        }
        return card;
    }

    /**
     * 手牌
     * @return the handCardList
     */
    public ArrayList<HandCard> getHandCardList() {
        return handCardList;
    }

    final class HandCardTemplate {

        String name, desc;
        String id;
        int type;

        

        public HandCardTemplate(Node node) {
            NamedNodeMap attrs = node.getAttributes();
            name = attrs.getNamedItem("name").getNodeValue();
            desc = node.getNodeValue();
            id = attrs.getNamedItem("id").getNodeValue();
            type = HAND_CARD_TYPE_MAP.get(id);
        }

        public void appendInfo(Card card){
            HandCard hc = (HandCard)card;
            hc.setType(type);
            hc.setDesc(desc);
            hc.setName(name);
        }
    }
}
