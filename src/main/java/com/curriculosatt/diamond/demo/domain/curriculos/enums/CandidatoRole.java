package com.curriculosatt.diamond.demo.domain.curriculos.enums;

public enum CandidatoRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    CandidatoRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static CandidatoRole fromString(String value) {
        for (CandidatoRole role : CandidatoRole.values()) {
            if (role.role.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}