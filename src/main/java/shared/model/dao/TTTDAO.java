package shared.model.dao;

import org.hibernate.Session;
import shared.model.dao.DAO;
import shared.model.snapshots.TTT;

public class TTTDAO extends DAO<TTT> {
    public TTTDAO(Session session) {
        super(session, TTT.class);
    }
}
