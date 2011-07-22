package br.com.jro.developer.tools.shapefile;

public class Field {
	private String name;
	private Object value;
	private Class javaClass;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setJavaClass(Class javaClass) {
		this.javaClass = javaClass;
	}

	public Class getJavaClass() {
		return javaClass;
	}
}