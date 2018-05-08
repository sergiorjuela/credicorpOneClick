package com.mega.credicorp.riesgos.reportes;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.*;
import org.json.XML;

public class JsonReporte {

	private String data = null;

	public static String xmlStringToJSONString(String xmlString,
			int indentFactor) throws JSONException {
		JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
		String string = xmlJSONObj.toString(indentFactor);
		return string;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String generarHtml() {

		/*try {
			JSONObject jsonObj = new JSONObject(this.data);
			JSONArray data =  jsonObj.getJSONArray("data");
			JOptionPane.showMessageDialog(null, "color: ");
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "error: " + e);
			e.printStackTrace();
		}
	*/

		return "";
	}

}
