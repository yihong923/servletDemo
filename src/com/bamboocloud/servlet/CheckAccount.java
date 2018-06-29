package com.bamboocloud.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bamboocloud.im.service.integration.object.ApiRequest4PullTask;

public class CheckAccount extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8522569345652532609L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		AccountBean account = new AccountBean();
		String username = req.getParameter("userNm");
		String pwd = req.getParameter("pwd");
		Object bBCloudBAMSession = req.getParameter("BBCloudBAMSession");
		System.out.println("bBCloudBAMSession:" + bBCloudBAMSession);
		account.setPassword(pwd);
		account.setUsername(username);
		/*if ((username != null) && (username.trim().equals("jsp"))) {
			if ((pwd != null) && (pwd.trim().equals("1"))) {*/
				System.out.println("success");
				session.setAttribute("account", account);
				session.setAttribute("BBCloudBAMSession", bBCloudBAMSession);
				String login_suc = "success.jsp";
				resp.sendRedirect(login_suc);
				//req.getRequestDispatcher("/user").forward(req, resp);
				return;
			/*}
		}
		String login_fail = "fail.jsp";
		resp.sendRedirect(login_fail);
		return;*/
	}
	
	
}
