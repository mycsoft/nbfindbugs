<%-- 
    Document   : PlayerBox
    Created on : 2010-3-8, 10:52:14
    Author     : MaYichao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.myc.game.sgs.*" %>
<%
            //取得游戏管理器.
            SgsGameManager gm = (SgsGameManager) session.getAttribute("com.myc.game.sgs.SgsGameManager");
            int id = Integer.valueOf(request.getParameter("id"));

            List<Player> players = gm.getPlayers();
            pageContext.setAttribute("players", players);
            pageContext.setAttribute("player",players.get(id));
%>
<table width="100%">
    <tbody>
        <tr>
            <td class="label">武将名:</td>
            <td class="value">${player.actCard.name}</td>
            <td class="label">角色:</td>
            <td class="value">${player.idCard.name}</td>
        </tr>
        <tr>
            <td class="label">血量:</td>
            <td class="value">3/4</td>
            <td class="label">手牌数:</td>
            <td class="value">3</td>
        </tr>
        <tr>
            <td class="label">武器:</td>
            <td class="value">诸葛连弩</td>
            <td class="label">防具:</td>
            <td class="value">八卦阵</td>
        </tr>
        <tr>
            <td class="label">防御马</td>
            <td class="value">飞影</td>
            <td class="label">进攻马</td>
            <td class="value">赤兔</td>
        </tr>
        <tr>
            <td class="label" colspan="2">玩家名:</td>
            <td class="value" colspan="2">${player.name}</td>
        </tr>
    </tbody>
</table>