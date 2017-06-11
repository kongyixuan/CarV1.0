<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>支付页面</title>
<link href="${ctx}/resource/common/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${ctx}/resource/common/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="${ctx}/resource/common/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href="${ctx}/resource/common/css/cart.css"rel="stylesheet" type="text/css" media="all"/>	
<link rel="stylesheet" href="${ctx}/resource/common/css/tips.css" type="text/css">

<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--fonts-->
<!--//fonts-->
<script type="text/javascript" src="${ctx}/resource/common/js/move-top.js"></script>
<script type="text/javascript" src="${ctx}/resource/common/js/easing.js"></script>
<!--slider-script-->
		<script src="${ctx}/resource/common/js/responsiveslides.min.js"></script>
			<script>
				$(function () {
				  $("#slider1").responsiveSlides({
					auto: true,
					speed: 500,
					namespace: "callbacks",
					pager: true,
				  });
				});
			</script>
<!--//slider-script-->
<script>$(document).ready(function(c) {
	$('.alert-close').on('click', function(c){
		$('.message').fadeOut('slow', function(c){
	  		$('.message').remove();
		});
	});	  
});
</script>
<script>
/* $(document).ready(function(c) {
	$('.alert-close1').on('click', function(c){
		$('.message1').fadeOut('slow', function(c){
	  		$('.message1').remove();
		});
	});	  
}); */

function FindOrder(){
//var payTrade=$.(".payTrade");
 var payTrade=document.getElementById("payTrade");
 if(confirm("确定扫过?")){
	      payTrade.action="/car/order_payOrder.do";
	      payTrade.submit();  
	      } 
}
</script>




</head>
<body>
<%@ include file="/common/menu.jsp" %>
	<div class="container cart">
		<div class="span24">
			<div class="step step1">
				<ul>
					<li class="current"></li>
					 <li style="margin-left: 200px;color: red;font-size: 20px; list-style-type:none">二维码支付</li>
				</ul>
			</div>
			<table>
				<tbody>
                             <tr>
                                <th>订单号</th>
                                <td>${outTradeNo}</td>
                              </tr> 
                            
                                <tr>
                                <th>汽车牌号</th>
                                <td>${payBean.carNumber}</td>
                              </tr> 
                              <tr>
                                <th>汽车品牌</th>
                                <td>${payBean.cname}</td>
                              </tr>                          
                              <tr>
                                <th>租车金额</th>
                                <td>￥${payBean.dailyPrice}</td>
                              </tr>
                        
                             <tr rowspan="4">
                               <!--  <th>支付二维码</th> -->
                               <p align="center"><img align="center" height="300px" width="300px" src="upload/${fileName}"/></p>
                              </tr> 
                               <tr>
                                
                               <td><p align="center">请用支付宝app扫上述二维码进行支付</p>
                               <p align="center"><button type="submit" onclick="FindOrder();">扫完请点击确 认</button></p>
                               </td>
                              </tr> 
                          
                               
				</tbody>
		     	</table>
		     	<form id="payTrade" action="" method="post">
		     	<input type="hidden" name="outTradeNo" value="${outTradeNo}" />
		     	</form>
		     	
		</div>
	</div>
	
	
	
<%@ include file="/common/footer.jsp" %>	
</body>
</html>