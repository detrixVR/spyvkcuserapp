import controller.VKApiImpl;
import model.Client;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class VKChaseTest {
    final private VKApiImpl vkApi = Mockito.mock(VKApiImpl.class);
    final private Client client = Mockito.mock(Client.class);

    @Test
    public void collectInfo() throws IOException {

    }
}
