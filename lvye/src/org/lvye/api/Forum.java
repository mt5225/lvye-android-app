package org.lvye.api;

public class Forum {
	private  String id;
	private  String name;
	private  String url; 
	
	public Forum (String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


}
