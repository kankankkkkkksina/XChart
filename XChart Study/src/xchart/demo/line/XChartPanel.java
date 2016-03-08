package xchart.demo.line;

import javax.swing.JPanel;

public class XChartPanel<T extends Chart> extends JPanel {

	private final T chart;
	
	public XChartPanel(final T chart) {
		// TODO 自动生成的构造函数存根
		this.chart = chart;
	}

}
