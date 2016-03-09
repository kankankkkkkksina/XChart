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
package org.knowm.xchart.demo.charts.realtime;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

import org.knowm.xchart.Chart_XY;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.internal.chartpart.Chart;

/**
 * Realtime
 * <p>
 * Demonstrates the following:
 * <ul>
 * <li>real-time chart updates
 * <li>dynamic window
 */
public class RealtimeChart02 implements ExampleChart {

  private Chart chart;
  public static final String SERIES_NAME = "series1";
  private List<Integer> xData;
  private List<Double> yData;

  public static void main(String[] args) {

    // Setup the panel
    final RealtimeChart02 realtimeChart02 = new RealtimeChart02();
    final XChartPanel chartPanel = realtimeChart02.buildPanel();

    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("XChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
      }
    });

    // Simulate a data feed
    TimerTask chartUpdaterTask = new TimerTask() {

      @Override
      public void run() {

        realtimeChart02.updateData();
        chartPanel.updateSeries(SERIES_NAME, realtimeChart02.getxData(), realtimeChart02.getyData(), null);

      }
    };

    Timer timer = new Timer();
    timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);

  }

  public XChartPanel buildPanel() {

    return new XChartPanel(getChart());
  }

  @Override
  public Chart getChart() {

    xData = getMonotonicallyIncreasingData(5);
    yData = getRandomData(5);

    // Create Chart
    Chart_XY chart = new Chart_XY(500, 400);
    chart.setTitle("Sample Real-time Chart");
    chart.setXAxisTitle("X");
    chart.setYAxisTitle("Y");
    chart.addSeries(SERIES_NAME, xData, yData);

    return chart;
  }

  private List<Double> getRandomData(int numPoints) {

    List<Double> data = new CopyOnWriteArrayList<Double>();
    for (int i = 0; i < numPoints; i++) {
      data.add(Math.random() * 100);
    }
    return data;
  }

  private List<Integer> getMonotonicallyIncreasingData(int numPoints) {

    List<Integer> data = new CopyOnWriteArrayList<Integer>();
    for (int i = 0; i < numPoints; i++) {
      data.add(i);
    }
    return data;
  }

  public void updateData() {

    // Get some new data
    List<Double> newData = getRandomData(1);

    yData.addAll(newData);

    // Limit the total number of points
    while (yData.size() > 20) {
      yData.remove(0);
    }

    xData.add(xData.get(xData.size() - 1) + 1);
    while (xData.size() > 20) {
      xData.remove(0);
    }
  }

  public List<Double> getyData() {

    return yData;
  }

  public List<Integer> getxData() {

    return xData;
  }
}
