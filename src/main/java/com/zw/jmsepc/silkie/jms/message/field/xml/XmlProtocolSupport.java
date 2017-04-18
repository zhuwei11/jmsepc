/**
 * 
 */
package com.zw.jmsepc.silkie.jms.message.field.xml;

import java.text.DecimalFormat;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * XML协议实现
 * @author zw
 * 2011-4-14 下午05:56:50
 * @since 1.0
 */
public abstract class XmlProtocolSupport {
	
	protected String encodeing = "UTF-8";
	
	protected Document document;
	
	protected boolean format;
	
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getEncodeing() {
		return encodeing;
	}
	public void setEncodeing(String encodeing) {
		this.encodeing = encodeing;
	}
	public boolean isFormat() {
		return format;
	}
	public void setFormat(boolean format) {
		this.format = format;
	}
	
	protected void setElementText(String xpath, String text) {
		Element e = getElement(xpath, true);
		e.setText(text==null? "" : text);
	}
	
	protected void addElementText(Node node, String name, String text) {
		Element e = addElement(node, name);
		e.setText(text==null? "" : text);
	}	
	
	protected void addElementText(Node node, String name, int text) {
		Element e = addElement(node, name);
		e.setText(String.valueOf(text));
	}
	
	protected void addElementText(Node node, String name, long text) {
		Element e = addElement(node, name);
		e.setText(String.valueOf(text));
	}
	
	protected void addElementText(Node node, String name, float text) {
		Element e = addElement(node, name);
		e.setText(String.valueOf(text));
	}
	
	protected void addElementText(Node node, String name, double text) {
		Element e = addElement(node, name);
		e.setText(String.valueOf(text));
	}
	
	protected void addElementText(Node node, String name, boolean b) {
		Element e = addElement(node, name);
		e.setText(String.valueOf(b));
	}		
	
	protected void setElementText(Node node, String xpath, String text) {
		Element e = getElement(node, xpath, true);
		e.setText(text==null? "" : text);
	}
	
	protected void setElementText(Node node, String xpath, int text) {
		Element e = getElement(node, xpath, true);
		e.setText(String.valueOf(text));
	}
	
	protected void setElementText(Node node, String xpath, long text) {
		Element e = getElement(node, xpath, true);
		DecimalFormat df = new DecimalFormat("#0");
		e.setText(df.format(text));
	}
	
	protected void setElementText(Node node, String xpath, float text) {
		Element e = getElement(node, xpath, true);
		e.setText(String.valueOf(text));
	}
	
	protected void setElementText(Node node, String xpath, double text) {
		Element e = getElement(node, xpath, true);
		e.setText(String.valueOf(text));
	}
	
	protected void setElementText(Node node, String xpath, boolean b) {
		Element e = getElement(node, xpath, true);
		e.setText(String.valueOf(b));
	}	
	
	protected String getElementText(String xpath){
		return getElementText(xpath,"");
	}
	protected String getElementText(String xpath,String defaultValue){
		Element e = getElement(xpath);
		return e == null? defaultValue: e.getText();
	}
	
	protected String getElementAttribute(String xpath, String attrName) {
		Element e = getElement(xpath);
		return e.attributeValue(attrName);
	}
	
	protected void setElementAttribute(String xpath, String attrName,
			String attrValue) {
		Element e = getElement(xpath, true);
		e.addAttribute(attrName, attrValue);
	}

	protected Element getElement(String xpath) {
		return getElement(xpath, false);
	}
	
	protected Element getElement(Node node, String xpath) {
		return getElement(node, xpath, false);
	}	
	
	protected Element getElement(String xpath, boolean autoCreate) {
		Element p = (Element) getDocument().selectSingleNode(xpath);
		if (p != null)
			return p;
		// 如果不存在，并要求自动创建这个Node
		if (autoCreate) {
			return DocumentHelper.makeElement(getDocument(),xpath);
		}
		return null;
	}
	
	protected Element getElement(Node node, String xpath, boolean autoCreate) {
		Element p = (Element)node.selectSingleNode(xpath);
		if (p != null)
			return p;
		// 如果不存在，并要求自动创建这个Node
		if (autoCreate) {
			return addElement(node,xpath);
		}
		return null;
	}
	
	protected Element addElement(Node node, String name) {
		return ((Element)node).addElement(name);
	}
	
	@SuppressWarnings("unchecked")
	protected List<Element> getElements(String xpath) {
		return getDocument().selectNodes(xpath);
	}
	
	@SuppressWarnings("unchecked")
	protected List<Element> getElements(Node node, String xpath) {
		return node.selectNodes(xpath);
	}
	
	protected String getValue(Node node, String xpath) {
		Node p = node.selectSingleNode(xpath);
		return p == null ? "" : p.getText();
	}
	
	protected String getValue(Node node) {
		return node == null ? "" : node.getText();
	}
	
	protected short getShortValue(Node node, String xpath) {
		return (short)getIntValue(node, xpath);
	}
	
	protected short getShortValue(Node node) {
		return (short)getIntValue(node);
	}
	
	protected int getIntValue(Node node, String xpath) {
		int iv = 0;
		try {
			String str = getValue(node, xpath);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Integer.parseInt(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}
	
	protected int getIntValue(Node node) {
		int iv = 0;
		try {
			String str = getValue(node);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Integer.parseInt(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}
	
	
	protected long getLongValue(Node node, String xpath) {
		long iv = 0L;
		try {
			String str = getValue(node, xpath);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Long.parseLong(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}
	
	protected long getLongValue(Node node) {
		long iv = 0L;
		try {
			String str = getValue(node);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Long.parseLong(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}

	protected float getFloatValue(Node node, String xpath) {
		float iv = 0.0f;
		try {
			String str = getValue(node, xpath);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Float.parseFloat(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}
	
	protected float getFloatValue(Node node) {
		float iv = 0.0f;
		try {
			String str = getValue(node);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Float.parseFloat(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}
	
	protected double getDoubleValue(Node node, String xpath) {
		double iv = 0.0d;
		try {
			String str = getValue(node, xpath);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Double.parseDouble(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}	

	protected double getDoubleValue(Node node) {
		double iv = 0.0d;
		try {
			String str = getValue(node);
			if (str == null || str.trim().length() == 0)
				return iv;
			else 
				iv = Double.parseDouble(str);
		} catch(Exception ex) {
			iv = 0;
		}
		return iv;
	}	
	
	protected boolean getBooleanValue(Node node, String xpath) {
		boolean b = false;
		try {
			String str = getValue(node, xpath);
			if (str == null || str.trim().length() == 0)
				return false;
			else if (str.equals("1") || str.equalsIgnoreCase("true"))
				return true;
			else 
				b = Boolean.parseBoolean(str);
		} catch(Exception ex) {
			b = false;
		}
		return b;
	}
	
	protected boolean getBooleanValue(Node node) {
		boolean b = false;
		try {
			String str = getValue(node);
			if (str == null || str.trim().length() == 0)
				return false;
			else if (str.equals("1") || str.equalsIgnoreCase("true"))
				return true;
			else 
				b = Boolean.parseBoolean(str);
		} catch(Exception ex) {
			b = false;
		}
		return b;
	}

}
