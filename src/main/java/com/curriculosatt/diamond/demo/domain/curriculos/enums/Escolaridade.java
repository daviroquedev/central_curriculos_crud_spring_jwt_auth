package com.curriculosatt.diamond.demo.domain.curriculos.enums;

public enum Escolaridade {
    FUNDAMENTAL,
    MEDIO,
    TECNICO,
    SUPERIOR,
    POS_GRADUACAO,
    MESTRADO,
    DEFAULT,
    DOUTORADO;

    public static Escolaridade fromString(Escolaridade escolaridade) {
        for (Escolaridade e : Escolaridade.values()) {
            if (e.name().equalsIgnoreCase(String.valueOf(escolaridade))) {
                return e;
            }
        }
        // Se a escolaridade não for encontrada, retorna DEFAULT
        return DEFAULT;
    }
}