/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myc.game.sgs.action;

import com.myc.game.sgs.SgsBaseAction;
import com.myc.game.sgs.SgsGameManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 退出房间Action.
 * @author MaYichao
 */
public class ExitRoomAction extends SgsBaseAction {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    private static final String GM_ID = "com.myc.game.sgs.SgsGameManager";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //退出房间
        SgsGameManager gm = (SgsGameManager)request.getSession().
                getAttribute(GM_ID);
        gm.closeRoom();
        request.getSession().setAttribute(GM_ID, null);



        return mapping.findForward(SUCCESS);
    }
}
