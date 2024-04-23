import ru.hse.scheduled.api.Framework;
import ru.hse.scheduled.impl.SimpleFramework;

module ru.hse.scheduled.impl {
    exports ru.hse.scheduled.impl;

    requires ru.hse.scheduled.api;

    provides Framework with SimpleFramework;
}