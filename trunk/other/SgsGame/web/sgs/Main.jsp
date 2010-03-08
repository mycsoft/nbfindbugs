<%-- 
    Document   : SgsMain
    Created on : 2010-3-3, 15:45:04
    Author     : MaYichao
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags/"  prefix="sgs" %>
<%@ page import="java.util.*, com.myc.game.sgs.*" %>
<%!
    /** ��ʼ����Ϸ */
    SgsGameManager initGame() {
        SgsGameManager gm = new SgsGameManager();
        for (int idx = 0; idx < 4; idx++) {
            Player player = new Player();
            player.setName("������Ա" + idx);
            gm.getPlayers().add(player);

        }

        gm.startGame();
        return gm;
    }
%>
<%
            //ȡ����Ϸ������.
            SgsGameManager gm = (SgsGameManager) session.getAttribute("com.myc.game.sgs.SgsGameManager");
            //���û��,���ʼ����Ϸ
            if (gm == null) {
                gm = initGame();
                session.setAttribute("com.myc.game.sgs.SgsGameManager", gm);
            }

            //List<Player> players = gm.getPlayers();
            //pageContext.setAttribute("players", players);

            pageContext.setAttribute("userIdx", 3);

%>
<style>
    .play_div {
        width: 200px;
        height: 150px;
        border: 1 solid maroon;
        position: absolute
    }
    .play_div td{
        border: 1 solid gray;
        width: 25%
    }
    .user_div {
        top: 450;
        left: 0;
        width: 100%;
        height: 150px;
        border: 1 solid fuchsia;
        position: absolute
    }
    .user_info_div{
        top: 0;
        left: 0;
        width: 30%;
        height: 100%;
        border: 1 solid gray;
        position: absolute
    }
    .user_info_div td{
        border: 1 solid gray;
        width: 25%
    }
    .user_info_div.label{
        font: bold;
        font-size: smaller;
        text-align: center;
    }
    .user_info_div.value{
        font-size: smaller;
        text-align: center;
    }
</style>
<sgs:DefaultView>

    <div id="sgsRoom"
         style="position: relative;left: 0;top: 0;width: 1000px;height: 600px;border: solid gray;">
        <div id="player_div_1" class="play_div" style=" left: 0;top: 300;">
            player1
        </div>
        <div id="player_div_2" class="play_div" style=" left: 0;top: 150;">
            player2
        </div>
        <div id="player_div_3" class="play_div" style=" left: 0;top: 0;">
            player3
        </div>
        <div id="player_div_4" class="play_div" style=" left: 200;top: 0;">
            player4
        </div>
        <div id="player_div_5" class="play_div" style=" left: 400;top: 0;">
            player5
        </div>
        <div id="player_div_6" class="play_div" style=" left: 600;top: 0;">
            player6
        </div>
        <div id="player_div_7" class="play_div" style=" left: 800;top: 0;">
            player7
        </div>
        <div id="player_div_8" class="play_div" style=" left: 800;top: 150;">
            player8
        </div>
        <div id="player_div_9" class="play_div" style=" left: 800;top: 300;">
            player9
        </div>

        <div id="user_div" class="user_div">
            <div id="player_div_0" class="user_info_div">
                <table width="100%">
                    <tbody>
                        <tr>
                            <td class="label">�佫��:</td>
                            <td class="value">����</td>
                            <td class="label">��ɫ:</td>
                            <td class="value">�ҳ�</td>
                        </tr>
                        <tr>
                            <td class="label">Ѫ��:</td>
                            <td class="value">3/4</td>
                            <td class="label">������:</td>
                            <td class="value">3</td>
                        </tr>
                        <tr>
                            <td class="label">����:</td>
                            <td class="value">�������</td>
                            <td class="label">����:</td>
                            <td class="value">������</td>
                        </tr>
                        <tr>
                            <td class="label">������</td>
                            <td class="value">��Ӱ</td>
                            <td class="label">������</td>
                            <td class="value">����</td>
                        </tr>
                        <tr>
                            <td class="label" colspan="2">�����:</td>
                            <td class="value" colspan="2">WINNER</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</sgs:DefaultView>
<script>
    /**
     * ���������Ϣ
     * @param index ��ҵ����λ�ú�.
     * @param idx ��ҵ���Ե�ǰ��ҵ�λ�ú�.
     */
    function updatePlayer(idx,index) {
        log("���������Ϣ"+ idx + "|" + index);
        Ext.Ajax.request({
            url : "PlayerBox.jsp?id="+index,
            callback : function(options, success, response) {
                reflushPlayer(idx, response.responseText);
            }
        })

    }

    /**
     * ˢ�������
     */
    function reflushPlayer(index,content) {
        log("ˢ�������"+index);
        var div = "player_div_"+index;
        document.getElementById(div).innerHTML=content;
    }
    /**
     * �����������
     */
    function updateAllPlayers() {
        max = 4;
        for (i = 0;i < max ; i ++){
            updatePlayer(i,(i+${userIdx})%(max));
        }
    }

    
    updateAllPlayers();
</script>