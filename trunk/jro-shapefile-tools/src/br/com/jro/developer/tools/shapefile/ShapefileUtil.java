package br.com.jro.developer.tools.shapefile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.Feature;
import org.geotools.feature.FeatureCollection;

import com.vividsolutions.jts.geom.Geometry;

public class ShapefileUtil {
	

	private ShapefileDataStore util;
	
	public ShapefileUtil(URL url) throws Exception{
		util = new ShapefileDataStore(url);
	}
	public Iterator<Feature> getFeatures() throws Exception{
		List<String> wkts = new ArrayList<String>();
		FeatureSource fs = util.getFeatureSource();
		FeatureCollection fc = fs.getFeatures();
		@SuppressWarnings("unchecked")
		Iterator<Feature> i = (Iterator<Feature>)fc.iterator();
		return i;
	}
	public List<String> getWktList() throws Exception {
		List<String> wkts = new ArrayList<String>();
		FeatureSource fs = util.getFeatureSource();
		FeatureCollection fc = fs.getFeatures();
		@SuppressWarnings("unchecked")
		Iterator<Feature> i = (Iterator<Feature>)fc.iterator();
		while(i.hasNext()){
			Feature f = i.next();
			Geometry g = f.getDefaultGeometry();
			String wkt = this.getWKT(g);
			wkts.add(wkt);
		}
		return wkts;
	}
	private String getWKT(Geometry g){
		if(g!=null){
			return g.toString();
		}else{
			return null;
		}
	}
}
