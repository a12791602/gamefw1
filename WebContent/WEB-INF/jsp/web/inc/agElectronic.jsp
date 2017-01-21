<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
			  <div class="module-notice module-notice-slots" style="margin: 15px 10px 10px 10px;color: yellow;line-height: 22px;font-size: 14px"></div>
			  <div class="hall">
			    <ul class="clear">
					<c:forEach var="item" items="${slotSite}">
						<li class="${item.flat eq code?'active':'' }"><a href="${ctx}/${item.flatUrl}">${item.flatName}</a></li>
					</c:forEach>
			    </ul>
			  </div>
			  <div class="sort clear">
			    <ul class="left clear">
			      <%-- <li><a href="${ctx}/electronic?code=gd&gameType1=Table Game">桌面游戏</a></li>
			      <li><a href="${ctx}/electronic?code=gd&gameType1=Slot Game">老虎机</a></li>
			      <li><a href="${ctx}/electronic?code=gd&gameType1=Arcade">街机游戏</a></li>
			      <li><a href="${ctx}/electronic?code=gd&gameType1=Video Poker">视频扑克</a></li> --%>
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
											<i><a target="_blank" onclick="winOpen('${ctx}/forwardGame?gameType=ag&agGameType=${item.eleGameId}',window.screen.width,window.screen.height,0,0,'game','ag');return false;" href="javascript:void(0);">开始游戏</a>
											</i>
										</c:when>
										<c:otherwise>
											<i><a href="javascript:void(0);"
												onclick="alert('您尚未登录，请先登录再进行游戏');">开始游戏</a>
											</i>
										</c:otherwise>
									</c:choose>
								</div>
								<a class="gm-ic-mg lazy" data-original="${resourceRoot}/web/ybb/common/images/ag/${item.eleGamePic}" style="background-image: url(${resourceRoot}/web/ybb/common/images/grey.gif);background-size: 100%; background-position: 0px -6px;"></a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
