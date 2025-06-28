package ru.nsu.bondar.dsl

import ru.nsu.bondar.model.Task
import ru.nsu.bondar.model.Group
import ru.nsu.bondar.model.Checkpoint

class ConfigBuilder {

    public ConfigurationDelegate configDelegate = new ConfigurationDelegate()

    def tasks(@DelegatesTo(value = ConfigurationDelegate, strategy = Closure.DELEGATE_ONLY) final Closure closure) {
        closure.delegate = configDelegate
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    def groups(@DelegatesTo(value = ConfigurationDelegate, strategy = Closure.DELEGATE_ONLY) final Closure closure) {
        closure.delegate = configDelegate
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    def checkpoints(@DelegatesTo(value = ConfigurationDelegate, strategy = Closure.DELEGATE_ONLY) final Closure closure) {
        closure.delegate = configDelegate
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
    }

    List<Task> getTasks() {
        return configDelegate.tasks
    }

    List<Group> getGroups() {
        return configDelegate.groups
    }

    List<Checkpoint> getCheckpoints() {
        return configDelegate.checkpoints
    }
}
