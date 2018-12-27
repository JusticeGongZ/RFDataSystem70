import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class PieChart {
    ChartPanel frame;

    public PieChart() {
        DefaultPieDataset data = getDataSet();
        JFreeChart chart = ChartFactory.createPieChart3D("学生人数统计", data, true,
                false, false);
        // 设置百分比
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");
        NumberFormat nf = NumberFormat.getNumberInstance();
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator(
                "{0}  {2}", nf, df);
        pieplot.setLabelGenerator(sp1);

        // 没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);
        pieplot.setIgnoreZeroValues(true);
        frame = new ChartPanel(chart, true);
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
        PiePlot piePlot = (PiePlot) chart.getPlot();
        piePlot.setLabelFont(new Font("宋体", Font.BOLD, 10));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));
    }

    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("初一", 600);
        dataset.setValue("初二", 800);
        dataset.setValue("初三", 1000);
        dataset.setValue("高一", 800);
        dataset.setValue("高二", 800);
        return dataset;
    }

    public ChartPanel getChartPanel() {
        return frame;

    }
}
