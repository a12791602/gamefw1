package com.mh.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * @description 解析xml字符串
 */
public class ParseXmlUtil {
	/**
	 * 字符串转换为XML文档
	 * @author Andy
	 * @date 2015-12-1
	 */
	public static Document parseXMLStr(String xmlStr){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	/**
	 * 获取根节点
	 * @author Andy
	 * @date 2015-12-1
	 */
	public static Element getRootElement(Document doc){
		Element rootElt = doc.getRootElement();
		Element elt=rootElt.element("elementName");
		
		return rootElt;
	}
	/**
	 * 转换节点为MAP
	 * @return Map<String,Object>
	 * @author Andy
	 * @date 2015-12-1
	 */
	public static Map<String,String> parseEltToMap(Element elt){
		 Map<String,String> map=new HashMap<String, String>();
		 Iterator iter = elt.elementIterator();; // 获取根节点下的子节点head
         // 遍历节点
         while (iter.hasNext()) {
             Element recordEle = (Element) iter.next();
             String name=recordEle.getName();
             String value=recordEle.getText();
             map.put(name, value==null?"":value);
         }
         return map;
	}
   
   
    public static void main(String[] args) {

        // 下面是需要解析的xml字符串例子

        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Ips><GateWayReq>" +
        		"<head><Version>v0.3.4</Version><MerCode>170183</MerCode><MerName></MerName><Account>admin123</Account><MsgId>151201071523490</MsgId><ReqDate>20151201071528</ReqDate><Signature>06B42446F3A7B55EEF7FE8047ECCFF16</Signature></head><body><MerBillNo>151201071523490</MerBillNo><Amount>1.00</Amount><Date>20151201</Date><CurrencyType>156</CurrencyType ><GatewayType>01</GatewayType><Lang>GB</Lang><Merchanturl><![CDATA[http://localhost:9090/gamefw/payReturn/payReturnHuanxunPage]]></Merchanturl><FailUrl><![CDATA[http://103.230.218.241/]]></FailUrl><Attach><![CDATA[151201071523490_testou_14]]></Attach><OrderEncodeType>5</OrderEncodeType><RetEncodeType>17</RetEncodeType><RetType>1</RetType><ServerUrl><![CDATA[http://103.230.218.241:8882/payhc/payMsg_huanxunReturnMessage.action]]></ServerUrl><BillEXP>null</BillEXP><GoodsName>computer</GoodsName><IsCredit></IsCredit><BankCode></BankCode><ProductType></ProductType></body></GateWayReq></Ips>";
        Document doc=parseXMLStr(xmlString);
        Element root=getRootElement(doc);
        
        Element reqElt=root.element("GateWayReq");
        Element headElt=reqElt.element("head");
        System.out.println(headElt);
        
        Map<String,String> map=parseEltToMap(headElt);
        System.out.println(map);
        
    }

}