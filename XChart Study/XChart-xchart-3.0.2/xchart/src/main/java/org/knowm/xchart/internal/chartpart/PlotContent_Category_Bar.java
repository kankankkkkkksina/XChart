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
package org.knowm.xchart.internal.chartpart;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.knowm.xchart.Series_Category;
import org.knowm.xchart.Series_Category.ChartCategorySeriesRenderStyle;
import org.knowm.xchart.Styler_Category;
import org.knowm.xchart.internal.Series;
import org.knowm.xchart.internal.Utils;
import org.knowm.xchart.internal.style.Styler;
import org.knowm.xchart.internal.style.lines.SeriesLines;

/**
 * @author timmolter
 */
public class PlotContent_Category_Bar<ST extends Styler, S extends Series> extends PlotContent_ {

  Styler_Category stylerCategory;

  /**
   * Constructor
   *
   * @param chart
   */
  protected PlotContent_Category_Bar(Chart<Styler_Category, Series_Category> chart) {

    super(chart);
    this.stylerCategory = chart.getStyler();
  }

  @Override
  public void paint(Graphics2D g) {

    Rectangle2D bounds = getBounds();
    // g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
    // g.setColor(Color.red);
    // g.draw(bounds);

    // this is for preventing the series to be drawn outside the plot area if min and max is overridden to fall inside the data range
    Rectangle2D rectangle = new Rectangle2D.Double(0, 0, chart.getWidth(), chart.getHeight());
    // g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
    // g.setColor(Color.green);
    // g.draw(rectangle);
    g.setClip(bounds.createIntersection(rectangle));

    // X-Axis
    double xTickSpace = stylerCategory.getPlotContentSize() * bounds.getWidth();
    double xLeftMargin = Utils.getTickStartOffset(bounds.getWidth(), xTickSpace);

    // Y-Axis
    double yTickSpace = stylerCategory.getPlotContentSize() * bounds.getHeight();
    double yTopMargin = Utils.getTickStartOffset(bounds.getHeight(), yTickSpace);

    Map<String, Series_Category> seriesMap = chart.getSeriesMap();
    int numCategories = seriesMap.values().iterator().next().getXData().size();
    double gridStep = xTickSpace / numCategories;

    double yMin = chart.getAxisPair().getYAxis().getMin();
    double yMax = chart.getAxisPair().getYAxis().getMax();

    // figure out the general form of the chart
    int chartForm = 1; // 1=positive, -1=negative, 0=span
    if (yMin > 0.0 && yMax > 0.0) {
      chartForm = 1; // positive chart
    }
    else if (yMin < 0.0 && yMax < 0.0) {
      chartForm = -1; // negative chart
    }
    else {
      chartForm = 0;// span chart
    }
    // System.out.println(yMin);
    // System.out.println(yMax);
    // System.out.println("chartForm: " + chartForm);

    // plot series
    int seriesCounter = 0;
    for (Series_Category series : seriesMap.values()) {

      // for line series
      double previousX = -Double.MAX_VALUE;
      double previousY = -Double.MAX_VALUE;

      Iterator<? extends Number> yItr = series.getYData().iterator();
      Iterator<? extends Number> ebItr = null;
      Collection<? extends Number> errorBars = series.getErrorBars();
      if (errorBars != null) {
        ebItr = errorBars.iterator();
      }

      int categoryCounter = 0;
      while (yItr.hasNext()) {

        Number next = yItr.next();
        if (next == null) {

          previousX = -Double.MAX_VALUE;
          previousY = -Double.MAX_VALUE;
          categoryCounter++;
          continue;
        }
        double y = next.doubleValue();

        double yTop = 0.0;
        double yBottom = 0.0;
        switch (chartForm) {
        case 1: // positive chart
          // check for points off the chart draw area due to a custom yMin
          if (y < yMin) {
            categoryCounter++;
            continue;
          }
          yTop = y;
          yBottom = yMin;
          break;
        case -1: // negative chart
          // check for points off the chart draw area due to a custom yMin
          if (y > yMax) {
            categoryCounter++;
            continue;
          }
          yTop = yMax;
          yBottom = y;
          break;
        case 0: // span chart
          if (y >= 0.0) { // positive
            yTop = y;
            yBottom = 0.0;
          }
          else {
            yTop = 0.0;
            yBottom = y;
          }
          break;
        default:
          break;
        }

        double yTransform = bounds.getHeight() - (yTopMargin + (yTop - yMin) / (yMax - yMin) * yTickSpace);
        // double yTransform = bounds.getHeight() - (yTopMargin + (y - yMin) / (yMax - yMin) * yTickSpace);

        double yOffset = bounds.getY() + yTransform;

        double zeroTransform = bounds.getHeight() - (yTopMargin + (yBottom - yMin) / (yMax - yMin) * yTickSpace);
        double zeroOffset = bounds.getY() + zeroTransform;
        double xOffset;
        double barWidth;

        if (stylerCategory.isOverlapped()) {
          double barWidthPercentage = stylerCategory.getAvailableSpaceFill();
          barWidth = gridStep * barWidthPercentage;
          double barMargin = gridStep * (1 - barWidthPercentage) / 2;
          xOffset = bounds.getX() + xLeftMargin + gridStep * categoryCounter++ + barMargin;
        }
        else {
          double barWidthPercentage = stylerCategory.getAvailableSpaceFill();
          barWidth = gridStep / chart.getSeriesMap().size() * barWidthPercentage;
          double barMargin = gridStep * (1 - barWidthPercentage) / 2;
          xOffset = bounds.getX() + xLeftMargin + gridStep * categoryCounter++ + seriesCounter * barWidth + barMargin;
        }

        // paint series
        if (series.getChartCategorySeriesRenderStyle() == ChartCategorySeriesRenderStyle.Bar) {

          // paint bar
          Path2D.Double path = new Path2D.Double();
          path.moveTo(xOffset, yOffset);
          path.lineTo(xOffset + barWidth, yOffset);
          path.lineTo(xOffset + barWidth, zeroOffset);
          path.lineTo(xOffset, zeroOffset);
          path.closePath();

          // g.setStroke(series.getLineStyle());
          // g.setColor(series.getLineColor());
          // g.draw(path);
          g.setColor(series.getFillColor());
          g.fill(path);
        }
        else if (ChartCategorySeriesRenderStyle.Stick.equals(series.getChartCategorySeriesRenderStyle())) {

          // paint line
          if (series.getLineStyle() != SeriesLines.NONE) {

            g.setColor(series.getLineColor());
            g.setStroke(series.getLineStyle());
            Shape line = new Line2D.Double(xOffset + barWidth / 2, zeroOffset, xOffset + barWidth / 2, yOffset);
            g.draw(line);
          }

          // paint marker
          // if (series.getMarker() != null) {
          if (stylerCategory.shouldShowMarkers() && series.getMarker() != null) { // if set to Marker.NONE,
                                                                                  // the
            g.setColor(series.getMarkerColor());

            if (y <= 0) {
              series.getMarker().paint(g, xOffset + barWidth / 2, zeroOffset, stylerCategory.getMarkerSize());
            }
            else {
              series.getMarker().paint(g, xOffset + barWidth / 2, yOffset, stylerCategory.getMarkerSize());
            }
          }
        }
        else {

          // paint line
          if (series.getChartCategorySeriesRenderStyle() == ChartCategorySeriesRenderStyle.Line) {

            if (series.getLineStyle() != SeriesLines.NONE) {

              if (previousX != -Double.MAX_VALUE && previousY != -Double.MAX_VALUE) {
                g.setColor(series.getLineColor());
                g.setStroke(series.getLineStyle());
                Shape line = new Line2D.Double(previousX, previousY, xOffset + barWidth / 2, yOffset);
                g.draw(line);
              }
            }
          }
          previousX = xOffset + barWidth / 2;
          previousY = yOffset;

          // paint marker
          if (series.getMarker() != null) {
            g.setColor(series.getMarkerColor());
            series.getMarker().paint(g, previousX, previousY, stylerCategory.getMarkerSize());
          }

        }

        // paint error bars

        if (errorBars != null) {

          double eb = ebItr.next().doubleValue();

          // set error bar style
          if (stylerCategory.isErrorBarsColorSeriesColor()) {
            g.setColor(series.getLineColor());
          }
          else {
            g.setColor(stylerCategory.getErrorBarsColor());
          }
          g.setStroke(errorBarStroke);

          // Top value
          double topValue = y + eb;
          double topEBTransform = bounds.getHeight() - (yTopMargin + (topValue - yMin) / (yMax - yMin) * yTickSpace);
          double topEBOffset = bounds.getY() + topEBTransform;

          // Bottom value
          double bottomValue = y - eb;
          double bottomEBTransform = bounds.getHeight() - (yTopMargin + (bottomValue - yMin) / (yMax - yMin) * yTickSpace);
          double bottomEBOffset = bounds.getY() + bottomEBTransform;

          // Draw it
          double errorBarOffset = xOffset + barWidth / 2;
          Shape line = new Line2D.Double(errorBarOffset, topEBOffset, errorBarOffset, bottomEBOffset);
          g.draw(line);
          line = new Line2D.Double(errorBarOffset - 3, bottomEBOffset, errorBarOffset + 3, bottomEBOffset);
          g.draw(line);
          line = new Line2D.Double(errorBarOffset - 3, topEBOffset, errorBarOffset + 3, topEBOffset);
          g.draw(line);
        }

      }
      seriesCounter++;
    }
    g.setClip(null);
  }

}
