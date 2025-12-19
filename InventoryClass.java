import java.io.*;

public class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "machine_inventory.ser";
    
    // Inventory Levels
    public int coffeeBeansGram;
    public int milkMl;
    public int syrupMl;

    private InventoryManager() {
        this.coffeeBeansGram = 1000;
        this.milkMl = 2000;
        this.syrupMl = 500;
    }

    public static InventoryManager loadInventory() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (InventoryManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(">> Starting new inventory system...");
            return new InventoryManager();
        }
    }

    public void saveInventory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
            System.out.println(">> Inventory state saved to disk.");
        } catch (IOException e) {
            System.out.println(">> Error saving inventory: " + e.getMessage());
        }
    }

    public boolean checkResources(int beansRequired, int milkRequired, int syrupRequired) {
        if (coffeeBeansGram < beansRequired) {
            System.out.println("!! Error: Low Coffee Beans. Please refill.");
            return false;
        }
        if (milkMl < milkRequired) {
            System.out.println("!! Error: Low Milk. Please refill.");
            return false;
        }
        return true;
    }

    public void consume(int beans, int milk, int syrup) {
        this.coffeeBeansGram -= beans;
        this.milkMl -= milk;
        this.syrupMl -= syrup;
        saveInventory();
    }
}
