<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/inc.jsp"%>
<%@include file="antiHack/antiHack_android.jsp" %>
<script src="${resourceRoot}/web/ybb/common/js/jquery.min.js"></script>
<script>
  var rootPath = "${ctx}";
  function Go(url, code){
  $.ajax({
      url: rootPath+"/valid/checkflatsatus",
      type: "post",
      data : {"code":code},
      contentType : "application/x-www-form-urlencoded; charset=UTF-8",
      timeout : 30000,
      dataType :"json",
      cache : false,
      success : function(obj) {
        if (obj.datas.status) {
          alert(obj.datas.msg);
          return;
        }else{
          window.location.href=url;
        }
      }
    });
  }
</script>