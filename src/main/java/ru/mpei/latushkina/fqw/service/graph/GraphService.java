package ru.mpei.latushkina.fqw.service.graph;

import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mpei.latushkina.fqw.model.dto.point.ChartPoint;
import ru.mpei.latushkina.fqw.model.dto.point.Source;
import ru.mpei.latushkina.fqw.service.fourier.FourierFilterService;
import ru.mpei.latushkina.fqw.service.graph.interfaces.GraphPoints;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GraphService implements GraphPoints<String> {
    private final ExtractPointsService extractPointsService;
    private final FourierFilterService fourierFilterService;

    @Value("${graph.picture.width}")
    private final Integer pictureWidth = 500;
    @Value("${graph.picture.height}")
    private final Integer pictureHeight = 500;
    @Value("${graph.picture.title}")
    private final String graphTitle = "FWQ";
    @Value("${graph.picture.axis-x}")
    private final String axisX = "Время";
    @Value("${graph.picture.axis-y}")
    private final String axisY = "Время";

    @Autowired
    public GraphService(ExtractPointsService extractPointsService, FourierFilterService fourierFilterService) {
        this.extractPointsService = extractPointsService;
        this.fourierFilterService = fourierFilterService;
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
            ChartUtils.writeChartAsPNG(outputStream, chart, pictureWidth, pictureHeight);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] chartBytes = outputStream.toByteArray();
        String base64Chart = Base64.getEncoder().encodeToString(chartBytes);

        return base64Chart;
    }

    private JFreeChart getChart(List<ChartPoint> dataset) {
        dataset = fourierFilterService.applyFilter(dataset);

        var listOfSeries =
                dataset.stream()
                        .map(ChartPoint::getSource)
                        .map(Source::getDescription)
                        .distinct()
                        .map(XYSeries::new)
                        .toList();

        Map<String, XYSeries> mapOfSeries = new HashMap<>();
        listOfSeries.forEach(x -> mapOfSeries
                .put(x.getKey().toString(), x)
        );
//        listOfSeries.forEach(x -> log.info("{}", x.getKey().toString()));
        for (ChartPoint chartPoint : dataset) {
            var series = mapOfSeries.get(chartPoint.getSource().getDescription());
            chartPoint.getPoints().forEach(x -> series.add(x.getTime(), x.getValue()));
        }
        XYSeriesCollection ds = new XYSeriesCollection();
        listOfSeries.forEach(ds::addSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                graphTitle,
                axisX,
                axisY,
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
