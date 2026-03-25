package seedu.address.model.delivery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Utility class for exporting delivery assignments to a formatted text file.
 * <p>
 * Provides methods to write all current driver-to-person assignments into
 * a human-readable file, grouping subscribers under each driver.
 */
public class ExportUtil {

    /**
     * Exports delivery assignments to a nicely formatted text file.
     * <p>
     * Each driver is printed as a heading, followed by their assigned subscribers.
     * <p>
     * @param assignments the map of drivers to assigned persons
     * @param filePath the file path to write the output to
     * @throws IOException if file writing fails
     */
    public static void exportAssignmentsFormatted(DeliveryAssignmentHashMap assignments, String filePath)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Driver driver : assignments.getDriversKeySet()) {

                // Driver header
                writer.write(driver.getName().toString() + " - " + driver.getPhone().toString());
                writer.newLine();
                writer.write("--------------------");
                writer.newLine();

                List<Person> people = assignments.getDeliveryListFor(driver);

                for (Person p : people) {
                    String line = String.format("%s | %s | %s | %s | Boxes: %d",
                            p.getName(),
                            p.getPhone(),
                            p.getEmail(),
                            p.getAddress(),
                            p.getBoxes().size());
                    writer.write(line);
                    writer.newLine();
                }

                writer.newLine(); // space between drivers
            }
        }
    }
}
