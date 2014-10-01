package common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SerializateurXML {
	
	public static String objectToXML(Object voObj) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder(new BufferedOutputStream(stream));
			xmlEncoder.writeObject(voObj);
			xmlEncoder.close();
			return stream.toString("UTF-8");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	
	public static Object xmlToObject(String xmlObjStr){
		   XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlObjStr.getBytes()));
		   Object o = decoder.readObject();
		   decoder.close();
		   return o;
		}

}
