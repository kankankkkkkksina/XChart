/**
 * Copyright 2015-2016 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2011-2015 Xeiam LLC (http://xeiam.com) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.xchart.demo.charts.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.knowm.xchart.ChartBuilder_XY;
import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.internal.chartpart.Chart;

/**
 * Year scale
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>Rotated X-Axis labels
 * <li>Setting a custom date formatter String
 */
public class DateChart08 implements ExampleChart {

    public static void main(String[] args) {

	ExampleChart exampleChart = new DateChart08();
	Chart chart = exampleChart.getChart();
	new SwingWrapper(chart).displayChart();
    }

    @Override
    public Chart getChart() {

	// Create Chart
	Chart_XY chart = new ChartBuilder_XY().width(800).height(600).title("Rotated Tick Labels").build();

	// Customize Chart
	chart.getStyler().setLegendVisible(true);
	chart.getStyler().setXAxisLabelRotation(90);// 旋转X轴文字
	chart.getStyler().setDatePattern("yyyy-MM-dd");

	// Series
	List<Date> xData = new ArrayList<Date>();
	List<Double> yData = new ArrayList<Double>();

	Random random = new Random();

	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;
	for (int i = 1; i <= 14; i++) {
	    try {
		date = sdf.parse("" + (2001 + i) + "-" + random.nextInt(12) + "-" + random.nextInt(28));
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    xData.add(date);
	    yData.add(Math.random() * i);
	}

	chart.addSeries("blah", xData, yData);

	return chart;

    }
}
