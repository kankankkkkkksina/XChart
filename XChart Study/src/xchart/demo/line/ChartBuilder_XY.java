package xchart.demo.line;

public class ChartBuilder_XY extends ChartBuilder<ChartBuilder_XY,Chart_XY>{

	private String xAxisTitle = "";
	private String yAxisTitle = "";

	public ChartBuilder_XY() {}

	public ChartBuilder_XY xAxisTitle(String xAxisTitle) {
		// TODO 自动生成的方法存根
		this.xAxisTitle = xAxisTitle;
		return this;
	}

	public ChartBuilder_XY yAxisTitle(String yAxisTitle) {
		// TODO 自动生成的方法存根
		this.yAxisTitle = yAxisTitle;
		return this;
	}

	public Chart_XY build() {
		// TODO 自动生成的方法存根
		return new Chart_XY(this);
	}
}
