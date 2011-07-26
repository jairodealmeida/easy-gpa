package br.com.jro.developer.tools.shapefile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.opengis.feature.Feature;

import br.com.jro.developer.tools.kml.KMLFile;

public class ShapeFileUtil {
	public static void exportToKML(String shapeFilePath, 
								   String kmlFilePath) throws Exception{
		if(shapeFilePath!=null){
			try {
				//read shape file from existing file path
				ShapeFile shape = new ShapeFile(new URL(shapeFilePath));
				//write kml file from noi existing file path
				//KMLFile kml = new KMLFile(new URL(kmlFilePath), "teste.xml");
				//kml.
				
				Iterator<Feature> iterator = shape.getFeatures();
				while(iterator.hasNext()){
					Feature feature = iterator.next();
					
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} 
		}else{
			throw new NullPointerException("shape file path is null");
		}
	}
}
