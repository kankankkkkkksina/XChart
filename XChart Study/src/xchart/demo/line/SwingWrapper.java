package xchart.demo.line;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingWrapper<T extends Chart> {
	
	private String windowTitle = "XChart";
	private List<T> charts = new ArrayList<T>();
	public SwingWrapper(T chart){
		
		this.charts.add(chart);
	}
	public JFrame displayChart() {
		// TODO 自动生成的方法存根
		final JFrame frame = new JFrame(windowTitle);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel chartPanel = new XChartPanel<T>(charts.get(0));
			}
		});
		return frame;
	}
}
