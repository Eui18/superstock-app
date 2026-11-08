package com.superstock.app.presentation.navigation

object AppRoute {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"

    const val PRODUCT_ID = "productId"
    const val PRODUCT_DETAIL = "product_detail/{$PRODUCT_ID}"
    const val PRODUCT_CREATE = "product_create"

    const val PRODUCTS_CHANGED = "productsChanged"

    fun productDetail(productId: Int): String = "product_detail/$productId"
}