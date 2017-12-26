package com.yfam.yscrumy.core

fun hash(source: String, salt: String): String {
  return source + salt
}