package ru.mpei.latushkina.fqw.service.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Source;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class GraphService implements IGraphPoints<String> {
    private final ExtractPointsService extractPointsService;

    @Autowired
    public GraphService(ExtractPointsService extractPointsService) {
        this.extractPointsService = extractPointsService;
    }

    @Override
    public String getGraphPointsBySource(List<Source> sources) {
        var points = extractPointsService.getGraphPointsBySource(sources);
        var chart = getChart(points);
        var base64 = chartToBase64(chart);
        return base64;
    }

    @Override
    public String getGraphPointsBySourceAndPeriod(List<Source> sources, Double timeBegin, Double timeEnd) {
        var points = extractPointsService.getGraphPointsBySourceAndPeriod(sources, timeBegin, timeEnd);
        var chart = getChart(points);
        var base64 = chartToBase64(chart);
        return base64;
    }

    private String chartToBase64(JFreeChart chart) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ChartUtils.writeChartAsPNG(outputStream, chart, 500, 300);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] chartBytes = outputStream.toByteArray();
        String base64Chart = Base64.getEncoder().encodeToString(chartBytes);

        return base64Chart;
    }

    private JFreeChart getChart(List<ChartPoint> dataset) {
        var listOfSeries =
                dataset.stream()
                        .map(ChartPoint::getSource)
                        .map(Source::getName)
                        .distinct()
                        .map(XYSeries::new)
                        .toList();

//        Map<String, XYSeries> mapOfSeries = new HashMap<>();
//        listOfSeries.forEach(x -> mapOfSeries
//                .put(x.getKey().toString(), x)
//        );
//
//        for (ChartPoint chartPoint : dataset) {
//            var series = mapOfSeries.get(chartPoint.getSource().getName());
//            chartPoint.getPoints().forEach(x-> series.add(x.getTime(),x.getValue()));
//        }
        XYSeriesCollection ds = new XYSeriesCollection();
        listOfSeries.forEach(ds::addSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "График",
                "Время",
                "Значение",
                ds,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        return chart;
    }
}
