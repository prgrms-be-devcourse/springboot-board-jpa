package com.prgrms.boardjpa;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utils {
   public static LocalDateTime now(){
       return LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
   }
}
