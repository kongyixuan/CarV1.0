package com.neusoft.action;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.neusoft.utils.Pager;
import com.opensymphony.xwork2.ModelDriven;
import com.neusoft.dto.RateDto;
import com.neusoft.model.*;
import com.neusoft.service.OrderService;
import com.neusoft.service.RentService;

/**
 * @ClassName:  
 * @Description: 
 * @author administrator
 * @date 2015年12月24日 下午1:46:33 - 2016年12月15日 21时47分54秒
 */

@Controller("orderAction")
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
	
	private static final long serialVersionUID = 1L;
private HttpServletRequest request=ServletActionContext.getRequest();
	//==========model==============
	  private Order order;
		@Override
		public Order getModel() {
			if(order==null) order = new Order();
			return order;	
		}
		//==========model==============
	/**
	 * 依赖注入 start dao/service/===
	 */
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RentService rentService;
	
	//依赖注入 end  dao/service/===
	
	//-------------------------华丽分割线---------------------------------------------
	
	//============自定义参数start=============
	private String startTime; //页面传入的开始时间
	private String endTime; //页面传入的结束时间
	private Integer flag;//标识位
	private Date rentTime;
	private Date returnTime;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	//============自定义参数end=============
	
	//-------------------------华丽分割线---------------------------------------------

	public Date getRentTime() {
		return rentTime;
	}
	public void setRentTime(Date rentTime) {
		this.rentTime = rentTime;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	//============文件上传start=======================================================
	private File file;
	//提交过来的file的名字
    private String fileFileName;
    //提交过来的file的MIME类型
    private String fileContentType;
    public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	 //============文件上传end=========================================================
			
	 //-------------------------华丽分割线---------------------------------------------//
	
	 //=============公=======共=======方=======法==========区=========start============//
	
	/**
	 * 前台用户查看我的订单（我的订单）
	 * @return
	 */
	public String findOrderByUserId() {
		// 获得用户的id.
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if(existUser==null){
			return "userLogin";
		}else{
			Map<String,Object> alias = new HashMap<String,Object>();
			StringBuffer sb = new StringBuffer();
			sb = sb.append("from Order where 1=1 ");
		
			sb = sb.append(" and user.id = :userId ");
			// 获得用户的id
			Integer userId = existUser.getId();
			alias.put("userId",userId);
			//根据编号模糊查询
			if(order.getCode()!=null){
				sb = sb.append(" and code like :code ");
				alias.put("code", "%"+order.getCode()+"%");
			}
			sb = sb.append("order by id desc");
			// 根据用户的id查询订单:
			Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
			// 将PageBean数据带到页面上.
			ActionContext.getContext().put("pagers", pagers);
				return "findOrderByUid";
		}
	}
	
	/**
	 * 前台订单详情
	 * @return
	 */
	public String getByOrderId(){
		Order currOrder = orderService.getById(order.getId());
		ActionContext.getContext().put("order", currOrder);
		if(flag==1){//当前台传入为1时表示查看详情 当为2的时就为跳入到订单修改页面
			return SUCCESS;
		}else{
			return "updateOrderByOrderId";
		}
	}
	public String payTrade(){	
    long day=1;
	String id=request.getParameter("id");
    String dailyPrice=request.getParameter("dailyPrice");
	String carNumber=request.getParameter("carNumber");
	String cname=request.getParameter("cname");
	String rentTime =request.getParameter("rentTime");
	String returnTime =request.getParameter("returnTime");
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	 Date beginDate;
     Date endDate;   
  if(rentTimeOrReturnTime(rentTime,returnTime)){
     try {
	   beginDate = format.parse(rentTime);
	   endDate= format.parse(returnTime);    
	day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
	if(day<=0){
		day=1;
	}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return ERROR;
	}
     }
		PayBean payBean=new PayBean();
		payBean.setId(id);
		payBean.setCarNumber(carNumber);
		payBean.setCname(cname);
		payBean.setRentTime(rentTime);
		payBean.setReturnTime(returnTime);
		if(dailyPrice.length()>0&&dailyPrice!=null){
		double totalPrice= Double.parseDouble(dailyPrice);
		totalPrice=totalPrice*day;
		payBean.setDailyPrice(String.valueOf(totalPrice));
		payTrade(payBean);
		return "payTrade";
		}
	    return ERROR;
	}
	public boolean rentTimeOrReturnTime(String rentTime,String returnTime ){
		 if(rentTime.length()>0&&rentTime!=null&&returnTime.length()>0&&returnTime!=null){	 
			 return true;
		 }
		return false;
	}
	
	
	/**×*****************支付宝*×××××××××××××××××××××××××××××××××××××××/
	 * 
	 * 
	 * 
	 * @param payBean
	 */
	 private static Log  log = LogFactory.getLog(OrderAction.class);

	    // 支付宝当面付2.0服务
	    private static AlipayTradeService   tradeService;

	    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
	    private static AlipayTradeService   tradeWithHBService;

	    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
	    private static AlipayMonitorService monitorService;

	    static {
	        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
	         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
	         */
	        Configs.init("zfbinfo.properties");

	        /** 使用Configs提供的默认参数
	         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
	         */
	        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

	        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
	        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

	        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
	        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
	            .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
	            .setFormat("json").build();
	    }
	  /********************************** end*************************************** */
	
	/*进行业务处理*/
	public void  payTrade(PayBean payBean){
		// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo =payBean.getId() + System.currentTimeMillis()
                            + (long) (Math.random() * 1000000000L);
        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "租车订单"+payBean.getCarNumber();
        payBean.setCarNumber(subject);
        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = payBean.getDailyPrice();
        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";
        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "2088102169136450";
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = payBean.getCname()+payBean.getCarNumber();
        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";
        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";
       // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088102169136450");
        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";
        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);
        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        goodsDetailList.add(goods2);
        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
            .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
        .setNotifyUrl("http://localhost:8080/car/order_notifyResult.do")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
            .setGoodsDetailList(goodsDetailList);
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");
          AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                // 需要修改为运行机器上的路径
        String fileName = String.format("qr-%s.png",payBean.getId()+payBean.getCarNumber());
       String basePath= request.getSession().getServletContext().getRealPath("/");        
        String filePath = new StringBuilder(basePath).append("upload"+File.separator+fileName).toString();
        request.getSession().setAttribute("payBean", payBean);
       request.getSession().setAttribute("fileName", fileName);
       request.getSession().setAttribute("outTradeNo", outTradeNo);
       
        // String filePath = String.format("images%sqr-%s.png",response.getOutTradeNo());
                log.info("fileName:" + fileName);
                log.info("filePath:" + filePath);
             ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;
            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;
            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
	}
	}
	 // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                    response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }
	/**×*****************支付宝*×××××××××××××××××××××××××××××××××××××××/
	/**
	 * 前台修改订单
	 * @return
	 */
	public String exUpdateOrderByOrderId(){
		Order n = orderService.getById(order.getId());
		Rent r=rentService.getById(n.getRent().getId());
		r.setRentTime(rentTime);
		r.setReturnTime(returnTime);
		rentService.update(r);
		ActionContext.getContext().put("url", "/order_findOrderByUserId.do");
		return "redirect";
	}
	
	/**
	 * 前台用户查看租车记录（租车记录）
	 * @return
	 */
	public String rentInfoList() {
		// 获得用户的id.
		User existUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		if(existUser==null){
			return "userLogin";
		}else{
			Map<String,Object> alias = new HashMap<String,Object>();
			StringBuffer sb = new StringBuffer();
			sb = sb.append("from Order where 1=1 ");
		
			sb = sb.append(" and user.id = :userId ");
			// 获得用户的id
			Integer userId = existUser.getId();
			alias.put("userId",userId);
			//根据编号模糊查询
			if(order.getCode()!=null){
				sb = sb.append(" and code like :code ");
				alias.put("code", "%"+order.getCode()+"%");
			}
			sb = sb.append("order by id desc");
			// 根据用户的id查询订单:
			Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
			// 将PageBean数据带到页面上.
			ActionContext.getContext().put("pagers", pagers);
		return SUCCESS;
		}
	}
	
	/**
	 * 前台租车记录中的已经完成租车详情
	 * @return
	 */
	public String getDetailByOrderId(){
		Map<String,Object> alias = new HashMap<String,Object>();
		String hql ="from Order where 1=1  and id = :id  and isDeal=1";
		 alias.put("id", order.getId());
		List<Order> orderList = orderService.getByHQL(hql, alias);
         if(orderList.size()>0){
        	ActionContext.getContext().put("order", orderList.get(0));
		 }
		return SUCCESS;
	}
	
	/**
	 * 列表分页查询
	 * @throws Exception 
	 */
	public String order() throws Exception{
	    Map<String,Object> alias = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Order where 1=1 ");
		if(startTime != null && !"".equals(startTime)){
			sb.append(" and createTime >= :startTime");
		}
		if(endTime != null && !"".equals(endTime)){
			sb.append(" and createTime < :endTime");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime != null && !"".equals(startTime)){
			alias.put("startTime", sdf.parse(startTime+" 00:00:00"));
		}
		if(endTime != null && !"".equals(endTime)){
			alias.put("endTime", sdf.parse(endTime+" 23:59:59"));
		}
		sb = sb.append(" order by id desc");
		Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("order", order);
		return SUCCESS;
    }
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	public String add(){
		return SUCCESS;
	}
	
	/**
	 * 执行添加
	 * @return
	 */
	public String exAdd(){
		orderService.save(order);
		ActionContext.getContext().put("url", "/order_order.do");
		return "redirect";
	}
	
	/**
	 * 查看详情页面
	 * @return
	 */
	public String view(){
		Order n = orderService.getById(order.getId());
		ActionContext.getContext().put("order", n);
		if(flag==1){//当传入1时显示的详情
			return SUCCESS;
		}else if(flag==2){//当传入2时已经审核通过的详情
			return "agreeOrder";
		}else{//当传入2时已经完成交易的的详情
			return "finishOrder";
		}
	}
	
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String update(){
		Order n = orderService.getById(order.getId());
		ActionContext.getContext().put("order", n);
		return SUCCESS;
	}
    
	/**
	 * 执行修改
	 * @return
	 */
	public String exUpdate(){
		Order n = orderService.getById(order.getId());
		orderService.update(n);
		ActionContext.getContext().put("url", "/order_order.do");
		return "redirect";
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		Order n = orderService.getById(order.getId());
		orderService.update(n);
		ActionContext.getContext().put("url", "/order_order.do");
		return "redirect";
	}
	
	/**
	 * 查询未审核订单列表分页查询
	 * @throws Exception 
	 */
	public String unAgreeOrder() throws Exception{
	    Map<String,Object> alias = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Order where 1=1 and state=0 ");
		if(startTime != null && !"".equals(startTime)){
			sb.append(" and createTime >= :startTime");
		}
		if(endTime != null && !"".equals(endTime)){
			sb.append(" and createTime < :endTime");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime != null && !"".equals(startTime)){
			alias.put("startTime", sdf.parse(startTime+" 00:00:00"));
		}
		if(endTime != null && !"".equals(endTime)){
			alias.put("endTime", sdf.parse(endTime+" 23:59:59"));
		}
		sb = sb.append(" order by id desc");
		Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("order", order);
		return SUCCESS;
    }
	
	/**
	 * 查询未完成交易订单列表分页查询
	 * @throws Exception 
	 */
	public String unFinishOrder() throws Exception{
	    Map<String,Object> alias = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Order where 1=1 and isDeal=0 ");
		if(startTime != null && !"".equals(startTime)){
			sb.append(" and createTime >= :startTime");
		}
		if(endTime != null && !"".equals(endTime)){
			sb.append(" and createTime < :endTime");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime != null && !"".equals(startTime)){
			alias.put("startTime", sdf.parse(startTime+" 00:00:00"));
		}
		if(endTime != null && !"".equals(endTime)){
			alias.put("endTime", sdf.parse(endTime+" 23:59:59"));
		}
		sb = sb.append(" order by id desc");
		Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("order", order);
		return SUCCESS;
    }
	
	/**
	 * 完成交易
	 * @return
	 */
	public String finishOrder(){
		Order n = orderService.getById(order.getId());
		n.setIsDeal(1);
		n.setFinishTime(new Date());
		orderService.update(n);
		ActionContext.getContext().put("url", "/order_order.do");
		return "redirect";
	}
	
	/**
	 * 审批通过
	 * @return
	 */
	public String agreeOrder(){
		Order n = orderService.getById(order.getId());
		n.setState(1);
		orderService.update(n);
		ActionContext.getContext().put("url", "/order_order.do");
		return "redirect";
	}
	
	/**
	 * 查询完成交易订单列表分页查询
	 * @throws Exception 
	 */
	public String hasFinishedOrder() throws Exception{
	    Map<String,Object> alias = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Order where 1=1 and isDeal=1 ");
		if(startTime != null && !"".equals(startTime)){
			sb.append(" and finishTime >= :startTime");
		}
		if(endTime != null && !"".equals(endTime)){
			sb.append(" and finishTime < :endTime");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime != null && !"".equals(startTime)){
			alias.put("startTime", sdf.parse(startTime+" 00:00:00"));
		}
		if(endTime != null && !"".equals(endTime)){
			alias.put("endTime", sdf.parse(endTime+" 23:59:59"));
		}
		sb = sb.append(" order by id desc");
		Pager<Order> pagers =orderService.findByAlias(sb.toString(),alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("order", order);
		return SUCCESS;
    }
	
	
	 /**
     * 利润统计
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @return
     */
    public String tongji(){
        List<RateDto> list =orderService.listAll();
        ActionContext.getContext().put("list", list);
        return SUCCESS;
    }

	//=============公=======共=======方=======法==========区=========end============//
	
	 //-------------------------华丽分割线---------------------------------------------//
	
	 //=============自=======定=======义=========方=======法==========区=========start============//
	
	
	
	
	//=============自=======定=======义=========方=======法==========区=========end============//
		
    public String notifyResult() {
        log.info("收到支付宝异步通知！");
        Map<String, String> params = new HashMap<String, String>();

        //取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
          String parameterName = parameterNames.nextElement();
          params.put(parameterName, request.getParameter(parameterName));
        }
        boolean signVerified;
        try {
          signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8");
        } catch (AlipayApiException e) {
          e.printStackTrace();
          return "error";
        }
        if (signVerified) {
          String outtradeno = params.get("out_trade_no");
          log.info(outtradeno + "号订单回调通知。");
//          System.out.println("验证签名成功！");
          log.info("验证签名成功！");

          //若参数中的appid和填入的appid不相同，则为异常通知
          if (!Configs.getAppid().equals(params.get("app_id"))) {
            log.warn("与付款时的appid不同，此为异常通知，应忽略！");
            return "error";
          }

          //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
         /* BaobiaoOrder order = baobiaoOrderService.findOrderByOuttradeno(outtradeno);
          if (order == null) {
            log.warn(outtradeno + "查无此订单！");
            return "failed";
          }*/
         /* if (order.getAmount() != Double.parseDouble(params.get("total_amount"))) {
            log.warn("与付款时的金额不同，此为异常通知，应忽略！");
            return "failed";
          }*/

         // if (order.getStatus() == BaobiaoOrder.TRADE_SUCCESS) return "success"; //如果订单已经支付成功了，就直接忽略这次通知

          String status = params.get("trade_status");
         /* if (status.equals("WAIT_BUYER_PAY")) { //如果状态是正在等待用户付款
            if (order.getStatus() != BaobiaoOrder.WAIT_BUYER_PAY) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.WAIT_BUYER_PAY, outtradeno);
          } else if (status.equals("TRADE_CLOSED")) { //如果状态是未付款交易超时关闭，或支付完成后全额退款
            if (order.getStatus() != BaobiaoOrder.TRADE_CLOSED) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_CLOSED, outtradeno);
          } else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { //如果状态是已经支付成功
            if (order.getStatus() != BaobiaoOrder.TRADE_SUCCESS) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_SUCCESS, outtradeno);
          } else {
            baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.UNKNOWN_STATE, outtradeno);
          }
          log.info(outtradeno + "订单的状态已经修改为" + status);
        } else { //如果验证签名没有通过
          return "error";
        }*/
        return "success";
      }
        return "error";
    }
    
 /*********************** 订单支付查询***************************************&&&&*/
public String payOrder(){
	String outTradNO=request.getParameter("outTradeNo");
	if(outTradNO!=null){
		payFinsh(outTradNO);
		return "orderFinsh";
	}
	return "error";
    }
  public void payFinsh(String outTradeNo) {
	  Log log = LogFactory.getLog("trade_query");

		if(outTradeNo!=null){
			// 一定要在创建AlipayTradeService之前设置参数
			Configs.init("zfbinfo.properties");

			AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

			// (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
	        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
	        		.setOutTradeNo(outTradeNo);
			AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
			switch (result.getTradeStatus()) {
				case SUCCESS:
					log.info("查询返回该订单支付成功: )");

					AlipayTradeQueryResponse resp = result.getResponse();

					log.info(resp.getTradeStatus());
					log.info(resp.getFundBillList());
					 List<TradeFundBill> tradeFundBills=resp.getFundBillList();
					 TradeFundBill tradeFundBill=tradeFundBills.get(0);
					 log.info(tradeFundBill);
					request.getSession().setAttribute("tradeFundBill", tradeFundBills.get(0));
					request.getSession().setAttribute("outTradeNo",outTradeNo);
					request.getSession().setAttribute("success","支付成功");
					break;
				case FAILED:
					log.error("查询返回该订单支付失败!!!");
					break;

				case UNKNOWN:
					log.error("系统异常，订单支付状态未知!!!");
					break;

				default:
					log.error("不支持的交易状态，交易返回异常!!!");
					break;
			}
	       request.getSession().setAttribute("result", result.getResponse().getBody());
	       return;
		}
}  

    }
