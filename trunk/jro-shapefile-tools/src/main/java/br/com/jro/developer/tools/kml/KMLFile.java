package br.com.jro.developer.tools.kml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.opengis.feature.Feature;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.vividsolutions.jts.geom.CoordinateSequence;
import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
import com.vividsolutions.jts.geom.CoordinateSequences;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.io.gml2.GMLConstants;
import com.vividsolutions.jts.io.gml2.GMLHandler;

public class KMLFile {
	
	private KMLReader reader;
	
	public KMLFile(URL url) throws IOException, SAXException, URISyntaxException{
		if(url!=null){
			reader = new KMLReader(url);
			reader.read();
		}else{
			throw new NullPointerException("Null URL for KMLFile");
		}
	}
	public KMLFile(Iterator<Feature> features, URL url) {
		if(url!=null){
			reader = new KMLReader(url);
		}else{
			throw new NullPointerException("Null URL for KMLFile");
		}
	}
	
	class KMLReader{
		private URL filename;
		
		public KMLReader(URL url){
			this.filename = url;
		}
		public void write(Iterator<Feature> features){
			//Encoder encoder = new Encoder(new KMLConfiguration());
			//encoder.setIndenting(true);

			//encoder.encode(featureCollection, KML.kml, outputstream );
		}
		public void read()
		throws IOException, SAXException, URISyntaxException{
		    XMLReader xr; 
		    xr = new org.apache.xerces.parsers.SAXParser();
		    KMLHandler kmlHandler = new KMLHandler();
		    xr.setContentHandler(kmlHandler);
		    xr.setErrorHandler(kmlHandler);
		    File file = new File(filename.toURI());
		    Reader r = new BufferedReader(new FileReader(file));
		    LineNumberReader myReader = new LineNumberReader(r);
		    xr.parse(new InputSource(myReader));
		    
		    List geoms = kmlHandler.getGeometries();
		    printWKT(geoms);
		}
		public void printWKT(List geoms){
			for (int i = 0; i < geoms.size(); i++) {
				Geometry g = (Geometry)geoms.get(i);
				System.out.println(g);
			}
		}
	}

	class KMLHandler extends DefaultHandler{
		private List geoms = new ArrayList();
		
		private GMLHandler currGeomHandler;
		private String lastEltName = null;
		private GeometryFactory fact = new FixingGeometryFactory();
		
		public KMLHandler(){
			super();
		}
		
		public List getGeometries(){
			return geoms;
		}
		
	  /**
	   *  SAX handler. Handle state and state transitions based on an element
	   *  starting.
	   *
	   *@param  uri               Description of the Parameter
	   *@param  name              Description of the Parameter
	   *@param  qName             Description of the Parameter
	   *@param  atts              Description of the Parameter
	   *@exception  SAXException  Description of the Exception
	   */
	  public void startElement(String uri, String name, String qName,
				Attributes atts) throws SAXException {
			if (name.equalsIgnoreCase(GMLConstants.GML_POLYGON)) {
				currGeomHandler = new GMLHandler(fact, null);
			}
			if (currGeomHandler != null)
				currGeomHandler.startElement(uri, name, qName, atts);
			if (currGeomHandler == null) {
				lastEltName = name;
				//System.out.println(name);
			}
		}
	  
		public void characters(char[] ch, int start, int length) 
		throws SAXException	{
		    if (currGeomHandler != null) {
		    	currGeomHandler.characters(ch, start, length);
		    }
		    else {
		    	String content = new String(ch, start, length).trim();
		    	if (content.length() > 0) {
		    		//System.out.println(lastEltName + "= " + content);
		    	}
		    }
		}
		
		public void ignorableWhitespace(char[] ch, int start, int length)
		throws SAXException {
		    if (currGeomHandler != null)
		    	currGeomHandler.ignorableWhitespace(ch, start, length);
		}
		
	  /**
	   *  SAX handler - handle state information and transitions based on ending
	   *  elements.
	   *
	   *@param  uri               Description of the Parameter
	   *@param  name              Description of the Parameter
	   *@param  qName             Description of the Parameter
	   *@exception  SAXException  Description of the Exception
	   */
	  public void endElement(String uri, String name, String qName)
				throws SAXException {
			// System.out.println("/" + name);
			if (currGeomHandler != null) {
				currGeomHandler.endElement(uri, name, qName);

				if (currGeomHandler.isGeometryComplete()) {
					Geometry g = currGeomHandler.getGeometry();
					//System.out.println(g);
					geoms.add(g);

					// reset to indicate no longer parsing geometry
					currGeomHandler = null;
				}
			}
		}
	}

	/**
	 * A GeometryFactory extension which fixes structurally bad coordinate sequences
	 * used to create LinearRings.
	 * 
	 * @author mbdavis
	 * 
	 */
	class FixingGeometryFactory extends GeometryFactory{
		public LinearRing createLinearRing(CoordinateSequence cs){
			if (cs.getCoordinate(0).equals(cs.getCoordinate(cs.size() - 1))) 
				return super.createLinearRing(cs);
				
			// add a new coordinate to close the ring
			CoordinateSequenceFactory csFact = getCoordinateSequenceFactory();
			CoordinateSequence csNew = csFact.create(cs.size() + 1, cs.getDimension());
			CoordinateSequences.copy(cs, 0, csNew, 0, cs.size());
			CoordinateSequences.copyCoord(csNew, 0, csNew, csNew.size() - 1);
			return super.createLinearRing(csNew);
		}
	}
}
