
/*
 * The JTS Topology Suite is a collection of Java classes that
 * implement the fundamental operations required to validate a given
 * geo-spatial data set to a known topological specification.
 *
 * Copyright (C) 2001 Vivid Solutions
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, contact:
 *
 *     Vivid Solutions
 *     Suite #1A
 *     2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www.vividsolutions.com
 */

package br.com.jro.gis.wkt;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.gml2.*;
import java.util.*;
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * An example of using the {@link GMLHandler} class
 * to read geometry data out of KML files.
 * 
 * @author mbdavis
 *
 */
public class KMLReaderExample {
  public static void main(String[] args)
  throws Exception {
  	String filename = "//C:\\jairo\\projetos\\geotools-sample\\dados\\CCEAA10004.xml"; //"C:\\proj\\JTS\\KML\\usPop-STUS-p06.kml";
  	KMLReader rdr = new KMLReader(filename);
  	rdr.read();
  }

}

class KMLReader{
	private String filename;
	
	public KMLReader(String filename){
		this.filename = filename;
	}
	
	public void read()
	throws IOException, SAXException{
	    XMLReader xr; 
	    xr = new org.apache.xerces.parsers.SAXParser();
	    KMLHandler kmlHandler = new KMLHandler();
	    xr.setContentHandler(kmlHandler);
	    xr.setErrorHandler(kmlHandler);
	    
	    Reader r = new BufferedReader(new FileReader(filename));
	    LineNumberReader myReader = new LineNumberReader(r);
	    xr.parse(new InputSource(myReader));
	    
	    List geoms = kmlHandler.getGeometries();
	    printWKT(geoms);
	}
	public static void printWKT(List geoms){
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
