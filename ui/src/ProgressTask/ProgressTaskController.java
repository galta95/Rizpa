package ProgressTask;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressTaskController {

    @FXML
    private ProgressBar progress;

    private Task taskCreator(int seconds) {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds;i++){
                    Thread.sleep(10);
                    updateProgress(i+1, seconds);
                }
                return true;
            }
        };
    }

    public void startProgress() throws InterruptedException {
        Task task = taskCreator(200);
        progress.progressProperty().unbind();
        progress.progressProperty().bind(task.progressProperty());
        progress.progressProperty().unbind();
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Stage stage = (Stage) progress.getScene().getWindow();
                stage.close();
            }
        });
    }
}
