package br.com.jro.developer.tools.kml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

public class KMLWriter {

	
	public void write(Iterator<Feature> iterator) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter("data/wwf-x-y-coordinates-area.kml", true));
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("\n<kml xmlns=\"<a href=\"http://www.opengis.net/kml/2.2\">http://www.opengis.net/kml/2.2\">");
        out.write("\n\t<Placemark>");
        out.write("\n\t\t<name>WWWF EcoRegions Database</name>");
        
		while(iterator.hasNext()){
			Feature feature = iterator.next();
			Geometry geometry = (Geometry) feature.getDefaultGeometryProperty().getValue(); 
			
		}
        
        out.write("\n\t</Placemark>");
        out.write("\n</kml>");
        out.write("\n");
        out.close();
	}
}
