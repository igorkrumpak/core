package si.iitech.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.Shape;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Color;

public class IITechChartUtils {

	public static ChartObject createChartObject(String notation, Color color, List<Double> yValues) {
		return new ChartObject(notation, color, yValues);
	}

	public static ChartObject createChartObjectWithShape(String notation, Color color, Shape shape, List<Double> yValues) {
		return new ChartObject(notation, color, shape, yValues);
	}

	public static byte[] chartWithDetails(int width, int height, String title, ChartObject... chartObjects) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		for (ChartObject chartObject : chartObjects) {
			final XYSeries series = new XYSeries(chartObject.getName());
			List<Double> values = chartObject.getYValues();
			for (int i = 0; i < values.size(); i++) {
				series.add(i, values.get(i));
			}
			dataset.addSeries(series);
		}
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultItemLabelGenerator(new XYItemLabelGenerator() {
			@Override
			public String generateLabel(XYDataset dataset, int series, int item) {
				if (dataset.getItemCount(series) - 1 == item) {
					double value = dataset.getYValue(series, item);
					if (value <= 0.00000001) {
						return String.format("%,.10f", value);
					} 
					if (value <= 0.000001) {
						return String.format("%,.8f", value);
					} 
					if (value <= 0.0001) {
						return String.format("%,.6f", value);
					} 
					if (value <= 0.01) {
						return String.format("%,.4f", value);
					} 
					if (value > 0.01 && value < 1000) {
						return String.format("%,.2f", value);
					}
					return String.format("%.0f", value);
					
				}
				return "";
			}
		});

		for (int i = 0; i < chartObjects.length; i++) {
			renderer.setSeriesShapesVisible(i, false);
			renderer.setSeriesPaint(i, chartObjects[i].getColor());
			renderer.setSeriesItemLabelPaint(i, chartObjects[i].getColor());
			renderer.setSeriesPositiveItemLabelPosition(i, new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.CENTER_LEFT));
		}

		int seriesWithLowestLastValue = getSeriesWithLowestLastValue(chartObjects);
		int seriesWithHighestLastValue = getSeriesWithHighestLastValue(chartObjects);

		renderer.setSeriesPositiveItemLabelPosition(seriesWithLowestLastValue, new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.TOP_LEFT));
		renderer.setSeriesPositiveItemLabelPosition(seriesWithHighestLastValue, new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.BOTTOM_LEFT));


		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				title,
				"",
				"",
				dataset,
				PlotOrientation.VERTICAL,
				true, false, false);
		xylineChart.getTitle().setPaint(Color.GRAY);
		xylineChart.setBackgroundPaint(Color.WHITE);
		xylineChart.getPlot().setBackgroundAlpha(0.0f);
		xylineChart.getPlot().setBackgroundImage(null);
		xylineChart.getPlot().setOutlinePaint(null);
		xylineChart.getXYPlot().setDomainGridlinesVisible(false);
		xylineChart.getXYPlot().setRangeGridlinesVisible(false);
		xylineChart.getXYPlot().getDomainAxis().setVisible(false);
		xylineChart.getXYPlot().getRangeAxis().setVisible(false);

		int domainAxisSize = Arrays.asList(chartObjects).stream().map(each -> each.getYValues().size()).max(Integer::compareTo).get();
		xylineChart.getXYPlot().getDomainAxis().setRange(
				0.0,
				domainAxisSize + domainAxisSize * 0.15);


		xylineChart.getXYPlot().getRangeAxis().setRange(
			Arrays.asList(chartObjects).stream().map(each -> each.getMinYValue()).min(Double::compareTo).get(),
			Arrays.asList(chartObjects).stream().map(each -> each.getMaxYValue()).max(Double::compareTo).get());

		xylineChart.getXYPlot().setRenderer(0, renderer);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ChartUtils.writeChartAsPNG(out, xylineChart, width, height);
		} catch (IOException e) {
			return null;
		}
		return out.toByteArray();
	}

	public static byte[] chartWithAllDetails(int width, int height, String title, ChartObject... chartObjects) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		for (ChartObject chartObject : chartObjects) {
			final XYSeries series = new XYSeries(chartObject.getName());
			List<Double> values = chartObject.getYValues();
			for (int i = 0; i < values.size(); i++) {
				series.add(i, values.get(i));
			}
			dataset.addSeries(series);
		}
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultItemLabelGenerator(new XYItemLabelGenerator() {
			@Override
			public String generateLabel(XYDataset dataset, int series, int item) {
				if (dataset.getItemCount(series) - 1 == item) {
					double value = dataset.getYValue(series, item);
					if (value <= 0.00000001) {
						return String.format("%,.10f", value);
					} 
					if (value <= 0.000001) {
						return String.format("%,.8f", value);
					} 
					if (value <= 0.0001) {
						return String.format("%,.6f", value);
					} 
					if (value <= 0.01) {
						return String.format("%,.4f", value);
					} 
					if (value > 0.01 && value < 1000) {
						return String.format("%,.2f", value);
					}
					return String.format("%.0f", value);
					
				}
				return "";
			}
		});

		for (int i = 0; i < chartObjects.length; i++) {
			renderer.setSeriesShapesVisible(i, false);
			renderer.setSeriesPaint(i, chartObjects[i].getColor());
			renderer.setSeriesItemLabelPaint(i, chartObjects[i].getColor());
			renderer.setSeriesPositiveItemLabelPosition(i, new ItemLabelPosition(ItemLabelAnchor.INSIDE1, TextAnchor.CENTER_LEFT));
		}

		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				title,
				"",
				"",
				dataset,
				PlotOrientation.VERTICAL,
				true, true, true);
		xylineChart.getTitle().setPaint(Color.GRAY);
		xylineChart.setBackgroundPaint(Color.WHITE);

		xylineChart.getXYPlot().setRenderer(0, renderer);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ChartUtils.writeChartAsPNG(out, xylineChart, width, height);
		} catch (IOException e) {
			return null;
		}
		return out.toByteArray();
	}

	private static int getSeriesWithHighestLastValue(ChartObject[] chartObjects) {
		int series = 0;
		double highestValue = 0.0;
		for (int i = 0; i < chartObjects.length; i++) {
			Double doubleValue = chartObjects[i].getYValues().get(chartObjects[i].getYValues().size() - 1);
			if (doubleValue == null) continue;
			double value = doubleValue.doubleValue();
			if (highestValue == 0 || value > highestValue) {
				series = i;
				highestValue = value;
			}
		}
		return series;
	}

	private static int getSeriesWithLowestLastValue(ChartObject[] chartObjects) {
		int series = 0;
		double lowestValue = 0.0;
		for (int i = 0; i < chartObjects.length; i++) {
			Double doubleValue = chartObjects[i].getYValues().get(chartObjects[i].getYValues().size() - 1);
			if (doubleValue == null) continue;
			double value = doubleValue.doubleValue();
			if (lowestValue == 0 || value < lowestValue) {
				series = i;
				lowestValue = value;
			}
		}
		return series;
	}

	public static byte[] chart(int width, int height, ChartObject... chartObjects) {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		for (ChartObject chartObject : chartObjects) {
			final XYSeries series = new XYSeries(chartObject.getName());
			List<Double> values = chartObject.getYValues();
			for (int i = 0; i < values.size(); i++) {
				series.add(i, values.get(i));
			}
			dataset.addSeries(series);
		}
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for (int i = 0; i < chartObjects.length; i++) {
			renderer.setSeriesShapesVisible(i, false);
			renderer.setSeriesPaint(i, chartObjects[i].getColor());
		}
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				"",
				"",
				"",
				dataset,
				PlotOrientation.VERTICAL,
				false, false, false);
		xylineChart.setBackgroundPaint(null);
		xylineChart.getPlot().setBackgroundAlpha(0.0f);
		xylineChart.getPlot().setBackgroundImage(null);
		xylineChart.getPlot().setOutlinePaint(null);
		xylineChart.getXYPlot().setDomainGridlinesVisible(false);
		xylineChart.getXYPlot().setRangeGridlinesVisible(false);
		xylineChart.getXYPlot().getDomainAxis().setVisible(false);
		xylineChart.getXYPlot().getRangeAxis().setVisible(false);

		xylineChart.getXYPlot().getRangeAxis().setRange(
				Arrays.asList(chartObjects).stream().map(each -> each.getMinYValue()).min(Double::compareTo).get(),
				Arrays.asList(chartObjects).stream().map(each -> each.getMaxYValue()).max(Double::compareTo).get());

		xylineChart.getXYPlot().setRenderer(0, renderer);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ChartUtils.writeChartAsPNG(out, xylineChart, width, height);
		} catch (IOException e) {
			return null;
		}
		return out.toByteArray();
	}
}
