package com.app.jconfiglibrary

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.github.cdimascio.dotenv.Dotenv
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.lang.RuntimeException
import java.util.Properties

class JConfig {
    private var config: Config

    init {
        config = ConfigFactory.empty()
    }

    fun parseFile(pathFile: File): Boolean {
        val path = pathFile.path
        val separador = if (path.contains('/')) '/' else '\\'
        var res = true

        if (pathFile.exists()) {
            when {
                path.endsWith(".env") -> {
                    try {
                        val directorio = path.substringBeforeLast(separador)
                        val archivo = path.substringAfterLast(separador)
                        val dotenv = Dotenv
                            .configure()
                            .directory(directorio)
                            .filename(archivo)
                            .load()
                        val envMap = dotenv.entries().associateBy({ it.key }, { it.value })
                        val properties = Properties()
                        properties.putAll(envMap)
                        config = ConfigFactory.parseProperties(properties)
                    } catch (e: RuntimeException) {
                        res = false
                    }
                }

                path.endsWith(".yml") -> {
                    try {
                        val yaml = Yaml()
                        val inputStream = FileInputStream(pathFile)
                        val yamlData = yaml.load<Map<String, Any>>(inputStream)
                        config = ConfigFactory.parseMap(yamlData)
                    } catch (e: RuntimeException) {
                        res = false
                    }
                }

                path.endsWith(".conf") || path.endsWith(".json") || path.endsWith(".properties") -> {
                    try {
                        val fileConfig: Config = ConfigFactory.parseFile(pathFile)
                        config = fileConfig
                    } catch (e: RuntimeException) {
                        res = false
                    }
                }

                else -> {
                    res = false
                }
            }

        } else {
            res = false
        }
        return res
    }

    fun getString(key: String): String? {
        var res: String?= config.getString(key)
        return res
    }
}