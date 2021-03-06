package com.dev.hacknit.Helpers;

import android.graphics.Color;
import android.view.View;

import com.dev.hacknit.Database.ChartModel;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

class LabelFormatter implements IAxisValueFormatter {
    private final String[] mLabels;

    public LabelFormatter(String[] labels) {
        mLabels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mLabels[(int) value];
    }
}

class DayFormmater implements IValueFormatter
{
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return ""+ (int)value + " dias";
    }
}
public class CustomChart {

        public static String empty = "";

        public static void CreateBarChart(ChartAdapter.BarViewHolder barViewHolder, ChartModel chartModel) {


            // barViewHolder.Info.
            barViewHolder.Title.setText(chartModel.Title);
            barViewHolder.Chart.getDescription().setText(empty);
            barViewHolder.Chart.getXAxis().setDrawLabels(false);

            barViewHolder.Chart.setPinchZoom(false);
            barViewHolder.Chart.setScaleEnabled(true);
            barViewHolder.Chart.setDrawBarShadow(false);
            barViewHolder.Chart.setDrawGridBackground(false);

            barViewHolder.Chart.getAxisRight().setEnabled(false);
            YAxis leftAxis = barViewHolder.Chart.getAxisLeft();
            leftAxis.setAxisMaximum(10f);
            leftAxis.setAxisMinimum(0f);


            leftAxis.setDrawGridLines(true);
            leftAxis.setSpaceTop(8f);
            leftAxis.setAxisMinimum(0f);

            BarDataSet dataSet = new BarDataSet(chartModel.<BarEntry>getChartData(), chartModel.Label);
            dataSet.setColors(RandomColor());

            BarData data = new BarData(dataSet);
            data.setBarWidth(0.9f); // set custom bar width

            if (chartModel.getMedia() < 6.0f) {
                barViewHolder.Info.setVisibility(View.VISIBLE);
                barViewHolder.Info.setText("O aluno precisa de reforço em: "+barViewHolder.Title.getText().toString().toLowerCase());
            } else {
                barViewHolder.Info.setVisibility(View.GONE);
                barViewHolder.Info.setText("");
            }

            barViewHolder.Chart.setData(data);
            barViewHolder.Chart.setFitBars(true); // make the x-axis fit exactly all bars
            barViewHolder.Chart.invalidate(); // refresh
        }

        private static List<Integer> RandomColor() {

            // add many colors
            ArrayList<Integer> colors = new ArrayList<Integer>();

            int cor1 = Color.parseColor("#f86674"); //vermelho
            colors.add(cor1);

            int cor2 = Color.parseColor("#47c664"); //verde
            colors.add(cor2);

            int cor3 = Color.parseColor("#dc3545"); //vermelho-escuro
            colors.add(cor3);

            int cor4 = Color.parseColor("#7b45dd"); //roxo
            colors.add(cor4);

            return colors;

        }

        public static void CreatePieChart(ChartAdapter.PieViewHolder pieViewHolder, ChartModel chartModel) {

            // configure pie chart
            int colorBlack = Color.parseColor("#000000");
            pieViewHolder.Chart.setEntryLabelColor(colorBlack);
            pieViewHolder.Chart.setDrawCenterText(true);
            // pieViewHolder.Chart.setUsePercentValues(true);
            pieViewHolder.Title.setText(chartModel.Title);
            pieViewHolder.Chart.getDescription().setText(empty);

            // enable hole and configure
            pieViewHolder.Chart.setDrawHoleEnabled(true);
            pieViewHolder.Chart.setHoleRadius(7);
            pieViewHolder.Chart.setTransparentCircleRadius(10);

            // enable rotation of the chart by touch
            pieViewHolder.Chart.setRotationAngle(0);
            pieViewHolder.Chart.setRotationEnabled(true);

            PieDataSet set = new PieDataSet(chartModel.<PieEntry>getChartData(), empty);
            PieData data = new PieData(set);
            data.setValueFormatter(new DayFormmater());
            //  data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(14f);
            //  data.setValueTextColor(Color.GRAY);

            set.setColors(RandomColor());

            pieViewHolder.Chart.setData(data);

            // undo all highlights
            pieViewHolder.Chart.highlightValues(null);

            // customize legends
            Legend l = pieViewHolder.Chart.getLegend();

            data.setValueTextColor(Color.BLACK);
            set.setValueLinePart1OffsetPercentage(90.f);
            set.setValueLinePart1Length(1f);
            set.setValueLinePart2Length(.2f);
            set.setValueTextColor(Color.BLACK);
            set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            set.setSliceSpace(3f);

            // update pie chart
            pieViewHolder.Chart.invalidate();
        }
}