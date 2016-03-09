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
package org.knowm.xchart.demo.charts.theme;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.ChartBuilder_XY;
import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.Series_XY;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.style.Styler.ChartTheme;

/**
 * Matlab Theme
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>Building a Chart with ChartBuilder
 * <li>Applying the Matlab Theme to the Chart
 * <li>Generating Gaussian Bell Curve Data
 */
public class ThemeChart03 implements ExampleChart {

  public static void main(String[] args) {

    ExampleChart exampleChart = new ThemeChart03();
    Chart chart = exampleChart.getChart();
    new SwingWrapper(chart).displayChart();
  }

  @Override
  public Chart getChart() {

    // Create Chart
    Chart_XY chart = new ChartBuilder_XY().width(800).height(600).theme(ChartTheme.Matlab).title("Matlab Theme").xAxisTitle("X").yAxisTitle("Y").build();

    // Customize Chart
    chart.getStyler().setPlotGridLinesVisible(false);
    chart.getStyler().setXAxisTickMarkSpacingHint(100);

    // Series
    List<Integer> xData = new ArrayList<Integer>();
    for (int i = 0; i < 640; i++) {
      xData.add(i);
    }
    List<Double> y1Data = getYAxis(xData, 320, 160);
    List<Double> y2Data = getYAxis(xData, 320, 320);
    List<Double> y3Data = new ArrayList<Double>(xData.size());
    for (int i = 0; i < 640; i++) {
      y3Data.add(y1Data.get(i) - y2Data.get(i));
    }

    Series_XY series = chart.addSeries("Gaussian 1", xData, y1Data);
    chart.addSeries("Gaussian 2", xData, y2Data);
    chart.addSeries("Difference", xData, y3Data);

    return chart;
  }

  private List<Double> getYAxis(List<Integer> xData, double mean, double std) {

    List<Double> yData = new ArrayList<Double>(xData.size());

    for (int i = 0; i < xData.size(); i++) {
      yData.add((1 / (std * Math.sqrt(2 * Math.PI))) * Math.exp(-(((xData.get(i) - mean) * (xData.get(i) - mean)) / ((2 * std * std)))));
    }
    return yData;
  }

}
