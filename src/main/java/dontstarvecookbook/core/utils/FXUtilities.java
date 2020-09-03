package dontstarvecookbook.core.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.lang.reflect.Field;

public class FXUtilities {

    private FXUtilities() {}

    @Deprecated
    public static void hackTooltipTimer(Tooltip tooltip, Duration showDuration) {
        try {
            Field behaviourField = tooltip.getClass().getDeclaredField("BEHAVIOR");
            behaviourField.setAccessible(true);
            Object o = behaviourField.get(tooltip);

            Field timerField = o.getClass().getDeclaredField("activationTimer");
            timerField.setAccessible(true);
            Timeline tl = (Timeline)timerField.get(o);

            tl.getKeyFrames().clear();
            tl.getKeyFrames().add(new KeyFrame(showDuration));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
