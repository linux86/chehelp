package com.chehelp.protocol.obd.impl;

import com.chehelp.protocol.Command;
import com.chehelp.protocol.Unit;
import com.chehelp.protocol.obd.OBDCommandDict;

/**
 * Created by Administrator on 2015-1-21.
 */
public class X0100 extends Command {
    public X0100() {
        super(OBDCommandDict.MODEL_1.X00.name(), Unit.EMPTY);
    }

    @Override
    public String formatResponse(String response) {
        return response;
    }
}
