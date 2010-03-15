<%-- 
    Document   : MainBox
    Created on : 2010-3-9, 10:26:41
    Author     : MaYichao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div id="sgsRoom1"
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
                        <td class="label">武将名:</td>
                        <td class="value">赵云</td>
                        <td class="label">角色:</td>
                        <td class="value">忠臣</td>
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
                        <td class="value" colspan="2">WINNER</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="handCardList_Div">
        </div>
    </div>

</div>