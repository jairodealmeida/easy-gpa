package br.com.jro.developer.tools.shapefile;

import java.io.IOException;

import org.geotools.feature.AttributeType;
import org.geotools.feature.AttributeTypeFactory;
import org.geotools.feature.Feature;
import org.geotools.feature.FeatureType;
import org.geotools.feature.FeatureTypes;
import org.geotools.feature.IllegalAttributeException;
import org.geotools.feature.SchemaException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * Classe para manipular arquivos do formato texto que contenham coodenadas
 * geradas por um GPS Podemos caracterizar estas coordenadas como pontos
 * diferenciados pelo primeiro caractes da linha "W" = Implementadas - este
 * caracter diferencia as coordenadas levantadas pelo GPS como Implementadas ou
 * seja as coordenadas que corespondem aos vértices do objeto. "T" - Virtuais -
 * este caracter diferencia as coordenadas levantadas pelo GPS como Virtuais ou
 * seja aquelas coordenadas que complementão as coordenadas Implementadas.
 * 
 * É necessário : loadGpsVirtualMark - carregar arquivo txt com as coordenadas
 * do GPS para o Objeto CreateShapefile importGPS2PolygonShapeFile - importar o
 * arquivo txt carregado como um arquivos shapefile definindo um local e nome do
 * arquivo
 * 
 * @Class Gps2Shapefile
 * @author Jairo de Almeida
 * @since 20/12/2006
 */
/**
 * @author jairo.almeida
 * 
 */
public class CreateShapefile {

	/**
	 * Método que cria um novo shape, recuperando o theme do Mapmaker no momento
	 * da exportação.
	 * @param nomeShape Nome do arquivo que o shape vai ser criado.
	 * @param diretorio Diretorio onde o arquivo vai ser criado.
	 * @param theme Theme que vai ser convertido para arquivo shapefile.
	 * @return True se conseguiu finalizar a exportação.
	 */
	/*
	 * public boolean createNewShape(String nomeShape, String diretorio, Theme
	 * theme) { if (theme == null) { return false; } StyledFeature[] features =
	 * theme.getStyledFeatures(); if (features == null) { return false; }
	 * Geometry[] jts = new Geometry[features.length]; Field[][] fields = new
	 * Field[features.length][]; int GTYPE = 0; for (int i = 0; i <
	 * features.length; i++) { fields[i] =
	 * features[i].getIdentifiableAttributes(); JSDOGeometry geom =
	 * features[i].getGeometry(); GTYPE = geom.getType() + geom.getDimensions()
	 * * 1000; int SRID = MapProfile.getMapConfig().getSrid(); double POINT[] =
	 * geom.getPoint(); int ELEMENINFO[] = geom.getElemInfo(); double
	 * ORDINATES[] = geom.getOrdinatesArray(); jts[i] = SDO.create(new
	 * GeometryFactory(), GTYPE, SRID, POINT, ELEMENINFO, ORDINATES); } Feature
	 * feature = null; try { FeatureCollection collection =
	 * FeatureCollections.newCollection(); for (int i = 0; i < features.length;
	 * i++) { if (GTYPE == 2003) feature =
	 * createPolygonGeometry(jts[i].getCoordinates(), fields[i]);
	 * 
	 * if (GTYPE == 2002) feature = createLineGeometry(jts[i].getCoordinates(),
	 * fields[i]);
	 * 
	 * if (GTYPE == 2001) feature = createPointGeometry(jts[i].getCoordinate(),
	 * fields[i]);
	 * 
	 * collection.add(feature); } URL shape = new File(diretorio + "\\" +
	 * nomeShape).toURL(); ShapefileDataStore newShapefileDataStore = new
	 * ShapefileDataStore( shape);
	 * newShapefileDataStore.createSchema(collection.getSchema()); FeatureSource
	 * newFeatureSource = newShapefileDataStore .getFeatureSource();
	 * FeatureStore newFeatureStore = (FeatureStore) newFeatureSource;
	 * Transaction t = newFeatureStore.getTransaction();
	 * newFeatureStore.addFeatures(collection); t.commit(); t.close(); return
	 * true; } catch (IllegalAttributeException e) { //
	 * log.error("Erro ao criar o shapefile: " +e.getMessage());
	 * e.printStackTrace(); return false; } }
	 */

	/**
	 * Metodo que cria as features de poligonos. Se houver atributos os seta em
	 * suas respectivas features.
	 * 
	 * @param polygonCoordinates Parametro obrigatorio que contem as coordenadas JTS das features.
	 * @param fields Parametros SDO que serão setados nas features. Null é permitido.
	 * @return retorna a feature criada.
	 * @throws IllegalAttributeException
	 * @throws SchemaException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	/*
	 * public Feature createPolygonGeometry(Coordinate[] polygonCoordinates,
	 * Field[] fields) throws IllegalAttributeException, SchemaException,
	 * IOException, InterruptedException { GeometryFactory geomFac = new
	 * GeometryFactory();
	 * 
	 * LinearRing ring = geomFac.createLinearRing(polygonCoordinates); Polygon
	 * polygon = geomFac.createPolygon(ring,null);
	 * 
	 * AttributeType[] types = null; Object[] objetos = null;
	 * 
	 * if(fields!=null) { types = new AttributeType[fields.length]; types[0] =
	 * AttributeTypeFactory.newAttributeType("geom", polygon.getClass()); types
	 * = parseField(types,fields);
	 * 
	 * objetos = new Object[fields.length]; objetos[0] = polygon; objetos =
	 * parseValues(objetos,fields); } else { types = new AttributeType[1];
	 * types[0] = AttributeTypeFactory.newAttributeType("geom",
	 * polygon.getClass()); objetos = new Object[1]; objetos[0] = polygon; }
	 * 
	 * FeatureType polygonType =
	 * FeatureTypes.newFeatureType(types,"polygonfeature");
	 * 
	 * Feature polygonFeature = polygonType.create(objetos); return
	 * polygonFeature; }
	 */

	/**
	 * Metodo que cria as features de linhas. Se houver atributos os seta em
	 * suas respectivas features.
	 * 
	 * @param lineCoordinates Parametro obrigatorio que contem as coordenadas JTS das features.
	 * @param fields Parametros SDO que serão setados nas features. Null é permitido.
	 * @return retorna a feature criada.
	 * @throws IllegalAttributeException
	 * @throws SchemaException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Feature createLineGeometry(Coordinate[] lineCoordinates,
			Field[] fields) throws Exception{
		GeometryFactory geomFac = new GeometryFactory();
		LineString line = geomFac.createLineString(lineCoordinates);
		AttributeType[] types = null;
		Object[] objetos = null;
		if (fields != null) {
			types = new AttributeType[fields.length];
			types[0] = AttributeTypeFactory.newAttributeType("geom",
					line.getClass());
			types = parseField(types, fields);

			objetos = new Object[fields.length];
			objetos[0] = line;
			objetos = parseValues(objetos, fields);
		} else {
			types = new AttributeType[1];
			types[0] = AttributeTypeFactory.newAttributeType("geom",
					line.getClass());
			objetos = new Object[1];
			objetos[0] = line;
		}
		FeatureType lineType = FeatureTypes
				.newFeatureType(types, "linefeature");
		Feature lineFeature = lineType.create(objetos);
		return lineFeature;
	}

	/**
	 * Metodo que cria as features de pontos. Se houver atributos os seta em
	 * suas respectivas features.
	 * 
	 * @param c Coordenada XY do ponto que vai ser criado.Coordenada JTS
	 * @param fields Parametros SDO que serão setados nas features. Null é permitido.
	 * @return retorna a feature criada.
	 * @throws IllegalAttributeException
	 * @throws SchemaException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Feature createPointGeometry(Coordinate c, Field[] fields)
			throws IllegalAttributeException, SchemaException, IOException,
			InterruptedException {
		GeometryFactory geomFac = new GeometryFactory();

		Point point = geomFac.createPoint(c);

		AttributeType[] types = null;
		Object[] objetos = null;

		if (fields != null) {
			types = new AttributeType[fields.length];
			types[0] = AttributeTypeFactory.newAttributeType("geom",
					point.getClass());
			types = parseField(types, fields);

			objetos = new Object[fields.length];
			objetos[0] = point;
			objetos = parseValues(objetos, fields);
		} else {
			types = new AttributeType[1];
			types[0] = AttributeTypeFactory.newAttributeType("geom",
					point.getClass());
			objetos = new Object[1];
			objetos[0] = point;
		}

		FeatureType pointType = FeatureTypes.newFeatureType(types,
				"pointfeature");

		Feature pointFeature = pointType.create(objetos);
		return pointFeature;
	}

	/**
	 * Recupera a classe e o nome do atributo da feature, setando no novo shape.
	 */
	private AttributeType[] parseField(AttributeType[] types, Field[] fields) {
		Class classe = null;
		String name = null;
		for (int i = 1; i < fields.length; i++){
			classe = fields[i].getJavaClass();
			name = fields[i].getName();
			types[i] = AttributeTypeFactory.newAttributeType(name, classe);
		}
		return types;
	}

	/**
	 * Recupera o valor do atributo da feature, setando no novo shape.
	 */
	private Object[] parseValues(Object[] objetos, Field[] fields) {
		Object value = null;
		for (int i = 1; i < fields.length; i++) {
			value = fields[i].getValue();
			objetos[i] = value;
		}
		return objetos;
	}


}
