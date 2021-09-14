package qc.veko.vsd.manager;

import net.twasi.obsremotejava.OBSCommunicator;
import net.twasi.obsremotejava.OBSRemoteController;
import net.twasi.obsremotejava.callbacks.Callback;
import net.twasi.obsremotejava.requests.StartStreaming.StartStreamingRequest;
import net.twasi.obsremotejava.requests.StartStreaming.StartStreamingResponse;

import java.util.function.Consumer;

public class OBSManager {
    public OBSManager() {
        OBSRemoteController controller = new OBSRemoteController("ws://localhost:4444", true);
        OBSCommunicator test = new OBSCommunicator(false);
        //Callback<StartStreamingResponse> tt = controller.registerStreamStartedCallback( ()-> System.out.println("Disconnected"));
        controller.registerDisconnectCallback(() -> System.out.println("Disconnected"));

        if (controller.isFailed()) { // Awaits response from OBS
            // Here you can handle a failed connection request
        } else {
            controller.setMute("test", true, null);
            new StartStreamingRequest(test);
            //test.startStreaming(() -> System.out.println("test"));
        }
    }

    private void test () {

    }

}
