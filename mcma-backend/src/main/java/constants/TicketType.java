package constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TicketType {
    ADULT(1, "Ticket for adults", 15),
    CHILD(1, "Ticket for children", 8),
    TEEN(1, "Ticket for teenagers", 10),
    SENIOR(1, "Ticket for seniors", 12),
    STUDENT(1, "Ticket for students", 9),
    COUPLE(1, "Discounted ticket for couples", 25),
    FAMILY(1, "Discounted ticket for families", 40),;
    private final int id;
    private final String description;
    private final double price;
    TicketType(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
    private static final Map<Integer, TicketType> ID_MAP = new HashMap<>();
    static {
        for (TicketType ticketType : TicketType.values()) {
            ID_MAP.put(ticketType.getId(), ticketType);
        }
    }
    public static TicketType getById(int id) {
        return ID_MAP.get(id);
    }
}
