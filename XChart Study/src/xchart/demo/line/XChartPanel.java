package xchart.demo.line;

import javax.swing.JPanel;

public class XChartPanel<T extends Chart> extends JPanel {

	private final T chart;
	
	public XChartPanel(final T chart) {
		// TODO �Զ����ɵĹ��캯�����
		this.chart = chart;
	}

}
