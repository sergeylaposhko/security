package com.my.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.CRC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Sergey on 09.05.2015.
 */
@Controller
@RequestMapping
public class MController {

    @RequestMapping(value = "/crc", method = RequestMethod.POST)
    public
    @ResponseBody
    ResultBean test(@RequestParam(value = "digest", required = true) BigInteger digest,
                    @RequestParam(value = "crc", required = true) BigInteger crc,
                    @RequestParam(value = "file") MultipartFile file,
                    @RequestParam(value = "n", required = true) BigInteger n,
                    @RequestParam(value = "publicKey") BigInteger publicKey) throws IOException {
        System.out.println("Diges is: " + digest);
        System.out.println("Crc is: " + crc);
        System.out.println("File is: " + file.getName());
        System.out.println("File length: " + file.getBytes().length);
        System.out.println("Public key is: " + publicKey);

        BigInteger expected = crc.modPow(publicKey, n);
        ResultBean resultBean = new ResultBean();
        if(expected.equals(digest)){
            resultBean.setCrcOk(true);
            int hash = CRC.hash(file.getBytes());
            if(hash == digest.intValue()){
                resultBean.setDigestOk(true);
            } else {
                resultBean.setDigestOk(false);
            }
        } else {
            resultBean.setCrcOk(false);
        }
        return resultBean;
    }

    @RequestMapping(value = "/getCrc", method = RequestMethod.POST)
    public
    @ResponseBody
    CrcResultBean getCrc(@RequestParam(value = "file") MultipartFile file) throws IOException {
        CrcResultBean crcResultBean = new CrcResultBean();
        int hash = CRC.hash(file.getBytes());
        crcResultBean.setCrc(hash);
        return crcResultBean;
    }

}