import ru.hse.scheduled.api.Framework;
import ru.hse.scheduled.impl_s.New_Framework;

module ru.hse.scheduled.impl_s {
    exports ru.hse.scheduled.impl_s;

    requires ru.hse.scheduled.api;

    provides Framework with New_Framework;
}