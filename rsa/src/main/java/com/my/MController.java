package com.my;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 09.05.2015.
 */
@Controller
@RequestMapping
public class MController {

    @RequestMapping(value = "/des", produces = {"application/json"})
    public
    @ResponseBody
    String test(@RequestParam(value = "data") String data,
                @RequestParam(value = "n") Integer n,
                @RequestParam(value = "publicKey") int publicKey) throws JsonProcessingException {
        System.out.println("Data is: " + data);
        System.out.println("N is: " + n);
        System.out.println("Public key is: " + publicKey);

        ArrayList<Long> res = new ArrayList<Long>();

        for (int i = 0; i < data.length(); i++) {
            BigInteger bigInteger = new BigInteger(String.valueOf(data.codePointAt(i)));
            bigInteger = bigInteger.pow(publicKey);
            bigInteger = bigInteger.mod(new BigInteger(String.valueOf(n)));

            res.add(bigInteger.longValue());
        }

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res);
    }

    public static class Result {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}