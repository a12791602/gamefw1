angular.isEmpty=function(obj){if(angular.isArray(obj))return obj.length<=0;if(angular.isObject(obj)){var keys=Object.keys(obj);return 0==keys.length}return angular.isString(obj)?0==obj.length:!!angular.isNumber(obj)&&0==obj},Date.prototype.toDateInputValue=function(){var local=new Date(this);return local.toJSON().slice(0,10)},Date.prototype.toPreWeekInputValue=function(){var timestamp=(new Date).getTime(),local=new Date(Math.floor(timestamp)-6048e5);return local.toJSON().slice(0,10)};var app=angular.module("App",["ui.router","AppDirective","AppController","AppService","AppFilter","AppTemplate","ngDialog"]).config(function($stateProvider,$urlRouterProvider,$httpProvider,ngDialogProvider){$httpProvider.defaults.cache=!0,$httpProvider.responseInterceptors.push(["$q",function($q){return function(promise){return promise.then(function(response){var data=response.data;return"string"==typeof data&&0==data.search(/^\<script\>window\.top\.location/)&&(alert("登录失败 请重新登录"),setTimeout(function(){window.location.reload()},1500)),response})}}]),ngDialogProvider.setDefaults({plain:!0,width:"90%",className:"ng-dialog-conversion-in mh-ngdialog-theme"}),$urlRouterProvider.otherwise("/home"),$stateProvider.state("home",{url:"/home",controller:"HomeController",templateUrl:templateBaseURI+"/home.html"}).state("thirdpay",{url:"/thidpay/:type",params:{type:null,payRname:null,payNo:null,payNname:null},controller:"ThirdpayController",templateUrl:templateBaseURI+"/thirdpay.html"}).state("thirdpay_deposit_form",{url:"/thirdpay/form/",params:{type:null,payid:null,payValue:null},controller:"ThirdpayDepositFormController",templateUrl:templateBaseURI+"/thirdpay-deposit-form.html"}).state("thirdpay_deposit_form1",{url:"/thirdpay/form1/",params:{type:null,payRname:null,payNo:null,payNname:null},controller:"ThirdpayDepositFormController",templateUrl:templateBaseURI+"/thirdpay-deposit-form1.html"}).state("edu_log",{url:"/user/edu/log",controller:"UserEduLogController",templateUrl:templateBaseURI+"/edu_log.html"}).state("messages",{url:"/messages",controller:"MessagesController",templateUrl:templateBaseURI+"/messages.html"}).state("deposit_account",{url:"/deposit/account",controller:"DepositAccountController",templateUrl:templateBaseURI+"/deposit-account.html"}).state("deposit",{url:"/deposit",controller:"DepositController",templateUrl:templateBaseURI+"/deposit.html"}).state("withdrawal_account",{url:"/withdrawal/account",controller:"WithDrawalAccountController",templateUrl:templateBaseURI+"/withdrawal-account.html"}).state("withdrawal",{url:"/withdrawal",controller:"WithdrawalController",templateUrl:templateBaseURI+"/withdrawal.html"}).state("conversion",{url:"/conversion",controller:"ConversionController",templateUrl:templateBaseURI+"/conversion.html"}).state("userinfo",{url:"/user/info",controller:"UserInfoController",templateUrl:templateBaseURI+"/userinfo.html"}).state("updatepwd",{url:"/user/update/password",controller:"UserPwdController",templateUrl:templateBaseURI+"/user-password.html"}).state("updatedeposit",{url:"/user/update/deposit",controller:"UpdateDepositController",templateUrl:templateBaseURI+"/update-deposit.html"}).state("dispensinglog",{url:"/user/dispensing/log/:type/:hkType/:beginTime/:endTime",controller:"DispensingLogController",templateUrl:templateBaseURI+"/dispensing-log.html"}).state("order",{url:"/user/order",controller:"OrderController",templateUrl:templateBaseURI+"/order.html"}).state("order_log",{url:"/user/order/log",controller:"OrderLogController",templateUrl:templateBaseURI+"/order_log.html"}).state("bankcard",{url:"/user/bind-bank",controller:"UserBindBankController",templateUrl:templateBaseURI+"/bink-card.html"}).state("404",{url:"/404",templateUrl:templateBaseURI+"/404.html"}).state("goPayCenter",{url:"/goPayCenter",params:{pay_url:"",sendParams:"",payValue:""},controller:"goPayCenterCtrl",templateUrl:templateBaseURI+"/goPayCenter.html"})}).run(["$rootScope","$window","$interval","$cacheFactory","$sce","$http","$state","ngDialog",function($rootScope,$window,$interval,$cacheFactory,$sce,$http,$state,ngDialog){function loadAccountInfo(){var injector=angular.injector(["ng"]),$http=injector.get("$http");$http.get(baseURI+"/memberinterface/getUserInfo").then(function(res){try{var data=angular.fromJson(res.data.datas);if("undefined"==typeof data)return;$rootScope.account=data,$rootScope.account.user_name=data.userRealName,$rootScope.account.balance=data.userMoney,$rootScope.account.level=data.userTypeLevel,$rootScope.account.has_bank_card=!(data.userBankCard.trim().length<=0)}catch(e){}})}FastClick.attach(document.body);var pager=function(){var pageSize=10,pageNo=1,totalPages=1,haveNext=!1,havePre=!1;return{init:function(result){haveNext=result.hasNext,havePre=result.hasPre,totalPages=result.totalPages,totalPages>1&&($rootScope.pager.havePager=!0),$rootScope.$emit("pagerLoad")},reset:function(){pageSize=10,pageNo=1,totalPages=1,haveNext=!1,havePre=!1},haveNext:function(){return haveNext},havePre:function(){return havePre},pageSize:function(size){return size&&(pageSize=size),pageSize},pageNo:function(setNo){return setNo&&(pageNo=setNo),pageNo},totalPages:function(){return totalPages},nextPageNo:function(){return haveNext?pageNo+1:pageNo},prePageNo:function(){return havePre?pageNo-1:pageNo}}}();pager.havePager=!1,$interval(function(){$cacheFactory.get("$http").removeAll()},1e3),$interval(function(){loadAccountInfo()},3e4);var date=new Date;if(angular.extend($rootScope,{pager:pager,onlineServiceLink:onlineServiceLink,templateURL:function(templateURI){return templateBaseURI+templateURI},staticURL:function(uri){return staticURI+uri},staticURI:staticURI,title:"会员中心",xzje:10,setTitle:function(title){$rootScope.title=title,window.document.title=title},today:date.toDateInputValue(),back:function(){return void 0!=$rootScope.backState&&""!=$rootScope.backState?$rootScope.go($rootScope.backState):$window.history.back()},currentNav:"home",scrollToTop:function(){$window.scrollTo(0,0)},isLogged:function(){return""!=$rootScope.account.user_name},redirectToLogin:function(){$window.location.href=baseURI+$rootScope.loginURL},redirectToRegister:function(){$window.location.href=baseURI+"/m/register/goRegister"},range:function(start,end,step){step=step||1;for(var ids=[],i=start;i<end;i+=step)ids.push(i);return ids},renderHTML:function(htmlCode){return"undefined"!=typeof htmlCode?(console.log(htmlCode),$sce.trustAsHtml(htmlCode.toString())):""},goMainHome:function(){window.location.href=baseURI+"/m/main"},logout:function(){$http({url:baseURI+"/loginOut",type:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded; charset=UTF-8"}}).then(function(res){var obj=res.data;obj.rs?window.location.href=baseURI+"/m/main":alert("用户退出系统出错~")},function(){alert("用户退出系统出错~")})},loadAccountInfo:loadAccountInfo,baseURI:baseURI,go:function(url){"/"==url[0]?window.location.href=url:$state.go.apply(this,arguments)},alert:function(msg,title){return title=title||"提示",ngDialog.open({plain:!1,template:templateBaseURI+"/dialog/alert.html",data:{title:title,message:msg},appendClassName:"ngdialog-alert"})},registerURL:"/m/register/goRegister",loginURL:"/m/main/index"}),$rootScope.loadAccountInfo(),$rootScope.account={user_name:"未登陆",balance:"0",level:"未知会员",has_bank_card:!1},$rootScope.$watch("title",function(newval,oldval){$rootScope.setTitle(newval)}),$rootScope.$on("$stateChangeStart",function(){$rootScope.scrollToTop(),ngDialog.closeAll(),$rootScope.pager.reset(),$rootScope.backState=""}),$rootScope.$on("$stateChangeSuccess",function(){}),!isLogged)return void $rootScope.goMainHome()}]);