<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true"%>

<div class="row">
	<div class="wrapper">
		<div class="content-top-banner"></div>
	</div>
</div>

<div class="row">
	<div class="wrapper">
		<div class="header-msg">
			<%@include file="msg.jsp" %>
		</div>
	</div>
</div>

<div class="row">
	<div class="wrapper">
		<div class="content-body">
			<div class="ybb-slot-bd w1000">
				<div class="hall">
					<ul class="clear">
						<c:forEach var="item" items="${slotSite }">
						<li class="${ item.flat eq  code ?  'active': '' }">
							<a href="${ctx}/${item.flatUrl}" class="block">${item.flatName }</a>
						</li>
						</c:forEach>
					</ul>
				</div>
				<div class="sort cf">
					<ul class="l cf">
						<%-- <li><a href="${ctx}/electronic?code=os&gameType1=Slots">老虎机</a>
						</li>
						<li><a href="${ctx}/electronic?code=os&gameType1=Card">纸牌游戏</a>
						</li>
						<li><a href="${ctx}/electronic?code=os&gameType1=Table">桌面游戏</a>
						</li>
						<li><a href="${ctx}/electronic?code=os&gameType1=Video">电玩扑克</a>
						</li>
						<li><a href="${ctx}/electronic?code=os&gameType1=Scratch">街机游戏</a>
						</li> --%>
						<c:forEach var="item" items="${electronicClass}">
					      <li><a ${item.eleType eq 'Pot' ? "style='font-weight: bold; color: #ff0;'" : '' } href="${ctx}/electronic?code=${item.eleFlat }&gameType1=${item.eleType}">${item.eleTypeName }</a></li>
					    </c:forEach>
					</ul>
				</div>
				<div class="group">
					<div class="section clear">
						<c:forEach var="item" items="${result}" varStatus="status">
							<div class="item">
								<div class="title abs">
									<h6>${item.eleGameCnname}</h6>
									<div class="bg-black abs"></div>
								</div>
								<div class="overlay abs">
									<c:choose>
										<c:when test="${!empty webUser}">
											<i><a
												onclick="winOpen('${ctx}/forwardGame?gameType=osElectronicReal&gameName=${item.eleGameId}',window.screen.width,window.screen.height,0,0,'game','os');return false;"
												href="javascript:void(0);" target="_blank">开始游戏</a> | <a
												onclick="comeToOpen('${ctx}/test/forwardGame?gameType=osElectronicFun&gameName=${item.eleGameId}',window.screen.width,window.screen.height,0,0,'game','os');return false;"
												href="javascript:void(0);" target="_blank">免费试玩</a>
											</i>
										</c:when>
										<c:otherwise>
											<i><a onclick="alert('您尚未登录，请先登录再进行游戏')"
												href="javascript:void(0);" target="_blank">开始游戏</a> | <a
												onclick="comeToOpen('${ctx}/test/forwardGame?gameType=osElectronicFun&gameName=${item.eleGameId}',window.screen.width,window.screen.height,0,0,'game','os');return false;"
												href="javascript:void(0);" target="_blank">免费试玩</a>
											</i>
										</c:otherwise>
									</c:choose>
								</div>
								<img class="lazy" width="230" height="150"
									data-original="${resourceRoot}/web/ybb/common/images/os/${item.eleGamePic}"
									src="${resourceRoot}/web/ybb/common/images/gray.gif" />
							</div>
						</c:forEach>
					</div>
				</div>
			</div>

		</div>

	</div>
</div>

