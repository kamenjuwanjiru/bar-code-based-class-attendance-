package com.gatepass.GatePass.barqrgenerator;

import java.nio.file.Paths;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gatepass.GatePass.dtos.StoredData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Generator {

    @Value("${pics.store}")
    String filePath;

    public boolean generateBarAndQrcode(StoredData storedData) throws Exception{
        log.info(filePath);
        try{
            log.info(storedData.getIdNo());
            // Code128Writer code128Writer = new Code128Writer();
            EAN13Writer ean13Writer = new EAN13Writer();
            BitMatrix bitMatrix = ean13Writer.encode(storedData.getIdNo(),BarcodeFormat.EAN_13, 1000, 500);
            BitMatrix matrix = new MultiFormatWriter().encode(new String(storedData.getIdNo()), BarcodeFormat.QR_CODE, 300, 500);
            
            MatrixToImageWriter.writeToPath(bitMatrix, "jpg", Paths.get( filePath+"\\"+storedData.getEmail()+"-BAR.jpg"));
            MatrixToImageWriter.writeToPath(matrix, "jpg",Paths.get(filePath+"\\"+storedData.getEmail()+"-QR.jpg"));
           log.info("bar and qr code saved");
           log.info("file saved at: "+ filePath);
           return true;
        }catch(Exception e){
            log.warn(e.getMessage());
            return false;
        }
    }

    //to satisfy the requirements of barcode
    public String genString(){
        String options = "123456789";
        Random random = new Random();
        String string = "";
        for(int i = 0; i<12; i++){
            string = string+ options.charAt(random.nextInt(options.length()));
        }
        return string;
    }
}
