package com.mh.web.util;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class PayUtil {
	/**
	 * 环讯V0.3.4回调信息
	 */
	public static Map<String,Object> getHxCallMap(String paymentResult){
		Map<String,Object> maps=new HashMap<String, Object>();
		Document doc=ParseXmlUtil.parseXMLStr(paymentResult);
        Element root=ParseXmlUtil.getRootElement(doc);
        
        Element reqElt=root.element("GateWayRsp");
        Element headElt=reqElt.element("head");
        Element bodyElt=reqElt.element("body");
        
        Map<String,String> headMap=ParseXmlUtil.parseEltToMap(headElt);
        Map<String,String> bodyMap=ParseXmlUtil.parseEltToMap(bodyElt);
        maps.put("headMap", headMap);
        maps.put("bodyMap", bodyMap);
        String bodyStr=getBodyStr(paymentResult);
        maps.put("bodyStr", bodyStr);
        System.out.println(maps);
        return maps;
	}
	
	
	public static Map<String,Object> getHxWxCallMap(String paymentResult){
		Map<String,Object> maps=new HashMap<String, Object>();
		Document doc=ParseXmlUtil.parseXMLStr(paymentResult);
        Element root=ParseXmlUtil.getRootElement(doc);
        
        Element reqElt=root.element("WxPayRsp");
        Element headElt=reqElt.element("head");
        Element bodyElt=reqElt.element("body");
        
        Map<String,String> headMap=ParseXmlUtil.parseEltToMap(headElt);
        Map<String,String> bodyMap=ParseXmlUtil.parseEltToMap(bodyElt);
        maps.put("headMap", headMap);
        maps.put("bodyMap", bodyMap);
        String bodyStr=getBodyStr(paymentResult);
        maps.put("bodyStr", bodyStr);
        System.out.println(maps);
        return maps;
	}
	
	/**
	 * body节点信息，签名用
	 * @Description: TODO
	 * @param    
	 * @return String
	 * @author Andy
	 * @date 2015-12-1
	 */
	public static String getBodyStr(String paymentResult){
		int b_point=paymentResult.indexOf("<body>");
		int e_point=paymentResult.indexOf("</body>");
		String bodyStr=paymentResult.substring(b_point, e_point);
		bodyStr+="</body>"; 
        return bodyStr;
	}
	
	
	public static void main(String[] args) {
		//String bodyStr="<body><MerBillNo>151202041149466</MerBillNo><CurrencyType>156</CurrencyType><Amount>1</Amount><Date>20151202</Date><Status>Y</Status><Msg><![CDATA[支付成功！]]></Msg><Attach><![CDATA[151202041149466_testou_14]]></Attach><IpsBillNo>BO20151202161201015590</IpsBillNo><IpsTradeNo>2015120204120231938</IpsTradeNo><RetEncodeType>17</RetEncodeType><BankBillNo>72019082849</BankBillNo><ResultType>0</ResultType><IpsBillTime>20151202161331</IpsBillTime></body>";
//		String bodyStr="<body><MerBillNo>151202041149466</MerBillNo><CurrencyType>156</CurrencyType><Amount>1</Amount><Date>20151202</Date><Status>Y</Status><Msg>支付成功！</Msg><Attach>151202041149466_testou_14</Attach><IpsBillNo>BO20151202161201015590</IpsBillNo><IpsTradeNo>2015120204120231938</IpsTradeNo><RetEncodeType>17</RetEncodeType><BankBillNo>72019082849</BankBillNo><ResultType>0</ResultType><IpsBillTime>20151202161331</IpsBillTime></body>";
//
//		String no="170183";
//		String key="hNdFUA3QEARjPfM68fQaQFHBykOXt4htqmuwjpp8zJhmLV4DRoPpn5svulRaG5MquVJGMZupT4f44eebQmjKPhMm11C0P3Asg84Q947PLawC1AOv1GM1oEBEHXzRksRP";
//		String signMD5 =MD5Encrypt.MD5Encode(bodyStr+no + key).toLowerCase();
//		System.out.println(signMD5);
		
		String s="<Ips><GateWayRsp><head><ReferenceID></ReferenceID><RspCode>000000</RspCode><RspMsg><![CDATA[交易成功！]]></RspMsg><ReqDate>20151202161302</ReqDate><RspDate>20151202161331</RspDate><Signature>8e6f48d3809ca5dc7bd6073ea62d8474</Signature></head><body><MerBillNo>151202041149466</MerBillNo><CurrencyType>156</CurrencyType><Amount>1</Amount><Date>20151202</Date><Status>Y</Status><Msg><![CDATA[支付成功！]]></Msg><Attach><![CDATA[151202041149466_testou_14]]></Attach><IpsBillNo>BO20151202161201015590</IpsBillNo><IpsTradeNo>2015120204120231938</IpsTradeNo><RetEncodeType>17</RetEncodeType><BankBillNo>72019082849</BankBillNo><ResultType>0</ResultType><IpsBillTime>20151202161331</IpsBillTime></body></GateWayRsp></Ips>";
		int b_point=s.indexOf("<body>");
		int e_point=s.indexOf("</body>");
		String ss=s.substring(b_point, e_point);
		System.out.println(ss);
	}
}
