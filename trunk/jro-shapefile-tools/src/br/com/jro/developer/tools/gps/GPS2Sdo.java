package br.com.jro.developer.tools.gps;

import java.io.File;

import br.com.jro.developer.tools.shapefile.CreateShapefile;

import com.vividsolutions.jts.geom.Coordinate;

public class GPS2Sdo {
	private File gpsfile = null;
	private CreateShapefile gpsgeometry = new CreateShapefile();
	/**
	 * Metodo construtor para setar o objeto Gps2Sdo com o arquivo GPS
	 * @param gpsfile
	 */
	public GPS2Sdo(String gpsfile) {
		setGpsfile(gpsfile);
	}

	/**
	 * Methodo para criar o tema default de Poligono
	 * @return LocalTheme
	 * @since 16/11/2006
	 */
	/*
	 * public LocalTheme createThemePolygon(){ //estilo para o tema LocalTheme
	 * theme = null; AppStyles styles = new AppStyles();
	 * 
	 * try { if(gpsfile!=null) { // create some geometries Vector featureVec =
	 * new Vector(10); StyledFeature sf = new StyledFeature(); // criando um
	 * poligono
	 * 
	 * double[] coords =
	 * captureCoordinates(gpsgeometry.loadGpsImplementedMark(gpsfile
	 * ));//gpsgeometry.loadGpsVirtualMark(gpsfile)
	 * 
	 * if(coords == null) { //log.error("O arquivo " + gpsfile.getName() +
	 * "é inválido");
	 * //tool.getStatusHolder().setValue(message.alteraStatus(42)); return null;
	 * }
	 * 
	 * if(coords.length >1) //se o arquivo for válido { JGeometry geom =
	 * JGeometry.createLinearPolygon(coords,2,
	 * MapProfile.getMapConfig().getSrid()); JSDOGeometry jgeom =
	 * JSDOGeometry.recast(geom);
	 * 
	 * sf = new StyledFeature(); ShpAtributos atributos = new ShpAtributos(); sf
	 * = atributos.setRecords2StyledFeacture(sf); sf.setGeometry(jgeom);
	 * sf.setFeatureStyle(styles.createColorDefault());
	 * sf.setFeatureStyleName(gpsfile
	 * .getName().substring(0,gpsfile.getName().length()-4));
	 * featureVec.addElement(sf); theme = new
	 * LocalTheme(gpsfile.getName().substring(0,gpsfile.getName().length()-4),
	 * featureVec); } else { //log.error("O arquivo " + gpsfile.getName() +
	 * "é inválido");
	 * //tool.getStatusHolder().setValue(message.alteraStatus(42)); return null;
	 * } } } catch(Exception e) {
	 * //log.error("Erro ao criar o thema com a importação ...\n" +
	 * e.getMessage()); e.printStackTrace(); } return theme; }
	 */

	public double[] captureCoordinates(Coordinate[] coordenadas) {
		if (coordenadas == null || coordenadas.length < 2)
			return null;
		double[] coords = new double[coordenadas.length * 2];
		int countcoord2 = 0;
		// log.info("Convertendo coordenadas para SDOGEOMETRY ...");
		for (int countcoord1 = 0; countcoord1 < coordenadas.length; countcoord1++) {
			if (coordenadas[countcoord1] != null) {
				coords[countcoord2] = coordenadas[countcoord1].x;
				// log.info("x = " + coords[countcoord2]);
				countcoord2++;

				coords[countcoord2] = coordenadas[countcoord1].y;
				// log.info("y = " + coords[countcoord2]);
				countcoord2++;

			}
		}
		return coords;
	}

	public File getGpsfile() {
		return gpsfile;
	}

	public void setGpsfile(String gpsfile) {
		File selFile = new File(gpsfile + ".txt");
		this.gpsfile = selFile;
	}
}
