package br.com.core.services.translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.XppDomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public abstract class XMLTranslatable{
	
	public static final int TYPE_SYNCRONIZE_SERVER = 1;
	public static final int TYPE_SYNCRONIZE_DEVICE = 2;
	public static final int TYPE_XPP_DRIVER = 3;
	public static final int TYPE_XPP_DOM_DRIVER = 4;
	public static final int TYPE_XPP_DRIVER_WITH_CDATA = 5;
	public static final int TYPE_STAX_DRIVER = 6;
	public static final int TYPE_DOM_DRIVE = 7;
	
	//public abstract void parseCollectionToJSON(Collection list, StringBuilder json);
	//public abstract void parseJSONToCollection(InputStream xml,Collection list);
	//public abstract void parseJSONToCollection(String xml, Collection list);
	/*
	public abstract boolean canConvert(Class clazz);
	
	public abstract boolean canConvert(String xml);

	public abstract void parseBeanToXML(Object value, String xmlBean);
	
	public abstract void parseXMLToBean(String xml, Serializable bean);
	
	public abstract void parseXMLToCollection(String xml, Collection list);
	
	public abstract void parseXMLToCollection(InputStream xml,Collection list);
	
	public abstract void parseCollectionToXML(Collection list, String xml);
	parseJSONToCollection
	*/
	
	private Class clazz;
	/**
	 * metodo instancia um novo objeto XStream para as classes de converter
	 * metodo preparado para instanciar a classe do framework xstream de acordo o parametro syncronizeType
	 * usando as constantes 
	 * 	public static final int TYPE_SYNCRONIZE_SERVER = 1;
	 *  public static final int TYPE_SYNCRONIZE_DEVICE = 2;
	 * @param syncronizeType - Typo de translator que ser� interpretado
	 * @return Objeto XStream instanciado
	 */
	public XStream getInstanceXStream(int syncronizeType){
		XStream stream = null;
		switch (syncronizeType) {
			case TYPE_SYNCRONIZE_DEVICE:
			 return newSyncronizeDeviceXtream();
			case TYPE_SYNCRONIZE_SERVER:
			 return newSyncronizeServerXtream();
			case TYPE_XPP_DRIVER:
				return newXppDriverInstance();
			case TYPE_XPP_DOM_DRIVER:
				return newXppDomDriverInstance();
			case TYPE_XPP_DRIVER_WITH_CDATA:
				return newXppDriverWithCDataInstance();
			case TYPE_STAX_DRIVER:
				return newStaxDriverInstance();
			case TYPE_DOM_DRIVE:
				return newDomDriveInstance();
		default:
			break;
		}
        return null;
	}
	private XStream newSyncronizeServerXtream(){
		return newXppDriverWithCDataInstance();
	}
	
	@Deprecated //Usar o metodo public XStream getInstanceXStream(int syncronizeType){ 
	public XStream getInstanceXStream(){
		return newXppDriverWithCDataInstance();
	}
	
	protected XStream newSyncronizeDeviceXtream(){
		return newXppDriverInstance();
	}
	protected XStream newSyncronizeDeviceXtream(Class clazz){
		this.clazz = clazz;
		return newXppDriverInstance();
	}
	private XStream newXppDriverInstance(){
		XStream stream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new CompactWriter(out);
            }
            
        }){
		  @Override
		  protected MapperWrapper wrapMapper(MapperWrapper next) {
		      return new MapperWrapper(next) {
		 
		      @Override
		      public boolean shouldSerializeMember(Class definedIn, String fieldName) {
		    	  	try {
		    	  		return definedIn != Object.class || realClass(fieldName) != null;
					} catch (Exception e) {
					  return false;
					}
		          }
		       };
		    }
		};
		return stream;
	}

	private XStream newXppDomDriverInstance(){
		XStream stream = new XStream(new XppDomDriver(){
			  public HierarchicalStreamWriter createWriter(Writer out) {
	                return new CompactWriter(out);
	          }
		}){
			  @Override
			  protected MapperWrapper wrapMapper(MapperWrapper next) {
			      return new MapperWrapper(next) {
			 
			      @Override
			      public boolean shouldSerializeMember(Class definedIn, String fieldName) {
			    	  	try {
			    	  		return definedIn != Object.class || realClass(fieldName) != null;
						} catch (Exception e) {
						  return false;
						}
			          }
			       };
			    }
		};
		return stream;
	}
	
	private XStream newXppDriverWithCDataInstance(){
		XStream stream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new CompactWriter(out) {
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                };
            }
        });
		return stream;
	}
	
	private XStream newStaxDriverInstance(){
		XStream stream = new XStream(new StaxDriver(){
			 public HierarchicalStreamWriter createWriter(Writer out) {
	                return new CompactWriter(out);
	          }
		}){
			  @Override
			  protected MapperWrapper wrapMapper(MapperWrapper next) {
			      return new MapperWrapper(next) {
			 
			      @Override
			      public boolean shouldSerializeMember(Class definedIn, String fieldName) {
			    	  	try {
			    	  		return definedIn != Object.class || realClass(fieldName) != null;
						} catch (Exception e) {
						  return false;
						}
			          }
			       };
			    }
		};
		return stream;
	}
	
	private XStream newDomDriveInstance(){
		XStream stream = new XStream(new DomDriver(){
			 public HierarchicalStreamWriter createWriter(Writer out) {
	                 return new CompactWriter(out);
	          }
		}) {
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        try {
                        	return definedIn != Object.class || realClass(fieldName) != null;
                        } catch(CannotResolveClassException cnrce) {
                            return false;
                        }
                    }
                };
            }
        };			
		return stream;
	}
		public static String getStringFromInputStream(InputStream is) {
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
	 
}
