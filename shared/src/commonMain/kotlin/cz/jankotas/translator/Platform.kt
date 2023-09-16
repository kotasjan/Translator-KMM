package cz.jankotas.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
