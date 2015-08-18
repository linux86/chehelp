package com.chehelp.protocol.format.impl;

import com.chehelp.protocol.format.FormatStrategy;

/**
 * Created by Administrator on 2015-1-26.
 */
public class HEX2ASCIIFormat implements FormatStrategy{
    @Override
    public String format(String param) {
        return convertHexToString(param);
    }

    public String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }
}
