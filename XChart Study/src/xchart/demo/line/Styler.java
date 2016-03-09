package xchart.demo.line;

import xchart.demo.line.Styler.ChartTheme;

public abstract class Styler {
//	public enum LegendPosition{
//		
//		
//	}
	public enum ChartTheme{
		
		XChart, GGPlot2, Matlab;

		public Theme_ newInstance(ChartTheme chartTheme) {
			// TODO 自动生成的方法存根
			switch (chartTheme) {
			case GGPlot2:
				return new Theme_GGPlot2();
			case Matlab:
				return new Theme_Matlab();
			case XChart:
				return new Theme_XChart();

			default:
				break;
			}
			return null;
		}
	}
}
