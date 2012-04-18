/**
 * 
 */
package br.com.jro.termos_manager.dao.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 13/04/2012
 * termos_manager 	
 */
public class InterfaceGroup extends AbstractModelObject{
	List<Interface> interfaces = new ArrayList<Interface>();
	public List<Interface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	public void addInterface(Interface bean){
		interfaces.add(bean);
	}
}
