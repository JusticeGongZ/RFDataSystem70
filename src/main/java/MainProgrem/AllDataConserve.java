package MainProgrem;

import Utils.Constant;
import Utils.DataSave;

public class AllDataConserve {

    public static void DataConserve(int flag, double[] AfterConvertData){
            //conserve every pass data
            switch (flag) {
                case Constant.PASS1_AMP:
                    DataSave dataSave1 = new DataSave(AfterConvertData, "pass1_AMP");
                    (new Thread(dataSave1)).start();
                    break;
                case Constant.PASS2_AMP:
                    DataSave dataSave2 = new DataSave(AfterConvertData, "pass2_AMP");
                    (new Thread(dataSave2)).start();
                    break;
                case Constant.PASS3_AMP:
                    DataSave dataSave3 = new DataSave(AfterConvertData, "pass3_AMP");
                    (new Thread(dataSave3)).start();
                    break;
                case Constant.PASS4_AMP:
                    DataSave dataSave4 = new DataSave(AfterConvertData, "pass4_AMP");
                    (new Thread(dataSave4)).start();
                    break;

                case Constant.PASS1_PHASE:
                    DataSave dataSave5 = new DataSave(AfterConvertData, "pass1_PHASE");
                    (new Thread(dataSave5)).start();
                    break;
                case Constant.PASS2_PHASE:
                    DataSave dataSave6 = new DataSave(AfterConvertData, "pass2_PHASE");
                    (new Thread(dataSave6)).start();
                    break;
                case Constant.PASS3_PHASE:
                    DataSave dataSave7 = new DataSave(AfterConvertData, "pass3_PHASE");
                    (new Thread(dataSave7)).start();
                    break;
                case Constant.PASS4_PHASE:
                    DataSave dataSave8 = new DataSave(AfterConvertData, "pass4_PHASE");
                    (new Thread(dataSave8)).start();
                    break;
            }
    }

    public static void packageDataConserve(){

    }
}
