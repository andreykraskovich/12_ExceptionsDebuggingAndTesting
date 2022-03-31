import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        if(data == null){
            throw new IllegalArgumentException("Невозможно добавить. Пустая строка");
        }
        String[] components = data.split("\\s+");
        if(components.length != 4){
            throw new IllegalArgumentException("Неверный формат ввода");
        }
        if(!components[INDEX_EMAIL].matches("[a-zA-Z0-9]+[._a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]*[a-zA-Z]*@[a-zA-Z0-9]{2,8}.[a-zA-Z.]{2,6}")){
            throw new IllegalArgumentException("Неверный формат email");
        }
        if(!components[INDEX_PHONE].matches("[+][7][0-9]{10}")){
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}