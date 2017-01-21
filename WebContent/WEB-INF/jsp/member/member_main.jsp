<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
  <!DOCTYPE html>
  <html class="no-js" lang="zh-CN">

  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>会员中心</title>
    <meta name="description" content="">
    <%@include file="/commons/member/jsp/member_common.jsp"%>
    
    <script>
		function resizeIframe(obj) {
			obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
		}
    </script>
    
  </head>

  <body class="${theme }">
    <div class="wrapper">
      <div class="panel-header">
        <div class="promo-nav-bg"></div>
        <div class="inner clear">
            <div class="user-info clearfix">
                <img class="left" src="${resourceRoot }/member/images/user-avatar.png" alt="" class="avatar" />
                <div class="ui-wrap left">
                    <ul class="c1">
                        <li><label>用户名：</label>${webUser.userName}</li></li>
		                <li><label>姓名：</label>${webUser.userRealName}</li>
			            <li><label>等级：</label><font color="red">${userType.typeLevel}</font></li>
                    </ul>
                    <ul class="c2">
                        <li><label>余额：</label><span>${webUser.userMoney}</span></li>
                    </ul>
                    <ul class="c3">
                        <li class="security-tips">
			                <h6 class=""><strong>上次登录信息：</strong> <a href="javascript:void(0);" onclick="goback('${ctx}/member/goPwdMain')" class="link-more block" style=" display: none">修改密码</a></h6>
			                <h6 class="">时间：<span class="font-hei "><fmt:formatDate value="${uc.lastLogonTime}" pattern="yyyy/MM/dd HH:mm:ss"/></span></h6>
			                <h6 class="">IP地址：<span class="font-hei ">${uc.ip}</span></h6>
			           </li>
                    </ul>
                </div>
                <div class="ui-links right">
                    <ul class="c4">
                        <li>
                            <h3>Member Center</h3>
                        </li>
                        <li class="timer">
                            <label>美东时间: </label> <div class="nav-timer" plugin-timer="-5.0"></div>
                        </li>
                        <li>
                            <a class="back-home" href="${ctx}/member/main">会员中心首页</a>
                            <a class="promo-link" href="${ctx}/goPageCenter?code=promos">优惠活动</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="promo-nav">
            	<div class="promo-nav-wrap clearfix">
            	   <div class="promo-notice-con left">
	            	   <div class="slide-notice">
		                <div class="bd left">
		                  <div class="mt5" style="font-size: 14px">
		                    <ul>
		                      <c:forEach var="item" items="${gongGaoList}" varStatus="item_index">
		                        <li>${item.ggName}</li>
		                      </c:forEach>
		                    </ul>
		                  </div>
		                </div>
			           </div>
            	   </div>
                   <div class="promo-slider-con right">
                     <div class="panel-head-promo slide-paging">
			            <li class="item item-2 ">
			              <div class="body slide-paging-body" style="font-size: 14px">
			                <ul>
			                  <c:forEach var="t" items="${lbPromoList}" varStatus="s">
			                    <li>${t.pmsTitle }， ${t.pmsSubTitle }</li>
			                  </c:forEach>
			                </ul>
			              </div>
			            </li>
			          </div>
                   </div>
            	</div>
            </div>
        </div>
        <div class="inner clear" style="display: none;">
        
          <div class="panel-player-info left clear">
            <div class="c1 left">
              <a  onclick="goback('${ctx}/member/goUserInfo?id=${webUser.id}')" href="javascript:void(0);" class="block">
                <img src="${ctx}/member/getUserTypeImg?id=${webUser.userType}" width="100" height="100" alt="">
                <div class="panel-player-more"></div>
                <div class="panel-player-txt">查看资料</div>
              </a>
            </div>
            <ul class="c2 left">
              <li>
                <label>用户名：</label>${webUser.userName}</li>
              <li>
                <label>姓　名：</label>${webUser.userRealName}</li>
              <li>
                <label>等　级：</label>
                <font color="red">${userType.typeLevel}</font>
              </li>
              <li>
                <label>余　额：</label>￥${webUser.userMoney}</li>
            </ul>
            <div class="gray-line"></div>
          </div>
          <div class="panel-player-security left">
            <ul>
              <li class="panel-inc-msg mt5 gray">
                <a onclick="goback('${ctx}/message/listForUser')" href="javascript:void(0);" title="查看 站内消息" class="block">您有<span class="ml5 mr5 red" id="userMessageId">${msgTotal}</span>封未读新消息</a>
                <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="150" height="26" id="msgClass" style="display: none">
                  <param name="movie" value="${resourceRoot}/member/images/msg-remind.swf">
                  <param name="wmode" value="transparent">
                  <param name="quality" value="high">
                  <embed src="${resourceRoot}/member/images/msg-remind.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" wmode="transparent" width="150" height="26
  ">
                </object>
              </li>
              <li class="security-tips gray-dark">
                <h6 class="mt10">
                <strong>上次登录信息：</strong> <a href="javascript:void(0);" onclick="goback('${ctx}/member/goPwdMain')" class="link-more block" style=" display: none">修改密码</a>
              </h6>
                <p class="mt10">
                  时间：<span class="font-hei red">

                  <fmt:formatDate value="${uc.lastLogonTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
                </span>
                </p>
                <div class="mt5">
                  IP地址：<span class="font-hei red">${uc.ip}</span>
                </div>
              </li>
            </ul>
            <div class="gray-line"></div>
          </div>
          <div class="panel-head-promo slide-paging left">
            <li class="item item-2 ">
              <h6 class="gray">
                                            最新优惠    <a href="${ctx}/goPageCenter?code=promos" class="link-more block">更多优惠</a>
              </h6>
              <div class="body slide-paging-body" style="font-size: 14px">
                <ul>
                  <c:forEach var="t" items="${lbPromoList}" varStatus="s">
                    <li>${t.pmsTitle }， ${t.pmsSubTitle }</li>
                  </c:forEach>
                </ul>
              </div>
              <ul class="slide-paging-group clear"></ul>
              <span class="em em-left font-hei s40 gray"></span><span class="em em-right font-hei s40 gray"></span>
            </li>
          </div>
          <div class="member-info left clear" style="display: none;">
            <div class="welcome left center">
              <div class="ih-item circle effect">
                <a href="javascript:void(0);">
                  <div class="spinner"></div>
                  <div class="icon">
                    <script>
                    var now = (new Date()).getHours();
                    if (now > 0 && now <= 6) {
                      document.write("<div class='icon-text center gray'>午夜好，</div><div class='icon-time flaticon-crescent33'></div>");
                    } else if (now > 6 && now <= 11) {
                      document.write("<div class='icon-text center gray'>早上好，</div><div class='icon-time flaticon-sunny27'></div>")
                    } else if (now > 11 && now <= 14) {
                      document.write("<div class='icon-text center gray'>中午好，</div><div class='icon-time flaticon-mountain5'></div>");
                    } else if (now > 14 && now <= 18) {
                      document.write("<div class='icon-text center gray'>下午好，</div><div class='icon-time flaticon-sunset9'></div>");
                    } else {
                      document.write("<div class='icon-text center gray'>晚上好，</div><div class='icon-time flaticon-crescent19'></div>");
                    }
                    </script>
                  </div>
                  <div class="info">
                    <div class="info-back font-hei">
                      <h3 class="s24">会员中心</h3>
                      <p class="gray">点击前往</p>
                    </div>
                  </div>
                </a>
              </div>
            </div>
            <!-- /welcome -->
            <div class="info left ml10">
              <h2 class="mt20 font-hei s20 gray-dark">${webUser.userName}</h2>
              <ul class="panel-head-user">
                <li class="panel-inc-msg mt5 gray">
                </li>
              </ul>
              <div class="security-tips gray-dark">
              </div>
            </div>
            <!-- /info -->
          </div>
          <div class="member-balance left" style="display: none;">
            <ul class="clear">
              <li class="item item-1">
                <h6 class="ml5 gray">
                账户余额 <a href="##" onclick="goback('${ctx}/member/goPayBank')" class="link-more">我要充值</a>
              </h6>
                <div class="mt10 font-hei s30" id="userMoneyId"></div>
                <audio id="player" src="${resourceRoot}/member/images/music.mp3" type="audio/mp3"></audio>
                <script type="text/javascript">
                var msgTotal = ${msgTotal};
                initMsgCount = msgTotal;
                if (msgTotal != 0) {
                  //声音提示消息
                  $("#msgClass").show();
                  var player = document.getElementById("player");
                  player.play();
                }
                </script>
              </li>
              <!-- /item -->
              <li class="item item-2 slide-paging">
                <h6 class="gray">
                最新优惠 <a href="${ctx}/goPageCenter?code=promos" class="link-more block">更多优惠</a>
              </h6>
                <div class="body slide-paging-body" style="font-size: 14px">
                  <ul>
                    <c:forEach var="t" items="${lbPromoList}" varStatus="s">
                      <li>${t.pmsTitle }， ${t.pmsSubTitle }</li>
                    </c:forEach>
                  </ul>
                </div>
                <ul class="slide-paging-group clear"></ul>
                <span class="em em-left font-hei s40 gray"></span><span class="em em-right font-hei s40 gray"></span>
              </li>
              <!-- /item -->
            </ul>
          </div>
          <!-- /balance -->
        </div>
      </div>
      <!-- /header -->
      <div class="panel-container">
        <div class="switch-page switch-load-mod inner font-hei clear">
	        <div class="service-online">
	            <a href="javascript:void(0);" class="btn-contact online-service-link">在线客服</a>
	        </div>
            <div class="back-home-link">
                <a href="${ctx }/" class="online-service-link">返回首页</a>
            </div>
          <div class="panel-sidebar left">
            <ul class="switch-name switch-load-name">
              <li class="item" style=" display: none"></li>
              <li class="title white finance-icon">
                <h2 class="pt15 s24">财务中心</h2>
                <h3 class="mt5">取款急速处理</h3>
              </li>
              <li class="item icon-1" onclick="goback('${ctx}/member/payOnline')">在线支付</li>
              <c:if test="${!empty wxkjPay}">
              	<li class="item icon-2" onclick="goback('${ctx}/member/goWxPay?paytype=${wxkjPay.payType }')">微信支付</li>
              </c:if>
              <c:if test="${!empty alikjPay}">
              	<li class="item icon-16" onclick="goback('${ctx}/member/goWxPay?paytype=${alikjPay.payType }')">支付宝支付</li>
              </c:if>
              
              <c:if test="${!empty wxthird}">
              	<li class="item icon-3" onclick="goback('${ctx}/member/paykjOnline?paytype=${wxthird.payType }&payid=${wxthird.thirdPayId }')">${wxthird.payName }</li>
              </c:if>
              <c:if test="${!empty alithird}">
              	<li class="item icon-4" onclick="goback('${ctx}/member/paykjOnline?paytype=${alithird.payType }&payid=${alithird.thirdPayId }')">${alithird.payName }</li>
              </c:if>
              <li class="item icon-5 <c:if test=" ${type=='1' } ">on</c:if>" onclick="goback('${ctx}/member/goPayBank')">公司入款</li>
              <li class="item icon-6 <c:if test=" ${type=='2' } ">on</c:if>" onclick="goback('${ctx}/member/goWithdraw')">提现到账户</li>
              <li class="item icon-7 <c:if test=" ${type=='3' } ">on</c:if>" onclick="goback('${ctx}/member/goEdu')">额度转换</li>
              <li class="title white finance-report-icon">
                <h2 class="pt15 s24">财务报表</h2>
                <h3 class="mt5">优惠时时结算</h3>
              </li>
              <li class="item icon-8" onclick="goback('${ctx}/member/goList?code=withdrawHistory')">提现记录</li>
              <li class="item icon-9" onclick="goback('${ctx}/member/goList?code=payHistory')">充值记录</li>
              <li class="item icon-10" onclick="goback('${ctx}/member/goList?code=eduHistory')">转换记录</li>
              <li class="item icon-11 <c:if test=" ${type=='4' } ">on</c:if>" onclick="goback('${ctx}/records/goList?code=ag')">投注记录</li>
              <li class="title white user-center-icon">
                <h2 class="pt15 s24">会员信息</h2>
                <h3 class="mt5">完成绑定方便快捷取款</h3>
              </li>
              <li class="item icon-12 <c:if test=" ${type=='5' } ">on</c:if>" onclick="goback('${ctx}/message/listForUser')">站内信息</li>
              <li class="item icon-13" onclick="goback('${ctx}/member/goUserInfo?id=${webUser.id}')">个人资料</li>
              <li class="item icon-14" onclick="goback('${ctx}/member/goPwdMain')">密码安全</li>
              <li class="title white user-agent-icon">
                <h2 class="pt15 s24">代理专区</h2>
                <h3 class="mt5">让财富来的更猛烈一些</h3>
              </li>
              <li class="item icon-15" onclick="goback('${ctx}/member/agentDetail')">代理申请</li>
            </ul>
          </div>
          <!-- /sidebar -->
          <div class="panel-content left">
            <div class="goback hideme">
              <div class="slide-notice left clear">
                <div class="bd left">
                  <h2 class="gray" style="font-size: 14px">最新公告：</h2>
                  <div class="mt5" style="font-size: 14px">
                    <ul>
                      <c:forEach var="item" items="${gongGaoList}" varStatus="item_index">
                        <li>${item.ggName}</li>
                      </c:forEach>
                    </ul>
                  </div>
                </div>
                <ul class="hd right">
                </ul>
                <div class="slide-ctrl">
                  <a class="next1 block"></a>
                  <a class="prev1 block"></a>
                </div>
              </div>
              <a href="${ctx}/member/main" class="right mt20 mr20 block white">返回我的个人中心</a>
            </div>
            <iframe id="memberFrame" name="memberFrame" src="${ctx}/${backUrl}" scrolling="no" frameborder="0" width="100%" height="850" onload="resizeIframe(this)" allowtransparency="true">
            </iframe>
            <!-- /group -->
          </div>
          <!-- /content -->
        </div>
      </div>
      <!-- /container -->
    </div>
    <!-- /wrapper -->
<!-- 會員通知 -->
<div class="yb-panel-dialog" style="display: none;">
  ${userType.msgContent}
</div>
<script type="text/javascript">

setInterval('refreshUserMoney()', 10000);
var contentStruts = "${userType.contentStatus}"; //弹框判断
if (contentStruts == "1")
{
  //$(".yb-panel-dialog").show();
 	// 會員通知
	layer.open({
	  type: 1,
	  shift: 2,
	  shadeClose: true,
	  area: '500px',
	  content: $('.yb-panel-dialog')
	});
}
jQuery('.slide-paging').slide({
  titCell: '.slide-paging-group',
  mainCell: '.slide-paging-body ul',
  autoPage: true,
  autoPlay: true,
  vis: 1,
  trigger: 'click'
});
jQuery('.slide-notice').slide({
  titCell: '.hd',
  mainCell: '.bd ul',
  autoPlay: true,
  autoPage: true,
  prevCell: '.prev1',
  nextCell: '.next1',
  trigger: 'click'
});
</script>
<!-- /會員通知 -->
  </body>

  </html>
