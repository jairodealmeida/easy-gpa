/**
 * 
 */
package br.com.jro.termos_manager.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import br.com.jro.termos_manager.dao.bean.Termos;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.wb.swt.SWTResourceManager;

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
	private final Termos termos = new Termos();
	/**
	 * @wbp.nonvisual location=237,269
	 */
	private final Termos termos_1 = new Termos();
	private Table table;
	private Table table_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TermosGUI window = new TermosGUI();
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
		List list = listViewer.getList();
		list.setBounds(10, 48, 100, 166);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		lblNewLabel.setBounds(10, 31, 100, 15);
		lblNewLabel.setText("Interfaces");
		
		Button btnCadastrar = new Button(shell, SWT.NONE);
		btnCadastrar.setBounds(349, 220, 75, 25);
		btnCadastrar.setText("Cadastrar ");
		
		Button btnCancelar = new Button(shell, SWT.NONE);
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
		
		table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		TabItem tbtmTermoIdioma = new TabItem(tabFolder, SWT.NONE);
		tbtmTermoIdioma.setText("Termos idioma");
		
		table_1 = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmTermoIdioma.setControl(table_1);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		TabItem tbtmScriptsSql = new TabItem(tabFolder, SWT.NONE);
		tbtmScriptsSql.setText("Scripts sql");
		
		ScrolledComposite scrolledComposite_1 = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmScriptsSql.setControl(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);

	}
}
