package xchart.demo.line;

public class ChartBuilder_XY extends ChartBuilder<ChartBuilder_XY,Chart_XY>{

	private String xAxisTitle = "";
	private String yAxisTitle = "";

	public ChartBuilder_XY() {}

	public ChartBuilder_XY xAxisTitle(String xAxisTitle) {
		// TODO �Զ����ɵķ������
		this.xAxisTitle = xAxisTitle;
		return this;
	}

	public ChartBuilder_XY yAxisTitle(String yAxisTitle) {
		// TODO �Զ����ɵķ������
		this.yAxisTitle = yAxisTitle;
		return this;
	}

	public Chart_XY build() {
		// TODO �Զ����ɵķ������
		return new Chart_XY(this);
	}
}
