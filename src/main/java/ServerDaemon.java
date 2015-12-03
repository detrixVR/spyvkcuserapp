import shared.controller.db_service.*;

public class ServerDaemon {
    public static void main(String[] args) {
        IDBService dbService = new DBServiceImpl();
    }
}
