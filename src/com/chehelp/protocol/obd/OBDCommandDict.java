package com.chehelp.protocol.obd;

import android.R.string;

/**
 * Created by Administrator on 2015-1-21.
 */
public interface OBDCommandDict {

    /**请求动力系当前数据*/
    public enum MODEL_1{
        X00("01 00")
        ,X01("01 01"),X02("01 02"),X03("01 03"),X04("01 04"),X05("01 05"),
        X0C/**/("01 0C"),X0D("01 0D"),X0E("01 0E"),
        X1F("01 1F"),
        X21("01 21"),
        X33("01 33"),
        X46("01 46");

        private String name;
        MODEL_1(String name) {
            this.name = name;
        }
        
        public String getName(){
        	return this.name;
        }
    }

    public enum MODEL_2{

    }

    /**请求排放相关的动力系诊断故障码*/
    public enum MODEL_3{}

    public enum MODEL_4{}

    public enum MODEL_5{}

    public enum MODEL_6{}

    public enum MODEL_7{}

    /**请求控制车载系统，测试或者部件*/
    public enum MODEL_8{}

    /**读车辆和标定识别号*/
    public enum MODEL_9{
        X00/*支持的命令*/("09 00"),X01("09 01"),X02/*VIN*/("09 02"),X03("09 03"),X04("09 04"),X05("09 05"),
        X0A/*ECU NAME*/("09 0A");

        private String name;
        MODEL_9(String name) {
            this.name = name;
        }
        
        public String getName(){
        	return this.name;
        }
    }

}
