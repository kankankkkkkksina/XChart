package xchart.demo.line;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class line {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//显示JFrame
		Chart chart = new line().getChart();
		new SwingWrapper(chart).displayChart(); 


	}

	private Chart getChart() {
		// TODO 自动生成的方法存根
		Chart_XY chart = new ChartBuilder_XY().width(800).height(600).title("Powers of Ten").xAxisTitle("Power").yAxisTitle("Value").build();
		return null;
	}

	
}
