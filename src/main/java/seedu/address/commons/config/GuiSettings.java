package seedu.address.commons.config;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * A Serializable class that contains the GUI settings.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final Optional<Double> DEFAULT_X = Optional.empty();
    private static final Optional<Double> DEFAULT_Y = Optional.empty();

    private double windowWidth;
    private double windowHeight;
    private Optional<Double> windowX;
    private Optional<Double> windowY;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowX = DEFAULT_X;
        this.windowY = DEFAULT_Y;
    }

    public GuiSettings(double windowWidth, double windowHeight, double windowX, double windowY) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowX = Optional.of(windowX);
        this.windowY = Optional.of(windowY);
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Optional<Double> getWindowX() {
        return windowX;
    }

    public Optional<Double> getWindowY() {
        return windowY;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings)other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowX, o.windowX)
                && Objects.equals(windowY, o.windowY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowX, windowY);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("X : " + windowX + "\n");
        sb.append("Y : " + windowY + "\n");
        return sb.toString();
    }
}
