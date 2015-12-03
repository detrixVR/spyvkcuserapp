import shared.controller.db_service.*;

public class Daemon {
    public static void main(String[] args) {
        IDBService dbService = new DBServiceImpl();
    }
}
