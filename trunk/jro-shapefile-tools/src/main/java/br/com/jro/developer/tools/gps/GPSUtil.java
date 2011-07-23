package br.com.jro.developer.tools.gps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;

public class GPSUtil {
	
	private int totalVirtualMarks = 0;
	private int totalImplementedMarks = 0;
	private ArrayList implementedMarks = new ArrayList();
	private ArrayList virtualMarks = new ArrayList();
	
	/**
	 * Méthodo get para setar ArrayList com os marcos virtuais contidos de
	 * arquivo GPS (txt)
	 * @param filepath - String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setVirtualMarks(String filepath) {
		try {
			FileReader file = new FileReader(filepath);
			BufferedReader read = new BufferedReader(file);
			int countVirtualMarks = 0;
			String line = "";
			System.out.println("\nCarregando marcos virtuais do arquivo ..: " + filepath + "\n");
			while ((line = read.readLine()) != null) {
				String[] splitline = line.split(",");
				if (splitline != null) {
					if (splitline[0].equalsIgnoreCase("T")){ // é um marco implementado
						countVirtualMarks++; // conta ele
						this.virtualMarks.add(line);
						System.out.println(line);
					}
				}
			}
			countVirtualMarks++;
			if (virtualMarks.size() > 1) {
				this.virtualMarks.add(virtualMarks.get(0)); // para fechar o polygono
				System.out.println(virtualMarks.get(0));
			}
			this.totalVirtualMarks = countVirtualMarks;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthodo get para pegar a quantidade de marcos implementados contidos de
	 * arquivo GPS (txt)
	 * 
	 * @return - int
	 */
	public int getTotalImplementedMarks() {
		return totalImplementedMarks;
	}

	/**
	 * Méthodo get para pegar o ArrayList com os marcos implementados contidos
	 * de arquivo GPS (txt)
	 * 
	 * @return - ArrayList
	 */
	public ArrayList getImplementedMarks() {
		return implementedMarks;
	}

	/**
	 * Méthodo para setar ArrayList com os marcos implementados contidos de
	 * arquivo GPS (txt)
	 * @param filepath - String
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void setImplementedMarks(String filepath) {
		try {
			FileReader file = new FileReader(filepath);
			BufferedReader read = new BufferedReader(file);
			int countImplementedMarks = 0;
			String line = "";
			System.out.println("\nCarregando marcos implementados do arquivo ..: "	+ filepath + "\n");
			while ((line = read.readLine()) != null) {
				String[] splitline = line.split(",");
				if (splitline != null) {
					if (splitline[0].equalsIgnoreCase("W")){ // é um marco implementado
						countImplementedMarks++; // conta ele
						this.implementedMarks.add(line);
						System.out.println(line);
					}
				}
			}
			countImplementedMarks++;
			if (implementedMarks.size() > 1) {
				this.implementedMarks.add(implementedMarks.get(0)); // para fechar o polygono
				System.out.println(implementedMarks.get(0));
			} else
				return;

			this.totalImplementedMarks = countImplementedMarks;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que carrega as coordenadas virtuais de um arquivo texto(gps) para
	 * um array de coordenadas
	 * 
	 * @param filepath
	 *            - String
	 * @return Coordinate[]
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Coordinate[] loadGpsVirtualMark(File filepath) {
		try {
			setVirtualMarks(filepath.getAbsolutePath());
			Coordinate[] polygonCoordinates = new Coordinate[totalVirtualMarks];
			System.out.println("\nInfo..: Imprimindo coordenadas (marcos virtuais) ..: \n");
			for (int count = 0; count < virtualMarks.size(); count++) {
				String line = (String) virtualMarks.get(count);
				if (line != null) {
					String[] splitline = line.split(",");
					if (splitline != null) {
						if (splitline[0].equalsIgnoreCase("T")){ // é um marco implementado
							polygonCoordinates[count] = new Coordinate(
									Double.parseDouble(splitline[3]),
									Double.parseDouble(splitline[4]));
							System.out.println(splitline[3] + " , "
									+ splitline[4]);
						}
					}
				}
			}
			return polygonCoordinates;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metodo que carrega as coordenadas implementadas de um arquivo texto(gps)
	 * para um array de coordenadas
	 * 
	 * @param filepath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Coordinate[] loadGpsImplementedMark(File filepath) {
		try {
			setImplementedMarks(filepath.getAbsolutePath());
			Coordinate[] polygonCoordinates = new Coordinate[totalImplementedMarks];
			// log.info("Imprimindo coordenadas (marcos implementados) ..:");
			for (int count = 0; count < implementedMarks.size(); count++) {
				String line = (String) implementedMarks.get(count);
				if (line != null) {
					String[] splitline = line.split(",");
					if (splitline != null) {
						if (splitline[0].equalsIgnoreCase("W")){ // é um marco implementado
							polygonCoordinates[count] = new Coordinate(
									Double.parseDouble(splitline[4]),
									Double.parseDouble(splitline[5]));
						}
					}
				}
			}
			return polygonCoordinates;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
