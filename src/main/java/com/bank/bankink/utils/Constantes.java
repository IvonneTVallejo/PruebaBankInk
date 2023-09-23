package com.bank.bankink.utils;

public class Constantes {

    private Constantes() {
        throw new IllegalStateException("Utility class");
    }


    // Mensajes
    public static final String CARD_ACTIVATION = "Card activation was carried out successfully";
    public static final String CARD_INACTIVATION = "Card was successfully inactivated";
    public static final String TOP_UP_BALANCE = "Card was recharged successfully";
    public static final String PURCHASE = "The purchase has been made successfully";



    // Errores

    public static final String ERR_01 = "Card is already active in the database";
    public static final String ERR_02 = "No record found with that card ID";
    public static final String ERR_03 = "Card is already inactive";
    public static final String ERR_04 = " The card is inactive. To recharge your balance you must activate it first";
    public static final String ERR_05 = " The card is inactive. Can't show balance";
    public static final String ERR_06 = "The purchase amount exceeds the card balance";
    public static final String ERR_07 = "The transaction does not exist";




}
