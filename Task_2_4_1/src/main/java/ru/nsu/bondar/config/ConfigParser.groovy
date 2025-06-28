package ru.nsu.bondar.config

import groovy.lang.Binding
import groovy.lang.GroovyShell
import ru.nsu.bondar.dsl.ConfigBuilder

class ConfigParser {
    static ConfigBuilder parse(File configFile) {
        if (!configFile.exists()) {
            throw new FileNotFoundException("Configuration file not found: ${configFile.absolutePath}")
        }

        def configBuilder = new ConfigBuilder()

        def binding = new Binding()
        binding.setVariable('tasks', { closure -> configBuilder.tasks(closure) })
        binding.setVariable('groups', { closure -> configBuilder.groups(closure) })
        binding.setVariable('checkpoints', { closure -> configBuilder.checkpoints(closure) })
        
        def shell = new GroovyShell(binding)
        shell.evaluate(configFile)

        return configBuilder
    }
}
