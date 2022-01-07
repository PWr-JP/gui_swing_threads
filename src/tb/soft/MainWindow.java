package tb.soft;

import javax.swing.*;
import java.awt.*;

/**
 * klasa główna zawierająca metodę statyczną main
 */
public class MainWindow extends JFrame {

    private ProgressMonitor progressMonitor =
            new ProgressMonitor(MainWindow.this,"Running",
            "% of done",0,100);

    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            try {
                MainWindow window = new MainWindow("Wątki");
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        });
    }

    public MainWindow() throws HeadlessException {
        this("undefined");
    }

    public MainWindow(String title) throws HeadlessException {
        super(title);

        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program ma się zakończyć po zamknięciu tego okna

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnDo = new JMenu("Do");
        menuBar.add(mnDo);

        JMenuItem mnitDo = new JMenuItem("Work");
        mnitDo.addActionListener(e -> {
            progressMonitor.setProgress(0);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    doWork();
                    return null;
                }
            }.execute();
        });
        mnDo.add(mnitDo);

    }

    /**
     * metoda nie robi nic, ale za to długo
     */
    private void doWork() {
        final int cnt = 10;
//        System.out.println("start");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i <= cnt; i++) {
            progressMonitor.setProgress(100*i /cnt);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//        System.out.println("stop");
    }
}
