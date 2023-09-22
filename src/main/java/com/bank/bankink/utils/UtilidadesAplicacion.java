package com.bank.bankink.utils;

import com.bank.bankink.persistence.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class UtilidadesAplicacion {

    @Autowired
    CardRepository cardRepository;

    public String generateCard(long productId){
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        String result = String.valueOf(productId) + String.valueOf(randomNumber);
        return result;
    }

    public String dueDate(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("MM/yyyy");
        String dateString = localDate.plusYears(3).format(dtformat);
        return dateString;
    }
}
