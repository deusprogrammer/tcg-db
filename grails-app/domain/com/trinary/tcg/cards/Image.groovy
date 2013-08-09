package com.trinary.tcg.cards

class Image {
    String name
    String localPath = "/"
    String uuid = UUID.randomUUID()
    Boolean invalid = true

    static constraints = {
    }
}
