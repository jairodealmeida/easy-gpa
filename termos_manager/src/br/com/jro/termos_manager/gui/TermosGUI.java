/**
 * 
 */
package br.com.jro.termos_manager.gui;

import java.sql.Date;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

import br.com.jro.termos_manager.dao.bean.Interface;
import br.com.jro.termos_manager.dao.bean.InterfaceGroup;
import br.com.jro.termos_manager.dao.bean.Termo;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.layout.TableColumnLayout;

/**
 * @author Jairo de Almeida - jairo.almeida@proxima.agr.br
 * @size 11/04/2012
 * termos_manager 	
 */
public class TermosGUI {

	protected Shell shell;
	/**
	 * @wbp.nonvisual location=407,59
	 */
	//private final Termos termos = new Termos();
	/**
	 * @wbp.nonvisual location=237,269
	 */

	private Table tableTermos;
	private Table tableTermosIdioma;
	private static InterfaceGroup interfaces = new InterfaceGroup();
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TermosGUI window = new TermosGUI();
			
			Interface group1 = new Interface("Interface1");
			interfaces.addInterface(group1);
		    group1.addTermo(new Termo(new Long(1),"termo1", "Descricao do termo 1", 12345, new Date(System.currentTimeMillis()), ""));
		    group1.addTermo(new Termo(new Long(2),"termo2", "Descricao do termo 2", 12346, new Date(System.currentTimeMillis()), ""));
		    group1.addTermo(new Termo(new Long(3),"termo3", "Descricao do termo 3", 12347, new Date(System.currentTimeMillis()), ""));
			
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		ListViewer listViewer = new ListViewer(shell, SWT.BORDER | SWT.V_SCROLL);
		List listInterfaces = listViewer.getList();
		listInterfaces.setToolTipText("Lista as interfaces criadas");
		listInterfaces.setBounds(10, 48, 100, 166);
		
		Label lblInterfaces = new Label(shell, SWT.NONE);
		lblInterfaces.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblInterfaces.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblInterfaces.setBounds(10, 31, 100, 15);
		lblInterfaces.setText("Interfaces");
		
		Button btnCadastrar = new Button(shell, SWT.NONE);
		btnCadastrar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCadastrar.setBounds(349, 220, 75, 25);
		btnCadastrar.setText("Cadastrar ");
		
		Button btnCancelar = new Button(shell, SWT.NONE);
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCancelar.setBounds(271, 220, 75, 25);
		btnCancelar.setText("Cancelar");
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(10, 220, 91, 23);
		
		Spinner spinner = new Spinner(shell, SWT.BORDER);
		spinner.setBounds(116, 220, 47, 22);
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(116, 48, 308, 166);
		
		TabItem tbtmTermos = new TabItem(tabFolder, SWT.NONE);
		tbtmTermos.setText("Termos");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmTermos.setControl(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		tableTermos = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		tableTermos.setToolTipText("Tabela de termos");
		tableTermos.setHeaderVisible(true);
		tableTermos.setLinesVisible(true);
		scrolledComposite.setContent(tableTermos);
		scrolledComposite.setMinSize(tableTermos.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		TabItem tbtmTermoIdioma = new TabItem(tabFolder, SWT.NONE);
		tbtmTermoIdioma.setText("Termos idioma");
		
		tableTermosIdioma = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmTermoIdioma.setControl(tableTermosIdioma);
		tableTermosIdioma.setHeaderVisible(true);
		tableTermosIdioma.setLinesVisible(true);
		
		TabItem tbtmScriptsSql = new TabItem(tabFolder, SWT.NONE);
		tbtmScriptsSql.setText("Scripts sql");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmScriptsSql.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		
		TextViewer textViewer = new TextViewer(scrolledComposite_1, SWT.BORDER);
		StyledText textAreaScripts = textViewer.getTextWidget();
		textAreaScripts.setToolTipText("Scripts gerados");
		scrolledComposite_1.setContent(textAreaScripts);
		scrolledComposite_1.setMinSize(textAreaScripts.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("New Item");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite);
		composite.setLayout(new TableColumnLayout());
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}
}
