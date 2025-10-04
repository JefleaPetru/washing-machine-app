package com.mycompany.irr00_group_project.data;

import com.mycompany.irr00_group_project.entity.Booking;
import com.mycompany.irr00_group_project.entity.User;
import com.mycompany.irr00_group_project.entity.WashingMachine;
import com.mycompany.irr00_group_project.entity.enums.Status;
import com.mycompany.irr00_group_project.entity.enums.WashingMachineModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Singleton class that simulates a database using CSV files for persistence.
 * Manages in-memory storage and file I/O for Users, Bookings, and WashingMachines.
 * @author 2119994 Stilyan Staykov
 * @author 2120887 Martin Beloperkin
 * @author 2087340 Georgi Kolev
 * @author Petar Zhelev
 */
public class FileDb {
    private int biggestId = 0;
    private static volatile FileDb instance;
    private static final Path DATA_PATH = Path.of("/data");
    private static final String USERS_FILE = "/data/Users.csv";
    private static final String WASHING_MACHINES_FILE = "/data/WashingMachines.csv";
    private static final String BOOKINGS_FILE = "/data/Bookings.csv";

    private Dictionary<Integer, User> userDictionary = new Hashtable<Integer, User>();
    private Dictionary<Integer, WashingMachine> washingMachineDictionary = 
            new Hashtable<Integer, WashingMachine>();
    private Dictionary<Integer, Booking> bookingDictionary = 
            new Hashtable<Integer, Booking>();

    /**
    * Private constructor for the FileDb singleton.
    * Initializes the database by loading existing data from file.
    */
    private FileDb() {
        System.out.println("FileDb instance created.");
        loadData();
    }

    /**
    * Returns the singleton instance of the FileDb.
    * If it does not exist yet, it creates one.
    *
    * @return the single instance of FileDb
    */
    public static FileDb getInstance() {
        if (instance == null) {
            instance = new FileDb();
        }
        return instance;
    }

    /**
    * Retrieves a list of all users from the in-memory user dictionary.
    *
    * @return a list of all User objects
    */
    public List<User> selectAllUsers() {
        return Collections.list(userDictionary.elements());
    }

    /**
    * Retrieves a list of all washing machines from the in-memory dictionary.
    *
    * @return a list of all WashingMachine objects
    */
    public List<WashingMachine> selectAllWashingMachines() {
        var wms = Collections.list(washingMachineDictionary.elements());
        wms.sort(Comparator.comparing(WashingMachine::getId));
        return wms;
    }

    /**
    * Retrieves a list of all bookings from the in-memory dictionary.
    *
    * @return a list of all Booking objects
    */
    public List<Booking> selectAllBookings() {
        return Collections.list(bookingDictionary.elements());
    }

    /**
    * Adds a new user to the in-memory dictionary.
    * If the user does not have an ID, a new unique ID is assigned.
    * The updated data is then saved to persistent storage.
    *
    * @param user the user to be added
    */
    public void add(User user) {
        if (user.getId() == null) {
            user.setId((long) ++biggestId);
        }
        userDictionary.put(user.getId().intValue(), user);
        saveData();
    }

    /**
    * Adds a new washing machine to the in-memory dictionary.
    * If the washing machine does not have an ID, a new unique ID is assigned.
    * The updated data is then saved to persistent storage.
    *
    * @param washingMachine the washing machine to be added
    */
    public void add(WashingMachine washingMachine) {
        if (washingMachine.getId() == null) {
            washingMachine.setId((long) ++biggestId);
        }
        washingMachineDictionary.put(washingMachine.getId().intValue(), washingMachine);
        saveData();
    }

    /**
    * Adds a new booking to the in-memory dictionary.
    * If the booking does not have an ID, a new unique ID is assigned.
    * Also updates the associated user and washing machine with the new booking.
    * The updated data is then saved to persistent storage.
    *
    * @param booking the booking to be added
    */
    public void add(Booking booking) {
        if (booking.getId() == null) {
            booking.setId((long) ++biggestId);
        }
        
        //get the User from UserDictionary and update it
        User user = userDictionary.get(booking.getUser().getId().intValue());
        if (user != null) {
            user.addBooking(booking);
        }
        
        //get the WashingMachine from WashingMachineDictionary and update it
        WashingMachine machine = washingMachineDictionary.get(
                booking.getWashingMachine().getId().intValue());
        if (machine != null) {
            machine.addBooking(booking);
            bookingDictionary.put(booking.getId().intValue(), booking);
        }

        saveData();
    }

    /**
    * Finds a user by their unique ID.
    *
    * @param id the unique ID of the user
    * @return the User object if found, or null if not found
    */
    public User findUserById(long id) {
        return userDictionary.get((int)id);
    }

    /**
    * Finds a washing machine by its unique ID.
    *
    * @param id the unique ID of the washing machine
    * @return the WashingMachine object if found, or null if not found
    */
    public WashingMachine findWashingMachineById(long id) {
        return washingMachineDictionary.get((int)id);
    }

    /**
    * Finds a booking by its unique ID.
    *
    * @param id the unique ID of the booking
    * @return the Booking object if found, or null if not found
    */
    public Booking findBookingById(long id) {
        return bookingDictionary.get((int)id);
    }

    /**
    * Removes a user from the in-memory dictionary based on their ID.
    * After removal, the changes are saved to persistent storage.
    *
    * @param user the user to be deleted
    */
    public void delete(User user) {
        userDictionary.remove(user.getId().intValue());
        saveData();
    }

    /**
    * Removes a washing machine from the in-memory dictionary based on its ID.
    * After removal, the changes are saved to persistent storage.
    *
    * @param washingMachine the washing machine to be deleted
    */
    public void delete(WashingMachine washingMachine) {
        washingMachineDictionary.remove(washingMachine.getId().intValue());
        saveData();
    }

    public void updateWashingMachineStatus(WashingMachine washingMachine, Status status) {
        washingMachine.setStatus(status);
        washingMachineDictionary.put(washingMachine.getId().intValue(), washingMachine);
        saveData();
    }
    /**
    * Removes a booking from the in-memory dictionary using its ID.
    * Saves the updated data to persistent storage after deletion.
    *
    * @param booking the booking to be removed
    */
    public void delete(Booking booking) {
        bookingDictionary.remove(booking.getId().intValue());
        var user = userDictionary.get(booking.getUser().getId().intValue());
        var wm = washingMachineDictionary.get(booking.getWashingMachine().getId().intValue());
        if (user != null) {
            user.removeBooking(booking);
        }
        if (wm != null) {
            wm.removeBooking(booking);
        }
        saveData();
    }

    private void saveData() {
        saveUsers();
        saveWashingMachines();
        saveBookings();
    }

    private void loadData() {
        loadUsers();
        loadWashingMachines();
        loadBookings();
        updateBiggestId();
    }

    private InputStream getResourceAsStream(String resource) {
        return getClass().getResourceAsStream(resource);
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(
                new File(getClass().getResource(USERS_FILE).toURI()))) {
            writer.println("id,name,email,password,balance");
            for (User user : selectAllUsers()) {
                writer.printf("%d,%s,%s,%s,%.2f%n",
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBalance());
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    private void saveWashingMachines() {
        try (PrintWriter writer = new PrintWriter(
                new File(getClass().getResource(WASHING_MACHINES_FILE).toURI()))) {
            writer.println("id,model,serialNumber,status");
            for (WashingMachine machine : selectAllWashingMachines()) {
                writer.printf("%d,%s,%s,%s%n",
                        machine.getId(),
                        machine.getModel(),
                        machine.getSerialNumber(),
                        machine.getStatus());
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error saving washing machines: " + e.getMessage());
        }
    }

    private void saveBookings() {
        try (PrintWriter writer = new PrintWriter(
                new File(getClass().getResource(BOOKINGS_FILE).toURI()))) {
            writer.println("id,userId,machineId,startTime,endTime,status");
            for (Booking booking : selectAllBookings()) {
                writer.printf("%d,%d,%d,%s,%s,%s%n",
                        booking.getId(),
                        booking.getUser().getId(),
                        booking.getWashingMachine().getId(),
                        booking.getBookingStart(),
                        booking.getBookingEnd(),
                        booking.getStatus());
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResourceAsStream(USERS_FILE)))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                User user = new User();
                user.setId(Long.parseLong(parts[0]));
                user.setName(parts[1]);
                user.setEmail(parts[2]);
                user.setPassword(parts[3]);
                user.setBalance(Double.parseDouble(parts[4]));
                userDictionary.put(user.getId().intValue(), user);
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private void loadWashingMachines() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResourceAsStream(WASHING_MACHINES_FILE)))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                WashingMachine machine = new WashingMachine();
                machine.setId(Long.parseLong(parts[0]));
                machine.setModel(WashingMachineModel.valueOf(parts[1]));
                machine.setSerialNumber(parts[2]);
                machine.setStatus(Status.valueOf(parts[3]));
                washingMachineDictionary.put(machine.getId().intValue(), machine);
            }
        } catch (IOException e) {
            System.err.println("Error loading washing machines: " + e.getMessage());
        }
    }

    private void loadBookings() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getResourceAsStream(BOOKINGS_FILE)))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Booking booking = new Booking();
                booking.setId(Long.parseLong(parts[0]));
                booking.setUser(findUserById(Integer.parseInt(parts[1])));
                booking.setWashingMachine(findWashingMachineById(Integer.parseInt(parts[2])));
                booking.setBookingStart(LocalDateTime.parse(parts[3]));
                booking.setBookingEnd(LocalDateTime.parse(parts[4]));
                booking.setStatus(Status.valueOf(parts[5]));
                bookingDictionary.put(booking.getId().intValue(), booking);
                washingMachineDictionary
                        .get(booking.getWashingMachine().getId().intValue())
                        .addBooking(booking);
                userDictionary
                        .get(booking.getUser().getId().intValue())
                        .addBooking(booking);
            }
        } catch (IOException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
    }

    private void updateBiggestId() {
        int maxUserId = selectAllUsers().stream()
                .mapToInt(u -> u.getId().intValue())
                .max()
                .orElse(0);

        int maxMachineId = selectAllWashingMachines().stream()
                .mapToInt(m -> m.getId().intValue())
                .max()
                .orElse(0);

        int maxBookingId = selectAllBookings().stream()
                .mapToInt(b -> b.getId().intValue())
                .max()
                .orElse(0);

        biggestId = Math.max(Math.max(maxUserId, maxMachineId), maxBookingId);
    }
}