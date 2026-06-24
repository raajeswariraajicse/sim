import java.util.*;

class Startup {
    int id;
    String name;
    String sector;
    double fundingNeeded;

    public Startup(int id, String name, String sector, double fundingNeeded) {
        this.id = id;
        this.name = name;
        this.sector = sector;
        this.fundingNeeded = fundingNeeded;
    }

    @Override
    public String toString() {
        return "Startup ID: " + id +
                ", Name: " + name +
                ", Sector: " + sector +
                ", Funding Needed: Rs." +
                String.format("%,.0f", fundingNeeded);
    }
}

class Investor {
    int id;
    String name;
    String interestedSector;
    double minInvestment;
    double maxInvestment;

    public Investor(int id, String name, String interestedSector,
                    double minInvestment, double maxInvestment) {
        this.id = id;
        this.name = name;
        this.interestedSector = interestedSector;
        this.minInvestment = minInvestment;
        this.maxInvestment = maxInvestment;
    }

    @Override
    public String toString() {
        return "Investor ID: " + id +
                ", Name: " + name +
                ", Sector Interest: " + interestedSector +
                ", Investment Range: Rs." +
                String.format("%,.0f", minInvestment) +
                " - Rs." +
                String.format("%,.0f", maxInvestment);
    }
}

public class StartupInvestorManagement {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Startup> startups = new ArrayList<>();
    static ArrayList<Investor> investors = new ArrayList<>();

    static int startupIdCounter = 1;

    public static void main(String[] args) {

        seedInvestors();

        while (true) {

            System.out.println("\n========================================");
            System.out.println(" STARTUP - INVESTOR MATCHING SYSTEM ");
            System.out.println("========================================");
            System.out.println("1. Add Startup");
            System.out.println("2. View Startups");
            System.out.println("3. Update Startup");
            System.out.println("4. Delete Startup");
            System.out.println("5. View Investors");
            System.out.println("6. Find Matching Investors");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> addStartup();
                case 2 -> viewStartups();
                case 3 -> updateStartup();
                case 4 -> deleteStartup();
                case 5 -> viewInvestors();
                case 6 -> matchInvestor();
                case 7 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ADD STARTUP
    static void addStartup() {
        System.out.print("Enter Startup Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Sector (Technology/Healthcare/FinTech/Media): ");
        String sector = sc.nextLine();

        System.out.print("Enter Funding Needed (Rs.): ");
        double funding = sc.nextDouble();
        sc.nextLine();

        Startup s = new Startup(startupIdCounter++, name, sector, funding);
        startups.add(s);

        System.out.println("\nStartup Added Successfully!");
        System.out.println("Startup ID: " + s.id);
    }

    // VIEW STARTUPS
    static void viewStartups() {
        if (startups.isEmpty()) {
            System.out.println("No startups available.");
            return;
        }

        System.out.println("\n===== STARTUPS =====");
        for (Startup s : startups) {
            System.out.println(s);
        }
    }

    // UPDATE STARTUP
    static void updateStartup() {
        System.out.print("Enter Startup ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Startup s : startups) {
            if (s.id == id) {

                System.out.print("New Name: ");
                s.name = sc.nextLine();

                System.out.print("New Sector: ");
                s.sector = sc.nextLine();

                System.out.print("New Funding (Rs.): ");
                s.fundingNeeded = sc.nextDouble();
                sc.nextLine();

                System.out.println("Updated Successfully!");
                return;
            }
        }

        System.out.println("Startup not found!");
    }

    // DELETE STARTUP
    static void deleteStartup() {
        System.out.print("Enter Startup ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean removed = startups.removeIf(s -> s.id == id);

        System.out.println(removed ? "Deleted Successfully!" : "Not Found!");
    }

    // VIEW INVESTORS
    static void viewInvestors() {
        System.out.println("\n===== INVESTORS =====");
        for (Investor i : investors) {
            System.out.println(i);
        }
    }

    // MATCH INVESTORS
    static void matchInvestor() {
        System.out.print("Enter Startup ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Startup target = null;

        for (Startup s : startups) {
            if (s.id == id) {
                target = s;
                break;
            }
        }

        if (target == null) {
            System.out.println("Startup not found!");
            return;
        }

        System.out.println("\n===== MATCH RESULTS =====");
        boolean found = false;

        for (Investor inv : investors) {

            boolean sectorMatch =
                    inv.interestedSector.equalsIgnoreCase(target.sector);

            boolean fundingMatch =
                    target.fundingNeeded >= inv.minInvestment &&
                    target.fundingNeeded <= inv.maxInvestment;

            if (sectorMatch && fundingMatch) {
                System.out.println(inv);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching investors found.");
        }
    }

    // SEED INVESTORS (3 PER MAJOR SECTOR)
    static void seedInvestors() {

        // TECHNOLOGY (3)
        investors.add(new Investor(1, "S. D. Shibulal", "Technology", 5000000, 200000000));
        investors.add(new Investor(2, "N. Srinivasan", "Technology", 1000000, 100000000));
        investors.add(new Investor(3, "Venu Srinivasan", "Technology", 5000000, 150000000));

        // HEALTHCARE (3)
        investors.add(new Investor(4, "A. Velumani", "Healthcare", 1000000, 50000000));
        investors.add(new Investor(5, "Tamil Health Ventures", "Healthcare", 2000000, 80000000));
        investors.add(new Investor(6, "Apollo Growth Fund", "Healthcare", 5000000, 100000000));

        // FINTECH (3)
        investors.add(new Investor(7, "R. Thyagarajan", "FinTech", 2000000, 100000000));
        investors.add(new Investor(8, "Chennai Fin Capital", "FinTech", 1000000, 50000000));
        investors.add(new Investor(9, "Digital Pay Partners", "FinTech", 5000000, 150000000));

        // MEDIA (1)
        investors.add(new Investor(10, "Kalanithi Maran", "Media", 1000000, 250000000));
    }
}