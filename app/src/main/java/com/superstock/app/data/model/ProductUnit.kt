package com.superstock.app.data.model

enum class ProductUnit {
    BOX,
    BAG,
    BOTTLE,
    PIECE,
    PACKAGE,
    CAN,
    JAR,
    ROLL;

    companion object {
        fun fromName(name: String): ProductUnit? {
            return entries.firstOrNull { it.name == name }
        }
    }
}