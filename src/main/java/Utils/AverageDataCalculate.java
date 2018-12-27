package Utils;

public class AverageDataCalculate {

    public static double AmpAvgData(double[] inputData) {

        double sum = 0;
        double avg = 0;
        for (int i = 0; i < inputData.length; i++) {
            sum += inputData[i];
        }
        avg = sum / inputData.length;
//        System.out.println(avg);
        return avg;
    }

    public static double PhaseAvgData(double[] inputData) {

        double sum = 0;
        double avg = 0;
        for (int i = 0; i < inputData.length; i++) {
            sum += inputData[i];
        }
        avg = sum / inputData.length;
//        System.out.println(avg);
        return avg;
    }
}
