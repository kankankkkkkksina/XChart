package xchart.demo.line;

import java.nio.CharBuffer;

import xchart.demo.line.Styler.ChartTheme;

public class Chart_XY extends Chart<Styler_XY, Series_XY>{

	public Chart_XY(ChartBuilder_XY chartBuilder) {
		// TODO �Զ����ɵĹ��캯�����
		this.(chartBuilder.width, chartBuilder.height, chartBuilder.chartTheme);
	}
	public Chart_XY(int width, int height, ChartTheme chartTheme) {
		this(width, height, chartTheme.newInstance(chartTheme));
	}
	public Chart_XY(int width, int height, Theme_ theme) {
		// TODO �Զ����ɵĹ��캯�����
		this(width, height);
		styler.setTheme(theme);
	}

}
