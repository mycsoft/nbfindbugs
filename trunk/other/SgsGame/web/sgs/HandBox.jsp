<%-- 
    Document   : HandBox
    Created on : 2010-3-9, 16:06:40
    Author     : MaYichao
--%>

<%@page contentType="text/xml" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.myc.game.sgs.*,com.myc.game.sgs.*" %>
<root>
    <%
                //取得游戏管理器.
                SgsGameManager gm = (SgsGameManager) session.getAttribute("com.myc.game.sgs.SgsGameManager");
                //int id = Integer.valueOf(request.getParameter("id"));
                int id = (Integer)session.getAttribute("com.myc.game.sgs.userIdx"); 


                List<Player> players = gm.getPlayers();
                pageContext.setAttribute("players", players);
                Player player = players.get(id);
                pageContext.setAttribute("player", player);
                pageContext.setAttribute("handCardCount", player.getHandCards().size());
                //List<Card.HandCard> hcs =
                for (Iterator<Card.HandCard> it = player.getHandCards().iterator(); it.hasNext();) {
                    Card.HandCard card = it.next();
                    %>
                    <Card name="<%=card.getName()%>"/>
    <%
                }
    %>


</root>