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
package org.knowm.xchart.demo.charts.pie;

import org.knowm.xchart.ChartBuilder_Pie;
import org.knowm.xchart.Chart_Pie;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;

/**
 * Pie Chart with 4 Slices
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>Pie Chart
 * <li>ChartBuilderPie
 */
public class PieChart01 implements ExampleChart<Chart_Pie> {

  public static void main(String[] args) {

    ExampleChart<Chart_Pie> exampleChart = new PieChart01();
    Chart_Pie chart = exampleChart.getChart();
    new SwingWrapper(chart).displayChart();
  }

  @Override
  public Chart_Pie getChart() {

    // Create Chart
    Chart_Pie chart = new ChartBuilder_Pie().width(800).height(600).title(getClass().getSimpleName()).build();

    // Customize Chart

    // Series
    chart.addSeries("Pennies", 100);
    chart.addSeries("Nickels", 100);
    chart.addSeries("Dimes", 100);
    chart.addSeries("Quarters", 100);

    return chart;
  }

}
