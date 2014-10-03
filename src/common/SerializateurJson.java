package common;

import com.google.gson.*;

public class SerializateurJson {
	
	public static String objectToJson(Object voObj) {
		Gson gson = new Gson();
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String s = gson.toJson(voObj);
		
		return s;	
		
	}
	
	
	private void Tojunk()
	{
		/*
		 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
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
		*/
	}
	
	private void Fromjunk()
	{
		/*
		XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlObjStr.getBytes()));
		   Object o = decoder.readObject();
		   decoder.close();
		   return o;
		   */
	}
	
	
	public static Object jsonToObject(String jsonObjStr, Class type){
		   Gson gson = new Gson();
		   gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		   Object o = gson.fromJson(jsonObjStr, type);
			return o;
		}

}
