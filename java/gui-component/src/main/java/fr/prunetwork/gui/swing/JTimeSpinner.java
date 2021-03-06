package fr.prunetwork.gui.swing;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class define a spinner used to select an hour with minutes (no seconds, nor date)
 *
 * @author Jean-Pierre PRUNARET
 */

public final class JTimeSpinner extends JSpinner {

    public JTimeSpinner() {
        @NotNull Calendar calendar = new GregorianCalendar();

        @Nullable SpinnerModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
        super.setModel(dateModel);

        JFormattedTextField tf = ((DefaultEditor) this.getEditor()).getTextField();
        @NotNull DefaultFormatterFactory factory = (DefaultFormatterFactory) tf.getFormatterFactory();
        @NotNull DateFormatter formatter = (DateFormatter) factory.getDefaultFormatter();

        /** Change the date format to only show the hours and minutes */
        formatter.setFormat(new SimpleDateFormat("HH:mm"));
    }

    @Override
    public void setModel(SpinnerModel model) {
        throw new IllegalStateException("Shouldn't be called. Already defined");
    }
}
