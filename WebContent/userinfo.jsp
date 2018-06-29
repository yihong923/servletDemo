<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>

<%!
	private static String getText(String httpUrl) {
		String getText = "";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(httpUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			int rsp = connection.getResponseCode();
			if (rsp == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					getText = getText + inputLine;
				}
				in.close();
			} else if (rsp == HttpURLConnection.HTTP_UNAUTHORIZED ) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					getText = getText + inputLine;
				}
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return getText;
	}

%>
<%!
	private JSONArray getJsonArray(JSONObject jsonObject) {
		return jsonObject.getJSONArray("attributes").getJSONObject(0).getJSONArray("values");
	}
%>
<%!
	private static String getTokenFromCookie(HttpServletRequest request) {
		String cookieToken = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if (COOKIE_NAME.equalsIgnoreCase(cookie.getName())) {
					if (!"".equalsIgnoreCase(cookie.getValue())) {
						cookieToken = cookie.getValue();
					}
				}
			}
		}
		return cookieToken;
	}
%>
<%!
	//常量
	private static String COOKIE_NAME = "BBCloudBAMSession";
	private static String APP_ID = "PMS"; //由统一用户认证系统提供
	private static String APP_KEY = "PMSPOC"; //由统一用户认证系统提供
	private static String REDIRECT_URL = "http://apphub.lsbank.com:38080/apphub/login"; //统一登陆入口登陆地址
	private static String AM_REST_IS_TOKEN_VALID = "http://bam.lsbank.com:28080/bam/identity/json/isTokenValid?tokenid=";//验证token是否有效地址
	private static String AM_REST_ATTR = "http://bam.lsbank.com:28080/bam/identity/json/attributesapi?tokenid=";
%>
<%

	String accountName = "";
	String token = "";
	JSONObject jsonObjectValid;
	JSONObject jsonObjectAccount;
	Boolean isValid = false;
	String requesttoken = request.getParameter(COOKIE_NAME);
	if (requesttoken != null && !requesttoken.equals("")) { 
		System.out.println("----requesttoken is: " + requesttoken + "-------------");
		token = requesttoken;
	} else {
		String cookieToken = getTokenFromCookie(request);
		System.out.println("-----cookietoken is: " + cookieToken + "-------------");
		token = cookieToken;
	}
	if (token != null && !token.equals("")) {
		String isTokenValidUrl = AM_REST_IS_TOKEN_VALID + token;
		String verifyData = getText(isTokenValidUrl);
		jsonObjectValid = JSONObject.fromObject(verifyData);
		System.out.println("----------------jsonObject is:" + jsonObjectValid + "---------------");
		if (jsonObjectValid.has("boolean")) {
			if (jsonObjectValid.getBoolean("boolean")) {
				isValid = true;
			}
			System.out.println("----------------isValid is:" + isValid + "--------------");
		}
		if (isValid) {
			String getAttrUrl = AM_REST_ATTR + token + "&attributenames=accountName&app_id="+APP_ID+"&app_key="+APP_KEY;
			String attrData = getText(getAttrUrl);
			jsonObjectAccount = JSONObject.fromObject(attrData);
			if (jsonObjectAccount.getJSONArray("attributes").isArray()) {
				JSONArray accountJsonArray = getJsonArray(jsonObjectAccount);
				accountName = accountJsonArray.getString(0);
				//这里的得到的是应用系统的账号，后续认证逻辑应用系统自身完成，一般的处理方法是根据账号去数据库中查，
				// 若存在进入应用系统index界面，若不存在抛出异常。(依据各个应用系统的认证逻辑而定，这里只提供参考)
				request.getSession().setAttribute("ssousername", accountName);
  				response.sendRedirect("loginController.do?ssologin");
			}
		} else {
			response.sendRedirect(REDIRECT_URL);
		}
	} else {
		response.sendRedirect(REDIRECT_URL);
	}
%>

<html>
  <head>
    <title></title>
  </head>
  <body>
  <%--   <div>
      <br>App Demo, welcome ( <%=getAccountName%> )<br>
      <br><a onclick=javascript:alert('<%=getText(AM_REST_IS_TOKEN_VALID + sso_token + "&appid=testSDK&clientIp=" + client_ip)%>') href="#">Validate Token</a><br>
      <br><a onclick=javascript:alert('<%=getText(AM_REST_ATTR + sso_token + "&attributenames=cn&attributenames=testSDK&appid=testSDK&clientIp=" + client_ip)%>') href="#">Get User Attributes</a><br>
      <br><a	href='logout.html'>Logout</a>
    </div> --%>
  </body>
</html>
